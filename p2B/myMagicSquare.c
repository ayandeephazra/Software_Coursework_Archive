///////////////////////////////////////////////////////////////////////////////
//
// Copyright 2020 Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission, CS 354 Spring 2021, Deb Deppeler
//
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
// Main File:        myMagicSquare.c
// This File:        myMagicSquare.c
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

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structure that represents a magic square
typedef struct
{
    int size;           // dimension of the square
    int **magic_square; // pointer to heap allocated magic square
} MagicSquare;

/* TODO:
 * Prompts the user for the magic square's size, reads it,
 * checks if it's an odd number >= 3 (if not display the required
 * error message and exit), and returns the valid number.
 */
int getSize()
{
    // size to be found
    int size;
    printf("Enter magic square's size (odd integer >=3)\n");

    scanf("%d", &size);

    if (size >= 3 && size % 2 == 1)
    {
        return size;
    }

    else
    {
        if (size % 2 == 0)
        {
            printf("%s\n", "Magic square size must be odd.");
            exit(1);
        }
        else if (size < 3)
        {
            printf("%s\n", "Magic square size must be >= 3.");
            exit(1);
        }
    }
    return 0;
}

/* TODO:
 * Makes a magic square of size n using the alternate 
 * Siamese magic square algorithm from assignment and 
 * returns a pointer to the completed MagicSquare struct.
 *
 * n the number of rows and columns
 */
MagicSquare *generateMagicSquare(int n)
{

// dynamic allocation of MagicSquare variable
    MagicSquare *square = malloc(sizeof(MagicSquare));
    // if null, print error message, exit
    if (square == NULL)
    {
        printf("malloc() operation failed.\n");
        exit(1);
    }
    // setting the size of the sqaure
    square->size = n;

    // allocating memory dynamically for the magic square
    int **arr = malloc(n * sizeof(int *));
    // if null, print error message, exit
    if (arr == NULL)
    {
        printf("malloc() operation failed.\n");
        exit(1);
    }

    // each row goes to an integer array
    for (int i = 0; i < n; i++)
    {
        *(arr + i) = malloc(sizeof(int) * n);
        // if null, print error message, exit
        if ((arr + i) == NULL)
        {
            printf("malloc() operation failed.\n");
            exit(1);
        }
    }

    // defaulting all of them to 0
    for (int a = 0; a < n; a++)
    {
        for (int b = 0; b < n; b++)
        {
            *(*(arr + a) + b) = 0;
        }
    }
    // Treat 0 as "empty" state, will be filled in later

    // Start at last column's middle row
    int i = (n / 2);
    int j = (n - 1);
    for (int val = 1; val <= (n * n); val++)
    {
        // val is used as iterating variable to represent "value"
        // between 1 and n^2
        *(*(arr + i) + j) = val;

        // Bottom right corner
        if (i == n - 1 && j == n - 1)
        {
            // empty case
            if (**arr == 0)
            {
                // next square is 0,0
                i = 0;
                j = 0;
            }
            // filled case
            else
            {
                // next square is 0,j
                i = 0;
                j = j;
            }
        }

        // Bottom border row
        else if (i == n - 1)
        {
            // empty case
            if (*(*(arr + 0) + j + 1) == 0)
            {
                // next square is 0,j+1
                i = 0;
                j = j + 1;
            }
            // filled case
            else
            {
                // next square is 0,j
                i = 0;
                j = j;
            }
        }

        // Right border column
        else if (j == n - 1)
        {
            // empty case
            if (*(*(arr + i + 1) + 0) == 0)
            {
                // next square is i+1,0
                i = i + 1;
                j = 0;
            }
            // filled case
            else
            {
                // next square is i+1,j
                i = i + 1;
                j = j;
            }
        }

        // General Default Case if nothing else works
        else
        {
            // empty case
            if (*(*(arr + i + 1) + j + 1) == 0)
            {
                // next square is i+1,j+1
                i = i + 1;
                j = j + 1;
            }
            // filled case
            else
            {
                // next square is i+1,j
                i = i + 1;
                j = j;
            }
        }
    }
    // Assigning the pointer to the magic square to the struct
    square->magic_square = arr;

    return square;
}

/* TODO:  
 * Opens a new file (or overwrites the existing file)
 * and writes the square in the specified format.
 *
 * magic_square the magic square to write to a file
 * filename the name of the output file
 */
void fileOutputMagicSquare(MagicSquare *magic_square, char *filename)
{
    // Create a new file
    FILE *fp = fopen(filename, "w+");
    if (fp == NULL)
    {
        printf("Can't open the file for reading.\n");
        exit(1);
    }

    // Writing
    fprintf(fp, "%d\n", magic_square->size);
    for (int i = 0; i < magic_square->size; i++)
    {
        for (int j = 0; j < magic_square->size; j++)
        {
            fprintf(fp, "%d", *(*(magic_square->magic_square + i) + j));
            if (j != (magic_square->size) - 1)
            {
                fprintf(fp, ",");
            }
        }
        fprintf(fp, "\n");
    }

    // Closing
    if (fclose(fp) != 0)
    {
        printf("Error while closing the file.\n");
        exit(1);
    }
}

/* TODO:
 * Generates a magic square of the user specified size and
 * output the quare to the output filename
 */
int main(int argc, char *argv[])
{
    // TODO: Check input arguments to get output filename
    if (argc != 2)
    {
        printf("Usage: .myMagicSqaure <output_filename>\n");
        exit(1);
    }

    // TODO: Get magic square's size from user
    int size = getSize();

    // TODO: Generate the magic square
    MagicSquare *square = generateMagicSquare(size);

    // TODO: Output the magic square
    fileOutputMagicSquare(square, *(argv + 1));

    // Free up allocated memory
    // first rows
    for (int i = 0; i < square->size; i++)
    {
        free(*((square->magic_square) + i));
    }
    // then component of struct
    free(square->magic_square);
    // then struct
    free(square);

    return 0;
}

//		s21		myMagicSquare.c
