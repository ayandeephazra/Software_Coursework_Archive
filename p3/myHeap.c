///////////////////////////////////////////////////////////////////////////////
//
// Copyright 2020-2021 Deb Deppeler based on work by Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission Fall 2020, CS354-deppeler
//
///////////////////////////////////////////////////////////////////////////////

// DEB'S PARTIAL SOLUTION FOR SPRING 2021 DO NOT SHARE

////////////////////////////////////////////////////////////////////////////////
// Main File:        myHeap.c
// This File:        myHeap.c
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

#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <stdio.h>
#include <string.h>
#include "myHeap.h"

/*
 * This structure serves as the header for each allocated and free block.
 * It also serves as the footer for each free block but only containing size.
 */
typedef struct blockHeader
{

    int size_status;
    /*
     * Size of the block is always a multiple of 8.
     * Size is stored in all block headers and in free block footers.
     *
     * Status is stored only in headers using the two least significant bits.
     *   Bit0 => least significant bit, last bit
     *   Bit0 == 0 => free block
     *   Bit0 == 1 => allocated block
     *
     *   Bit1 => second last bit 
     *   Bit1 == 0 => previous block is free
     *   Bit1 == 1 => previous block is allocated
     * 
     * End Mark: 
     *  The end of the available memory is indicated using a size_status of 1.
     * 
     * Examples:
     * 
     * 1. Allocated block of size 24 bytes:
     *    Allocated Block Header:
     *      If the previous block is free      p-bit=0 size_status would be 25
     *      If the previous block is allocated p-bit=1 size_status would be 27
     * 
     * 2. Free block of size 24 bytes:
     *    Free Block Header:
     *      If the previous block is free      p-bit=0 size_status would be 24
     *      If the previous block is allocated p-bit=1 size_status would be 26
     *    Free Block Footer:
     *      size_status should be 24
     */
} blockHeader;

/* Global variable - DO NOT CHANGE. It should always point to the first block,
 * i.e., the block at the lowest address.
 */
blockHeader *heapStart = NULL;

/* Size of heap allocation padded to round to nearest page size.
 */
int allocsize;

/*
 * Additional global variables may be added as needed below
 */
blockHeader *header = NULL;
int headerSizeStatus;
blockHeader *prevHeader = NULL;
blockHeader *recentAlloc = NULL;
int newprevHeadersizestatus;

/* 
 * Function for allocating 'size' bytes of heap memory.
 * Argument size: requested size for the payload
 * Returns address of allocated block (payload) on success.
 * Returns NULL on failure.
 *
 * This function must:
 * - Check size - Return NULL if not positive or if larger than heap space.
 * - Determine block size rounding up to a multiple of 8 
 *   and possibly adding padding as a result.
 *
 * - Use BEST-FIT PLACEMENT POLICY to chose a free block
 *
 * - If the BEST-FIT block that is found is exact size match
 *   - 1. Update all heap blocks as needed for any affected blocks
 *   - 2. Return the address of the allocated block payload
 *
 * - If the BEST-FIT block that is found is large enough to split 
 *   - 1. SPLIT the free block into two valid heap blocks:
 *         1. an allocated block
 *         2. a free block
 *         NOTE: both blocks must meet heap block requirements 
 *       - Update all heap block header(s) and footer(s) 
 *              as needed for any affected blocks.
 *   - 2. Return the address of the allocated block payload
 *
 * - If a BEST-FIT block found is NOT found, return NULL
 *   Return NULL unable to find and allocate block for desired size
 *
 * Note: payload address that is returned is NOT the address of the
 *       block header.  It is the address of the start of the 
 *       available memory for the requesterr.
 *
 * Tips: Be careful with pointer arithmetic and scale factors.
 */
void *myAlloc(int size)
{

    if (size <= 0 || size > allocsize)
    {
        return NULL;
    }
    // headers are always 4 long
    int header = 4;
    // padding is the remaining space after header and payload/size has been added
    int padding = (8 - (header + size) % 8) % 8;
    // we define another variable so if any changes to size is made, we have a copy
    int payload = size;
    // total size of the block
    int total = header + padding + payload;

    // declare current and BestBlock
    blockHeader *current = heapStart;
    blockHeader *bestBlock = NULL;
    // infinite while, exit only if allocated or cannot
    // do not exit if all possibilites are not explored
    while (1)
    {
        // if current block is free

        if ((current->size_status & 1) == 0)
        {
            int differenceCurrent = (current->size_status & ~3) - total;
            //if total equal to current block size, it's the bestfit one
            if (differenceCurrent == 0)
            {
                current->size_status = current->size_status | 1;
                blockHeader *next_header = (blockHeader *)((void *)current + (current->size_status & (~3)));
                // next_header is before the end of the heap
                if (next_header < (blockHeader *)((void *)heapStart + allocsize))
                {
                    next_header->size_status = next_header->size_status | 2;
                }
                //Returns address of the payload in the allocated block on success
                return (blockHeader *)((void *)current + 4);
                // store as bestfit
            }
            else if (differenceCurrent > 0)
            {
                // bestBlock NULL case
                if (bestBlock == NULL)
                    bestBlock = current;
                // bestBlock is bigger than current in size
                else if (bestBlock->size_status > current->size_status)
                {
                    bestBlock = current;
                }
            }
        }
        // make the currrent pointer move to the next
        current = (blockHeader *)((void *)current + (current->size_status & (~3)));
        //if currrent exceeds the heap's maximum,  break into pieces
        if (current >= (blockHeader *)((void *)heapStart + allocsize))
        {
            if (bestBlock == NULL)
            {
                //Returns NULL on failure
                return NULL;
            }
            else
            {
                // split only if the size of bestBlock is large enough, else do not
                int differenceBestBlock = (bestBlock->size_status & ~3) - total;
                
                if (differenceBestBlock < 8)
                {
                    bestBlock->size_status = bestBlock->size_status | 1;
                    blockHeader *next_header = (blockHeader *)((void *)bestBlock + (bestBlock->size_status & ~3));
                    if (next_header < (blockHeader *)((void *)heapStart + allocsize))
                    {
                        next_header->size_status = next_header->size_status | 2;
                    }
                }
                else
                {
                    blockHeader *header = (blockHeader *)((void *)bestBlock + total);
                    header->size_status = differenceBestBlock | 2;
                    blockHeader *footer = (blockHeader *)((void *)bestBlock + total + differenceBestBlock - 4);
                    footer->size_status = differenceBestBlock;
                    bestBlock->size_status = total | 3;
                }
                //Returns address of the payload in the allocated block on success
                recentAlloc = (blockHeader *)((void *)bestBlock + 4);
                return recentAlloc;
            }
        }
    }
}

/* 
 * Function for freeing up a previously allocated block.
 * Argument ptr: address of the block to be freed up.
 * Returns 0 on success.
 * Returns -1 on failure.
 * This function should:
 * - Return -1 if ptr is NULL.
 * - Return -1 if ptr is not a multiple of 8.
 * - Return -1 if ptr is outside of the heap space.
 * - Return -1 if ptr block is already freed.
 * - Update header(s) and footer as needed.
 */
int myFree(void *ptr)
{
    blockHeader *previousBlockFooter = NULL;
    blockHeader *nextBlockHeader = NULL;

    // if ptr is NULL, fail
    if (ptr == NULL)
    {
        return -1;
    }
    //Header of block to free
    header = (blockHeader *)((void *)ptr - 4);

    //Checking if the a bit is zero
    if ((header->size_status) % 2 == 0)
    {
        return -1;
    }
    // header is lower than heapStart or greater than max heap loc
    if (heapStart > header || header > (blockHeader *)((void *)heapStart + allocsize))
    {
        return -1;
    }

    //Header of block we are trying to free
    headerSizeStatus = header->size_status;
    header->size_status = headerSizeStatus - 1; //Changing the a bit to 0.
    //Footer of the current block, set
    previousBlockFooter = (blockHeader *)((void *)header +
                                          headerSizeStatus - (headerSizeStatus) % 8 - 4);
    // setting size of previousBlockFooter
    previousBlockFooter->size_status = headerSizeStatus - (headerSizeStatus) % 8;
    // nextBlockHeader assignment
    nextBlockHeader = (blockHeader *)((void *)previousBlockFooter + 4);
    // Setting the p bit of next block
    nextBlockHeader->size_status = nextBlockHeader->size_status - 2;
    // previous header setting to current one
    prevHeader = header;

    return 0;
}

/*
 * Function for traversing heap block list and coalescing all adjacent 
 * free blocks.
 *
 * This function is used for delayed coalescing.
 * Updated header size_status and footer size_status as needed.
 */

int coalesce()
{
    header = heapStart;

    // returnVal is 0 if coalesce fails i.e. no two blocks have been coalesced in whole heap
    int returnVal = 0;
    // counter to keep track track of how many contiguous free blocks exist
    int currFreeCount = 0;
    // memory of the blocks that have been coalesced uptill that point in the code
    int contiguousFreeMem = 0;
    // if a chain of free blocks have been found, this pointer points to the first of them
    blockHeader *currFirstBlock;

    // run through the whole heap
    while (header->size_status != 1)
    {

        // alloc bit of current is 0, meaning block is free and no former blocks have been taken
        if (!(header->size_status & 1) && currFreeCount == 0)
        {

            contiguousFreeMem = 0;
            currFirstBlock = header;
            currFreeCount = currFreeCount + 1;
            contiguousFreeMem = header->size_status;
            header = (blockHeader *)((void *)header + header->size_status - header->size_status % 8);
        }
        // alloc bit of current is 0, meaning block is free and some former blocks have been taken
        else if (!(header->size_status & 1) && currFreeCount != 0)
        {

            currFreeCount = currFreeCount + 1;
            contiguousFreeMem = contiguousFreeMem + header->size_status;
            //blockHeader* temp = header;
            header = (blockHeader *)((void *)header + header->size_status - header->size_status % 8);
            // setting to random positive value, since atleast one instance of coalesce happened
            returnVal = 10;
        }
        // // alloc bit of current is 1, meaning block is occupied
        else if ((header->size_status & 1))
        {

            // subcase to see if previously any free blocks exist or not
            // if free blocks exist, then all free blocks prior to this
            // block are coalesced
            if (currFreeCount != 0)
            {

                currFirstBlock->size_status = contiguousFreeMem;

                blockHeader *prevFooter = (blockHeader *)((void *)header - 4);
                prevFooter->size_status = contiguousFreeMem;

                currFreeCount = 0;
                contiguousFreeMem = 0;
                returnVal = 10;
                header = (blockHeader *)((void *)header + header->size_status - header->size_status % 8);
            }

            // if the prior block is also full (currFreeCount = 0), then just continue
            // down the heap

            else
            {
                currFreeCount = 0;
                contiguousFreeMem = 0;
                header = (blockHeader *)((void *)header + header->size_status - header->size_status % 8);
                continue;
            }
        }
    }

    // end of heap case
    // this case is necessary as what if the heap ends with many free blocks
    // this would fail as there would be no end allocated block to stop and execute
    // the second else if condition
    if (currFreeCount != 0)
    {

        currFirstBlock->size_status = contiguousFreeMem;

        blockHeader *prevFooter = (blockHeader *)((void *)header - 4);
        prevFooter->size_status = contiguousFreeMem;
    }

    return returnVal;
}

/* 
 * Function used to initialize the memory allocator.
 * Intended to be called ONLY once by a program.
 * Argument sizeOfRegion: the size of the heap space to be allocated.
 * Returns 0 on success.
 * Returns -1 on failure.
 */
int myInit(int sizeOfRegion)
{

    static int allocated_once = 0; //prevent multiple myInit calls

    int pagesize;   // page size
    int padsize;    // size of padding when heap size not a multiple of page size
    void *mmap_ptr; // pointer to memory mapped area
    int fd;

    blockHeader *endMark;

    if (0 != allocated_once)
    {
        fprintf(stderr,
                "Error:mem.c: InitHeap has allocated space during a previous call\n");
        return -1;
    }

    if (sizeOfRegion <= 0)
    {
        fprintf(stderr, "Error:mem.c: Requested block size is not positive\n");
        return -1;
    }

    // Get the pagesize
    pagesize = getpagesize();

    // Calculate padsize as the padding required to round up sizeOfRegion
    // to a multiple of pagesize
    padsize = sizeOfRegion % pagesize;
    padsize = (pagesize - padsize) % pagesize;

    allocsize = sizeOfRegion + padsize;

    // Using mmap to allocate memory
    fd = open("/dev/zero", O_RDWR);
    if (-1 == fd)
    {
        fprintf(stderr, "Error:mem.c: Cannot open /dev/zero\n");
        return -1;
    }
    mmap_ptr = mmap(NULL, allocsize, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
    if (MAP_FAILED == mmap_ptr)
    {
        fprintf(stderr, "Error:mem.c: mmap cannot allocate space\n");
        allocated_once = 0;
        return -1;
    }

    allocated_once = 1;

    // for double word alignment and end mark
    allocsize -= 8;

    // Initially there is only one big free block in the heap.
    // Skip first 4 bytes for double word alignment requirement.
    heapStart = (blockHeader *)mmap_ptr + 1;

    // Set the end mark
    endMark = (blockHeader *)((void *)heapStart + allocsize);
    endMark->size_status = 1;

    // Set size in header
    heapStart->size_status = allocsize;

    // Set p-bit as allocated in header
    // note a-bit left at 0 for free
    heapStart->size_status += 2;

    // Set the footer
    blockHeader *footer = (blockHeader *)((void *)heapStart + allocsize - 4);
    footer->size_status = allocsize;

    return 0;
}

/* 
 * Function to be used for DEBUGGING to help you visualize your heap structure.
 * Prints out a list of all the blocks including this information:
 * No.      : serial number of the block 
 * Status   : free/used (allocated)
 * Prev     : status of previous block free/used (allocated)
 * t_Begin  : address of the first byte in the block (where the header starts) 
 * t_End    : address of the last byte in the block 
 * t_Size   : size of the block as stored in the block header
 */
void dispMem()
{

    int counter;
    char status[6];
    char p_status[6];
    char *t_begin = NULL;
    char *t_end = NULL;
    int t_size;

    blockHeader *current = heapStart;
    counter = 1;

    int used_size = 0;
    int free_size = 0;
    int is_used = -1;

    fprintf(stdout,
            "*********************************** Block List **********************************\n");
    fprintf(stdout, "No.\tStatus\tPrev\tt_Begin\t\tt_End\t\tt_Size\n");
    fprintf(stdout,
            "---------------------------------------------------------------------------------\n");

    while (current->size_status != 1)
    {
        t_begin = (char *)current;
        t_size = current->size_status;

        if (t_size & 1)
        {
            // LSB = 1 => used block
            strcpy(status, "alloc");
            is_used = 1;
            t_size = t_size - 1;
        }
        else
        {
            strcpy(status, "FREE ");
            is_used = 0;
        }

        if (t_size & 2)
        {
            strcpy(p_status, "alloc");
            t_size = t_size - 2;
        }
        else
        {
            strcpy(p_status, "FREE ");
        }

        if (is_used)
            used_size += t_size;
        else
            free_size += t_size;

        t_end = t_begin + t_size - 1;

        fprintf(stdout, "%d\t%s\t%s\t0x%08lx\t0x%08lx\t%4i\n", counter, status,
                p_status, (unsigned long int)t_begin, (unsigned long int)t_end, t_size);

        current = (blockHeader *)((char *)current + t_size);
        counter = counter + 1;
    }

    fprintf(stdout,
            "---------------------------------------------------------------------------------\n");
    fprintf(stdout,
            "*********************************************************************************\n");
    fprintf(stdout, "Total used size = %4d\n", used_size);
    fprintf(stdout, "Total free size = %4d\n", free_size);
    fprintf(stdout, "Total size      = %4d\n", used_size + free_size);
    fprintf(stdout,
            "*********************************************************************************\n");
    fflush(stdout);

    return;
}

// end of myHeap.c (sp 2021)
