////////////////////////////////////////////////////////////////////////////////
// Main File:        csim.c
// This File:        csim.c
// Other Files:      (name of all other files if any)
// Semester:         CS 354 Spring 2021
// Instructor:       deppeler
//
// Author:           Ayan Deep Hazra
// Email:            ahazra2@wisc.edu
// CS Login:         ayan
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of
//                   of any information you find.
//////////////////////////// 80 columns wide ///////////////////////////////////
//YOUR FILE HEADER HERE

////////////////////////////////////////////////////////////////////////////////
//
// Copyright 2013,2019-2020, Jim Skrentny, (skrentny@cs.wisc.edu)
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission, CS354-Spring 2021, Deb Deppeler (deppeler@cs.wisc.edu)
//
////////////////////////////////////////////////////////////////////////////////

/**
 * csim.c:  
 * A cache simulator that can replay traces (from Valgrind) and 
 * output statistics for the number of hits, misses, and evictions.
 * The replacement policy is LRU.
 *
 * Implementation and assumptions:
 *  1. Each load/store can cause at most 1 cache miss plus a possible eviction.
 *  2. Instruction loads (I) are ignored.
 *  3. Data modify (M) is treated as a load followed by a store to the same
 *  address. Hence, an M operation can result in two cache hits, or a miss and a
 *  hit plus a possible eviction.
 */

#include <getopt.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>
#include <limits.h>
#include <string.h>
#include <errno.h>
#include <stdbool.h>

/*****************************************************************************/
/* DO NOT MODIFY THESE VARIABLES *********************************************/

//Globals set by command line args.
int b = 0; //number of block (b) bits
int s = 0; //number of set (s) bits
int E = 0; //number of lines per set

//Globals derived from command line args.
int B; //block size in bytes: B = 2^b
int S; //number of sets: S = 2^s

//Global counters to track cache statistics in access_data().
int hit_cnt = 0;
int miss_cnt = 0;
int evict_cnt = 0;

//Global to control trace output
int verbosity = 0; //print trace if set
/*****************************************************************************/

//Type mem_addr_t: Use when dealing with addresses or address masks.
typedef unsigned long long int mem_addr_t;

//Type cache_line_t: Use when dealing with cache lines.
//TODO - COMPLETE THIS TYPE
typedef struct cache_line
{
    char valid;
    mem_addr_t tag;
    //Add a data member as needed by your implementation for LRU tracking.
    struct cache_line *next;
    int count;
} cache_line_t;

//Type cache_set_t: Use when dealing with cache sets
//Note: Each set is a pointer to a heap array of one or more cache lines.
typedef cache_line_t *cache_set_t;
//Type cache_t: Use when dealing with the cache.
//Note: A cache is a pointer to a heap array of one or more sets.
typedef cache_set_t *cache_t;

// Create the cache (i.e., pointer var) we're simulating.
cache_t cache;

/**
 *  TODO - COMPLETE THIS FUNCTION
 * init_cache:
 * Allocates the data structure for a cache with S sets and E lines per set.
 * Initializes all valid bits and tags with 0s.
 */
void init_cache()
{
    // as defined S is 2 to the power s bits and B is 2 to the power b bits
    B = pow(2, b);
    S = pow(2, s);

    // malloc for the cache
    cache = malloc(sizeof(cache_line_t) * S);
    // error checking
    if (cache == NULL)
    {
        printf("Failed to allocate cache.");
        exit(1);
    }
    // define iteration variables
    int set = 0;
    int line = 0;
    while (set < S)
    {
        // temporary variable to hold current cache line by index
        cache_line_t *temp;
        // line variable is an iterator, set to 0 to start off iteration
        line = 0;
        while (line < E)
        {
            cache_line_t *block = malloc(sizeof(cache_line_t));
            if (block == NULL)
            {
                printf("Failed to allocate block.");
                exit(1);
            }
            // reset all parameters to 0
            block->valid = 0;
            block->tag = 0;
            block->count = 0;
            block->next = NULL;
            // if line is 0, assign cache set else iterate to next block
            if (line == 0)
            {
                cache[set] = block;
                temp = cache[set];
            }
            else
            {
                temp->next = block;
                temp = block;
            }
            // increment line
            line = line + 1;
        }
        // increment set
        set = set + 1;
    };
}

/**
 * TODO - COMPLETE THIS FUNCTION 
 * free_cache:
 * Frees all heap allocated memory used by the cache.
 */
void free_cache()
{
    cache_line_t *next;
    cache_line_t *currentLine;

    for (int set = 0; set < S; set++)
    {
        // array access
        currentLine = cache[set];
        while (currentLine != NULL)
        {
            // set next element
            next = currentLine->next;
            free(currentLine);
            // free currentLine and set it to NULL and assign next
            currentLine = NULL;
            currentLine = next;
        }
    }
    // free the main "cache" variable and set it to NULL
    free(cache);
    cache = NULL;
}

/**
 * TODO - COMPLETE THIS FUNCTION 
 * access_data:
 * Simulates data access at given "addr" memory address in the cache.
 *
 * If already in cache, increment hit_cnt
 * If not in cache, cache it (set tag), increment miss_cnt
 * If a line is evicted, increment evict_cnt
 */
void access_data(mem_addr_t addr)
{
    // variables for set and tag
    // set is just the address shifted by b bits and only the bits from 0 to s need to
    // be taken
    int setNumber = ((int)addr >> b) & (S - 1);
    // tag is just address shifted by s+b bits
    int tagNumber = (int)(addr) >> (s + b);

    // variable for current set
    cache_line_t *current = cache[setNumber];
    // current
    cache_line_t *tempHead = current;

    // max and minimum counter vars
    int maximum = current->count;
    int minimum = current->count;

    // set minimum and maximum values from traversing the cache
    for (int i = 0; i < E; i++)
    {
        if (current->count < minimum)
        {
            minimum = current->count;
        }
        if (current->count > maximum)
        {
            maximum = current->count;
        }
        current = current->next;
    }
    current = tempHead;
    for (int i = 0; i < E; i++)
    {
        // if current is valid is tagNumber matches current's tag, then
        // increment hit counter, set count to max + 1
        if ((current->valid == 1) && (tagNumber == current->tag))
        {
            hit_cnt = hit_cnt + 1;
            current->count = maximum + 1;
            return;
        }
        // else move onto next
        else
        {
            current = current->next;
        }
    }
    // miss counter
    miss_cnt = miss_cnt + 1;
    current = tempHead;
    for (int i = 0; i < E; i++)
    {
        // if valid is 0 set valid to 1, set tag and increment count
        if (current->valid == 0)
        {
            current->tag = tagNumber;
            current->count = maximum + 1;
            current->valid = 1;

            return;
        }
        current = current->next;
    }
    // evict counter
    evict_cnt = evict_cnt + 1;
    current = tempHead;
    for (int i = 0; i < E; i++)
    {
        // if count is minimum, set valid to 1, set tag and increment count
        if (current->count == minimum)
        {
            current->tag = tagNumber;
            current->count = maximum + 1;
            current->valid = 1;
            return;
        }
        current = current->next;
    }
}

/**
 * TODO - FILL IN THE MISSING CODE
 * replay_trace:
 * Replays the given trace file against the cache.
 *
 * Reads the input trace file line by line.
 * Extracts the type of each memory access : L/S/M
 * TRANSLATE each "L" as a load i.e. 1 memory access
 * TRANSLATE each "S" as a store i.e. 1 memory access
 * TRANSLATE each "M" as a load followed by a store i.e. 2 memory accesses 
 */
void replay_trace(char *trace_fn)
{
    char buf[1000];
    mem_addr_t addr = 0;
    unsigned int len = 0;
    FILE *trace_fp = fopen(trace_fn, "r");

    if (!trace_fp)
    {
        fprintf(stderr, "%s: %s\n", trace_fn, strerror(errno));
        exit(1);
    }

    while (fgets(buf, 1000, trace_fp) != NULL)
    {
        if (buf[1] == 'S' || buf[1] == 'L' || buf[1] == 'M')
        {
            sscanf(buf + 3, "%llx,%u", &addr, &len);

            if (verbosity)
                printf("%c %llx,%u ", buf[1], addr, len);

            // TODO - MISSING CODE
            // GIVEN: 1. addr has the address to be accessed
            //        2. buf[1] has type of acccess(S/L/M)
            // call access_data function here depending on type of access

            // if buf[1] is 'S' or 'L' access address only once
            if (buf[1] == 'S' || buf[1] == 'L')
            {
                access_data(addr);
            }
            // if buf[1] is 'M' access address twice
            else if (buf[1] == 'M')
            {
                access_data(addr);
                access_data(addr);
            }

            if (verbosity)
                printf("\n");
        }
    }

    fclose(trace_fp);
}

/**
 * print_usage:
 * Print information on how to use csim to standard output.
 */
void print_usage(char *argv[])
{
    printf("Usage: %s [-hv] -s <num> -E <num> -b <num> -t <file>\n", argv[0]);
    printf("Options:\n");
    printf("  -h         Print this help message.\n");
    printf("  -v         Optional verbose flag.\n");
    printf("  -s <num>   Number of s bits for set index.\n");
    printf("  -E <num>   Number of lines per set.\n");
    printf("  -b <num>   Number of b bits for block offsets.\n");
    printf("  -t <file>  Trace file.\n");
    printf("\nExamples:\n");
    printf("  linux>  %s -s 4 -E 1 -b 4 -t traces/yi.trace\n", argv[0]);
    printf("  linux>  %s -v -s 8 -E 2 -b 4 -t traces/yi.trace\n", argv[0]);
    exit(0);
}

/**
 * print_summary:
 * Prints a summary of the cache simulation statistics to a file.
 */
void print_summary(int hits, int misses, int evictions)
{
    printf("hits:%d misses:%d evictions:%d\n", hits, misses, evictions);
    FILE *output_fp = fopen(".csim_results", "w");
    assert(output_fp);
    fprintf(output_fp, "%d %d %d\n", hits, misses, evictions);
    fclose(output_fp);
}

/**
 * main:
 * Main parses command line args, makes the cache, replays the memory accesses
 * free the cache and print the summary statistics.  
 */
int main(int argc, char *argv[])
{
    char *trace_file = NULL;
    char c;

    // Parse the command line arguments: -h, -v, -s, -E, -b, -t
    while ((c = getopt(argc, argv, "s:E:b:t:vh")) != -1)
    {
        switch (c)
        {
        case 'b':
            b = atoi(optarg);
            break;
        case 'E':
            E = atoi(optarg);
            break;
        case 'h':
            print_usage(argv);
            exit(0);
        case 's':
            s = atoi(optarg);
            break;
        case 't':
            trace_file = optarg;
            break;
        case 'v':
            verbosity = 1;
            break;
        default:
            print_usage(argv);
            exit(1);
        }
    }

    //Make sure that all required command line args were specified.
    if (s == 0 || E == 0 || b == 0 || trace_file == NULL)
    {
        printf("%s: Missing required command line argument\n", argv[0]);
        print_usage(argv);
        exit(1);
    }

    //Initialize cache.
    init_cache();

    //Replay the memory access trace.
    replay_trace(trace_file);

    //Free memory allocated for cache.
    free_cache();

    //Print the statistics to a file.
    //DO NOT REMOVE: This function must be called for test_csim to work.
    print_summary(hit_cnt, miss_cnt, evict_cnt);
    return 0;
}

// end csim.c
