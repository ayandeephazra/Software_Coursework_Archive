////////////////////////////////////////////////////////////////////////////////
// Main File:        division.c
// This File:        division.c
// Other Files:      mySigHandler.c, sendsig.c
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

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int divCounter = 0;

/*
 * Handler for the SIGFPE signal. Prints the error message for
 * divide by zero and the number of divisions successfully done
 * up until that point. And a last message saying the program will
 * be terminated.
 * 
 * no pre conditions
 * no parameters
 * no return value
 */
void handler_SIGFPE() {
  printf("Error: a division by 0 operation was attempted.");
  printf("\nTotal number of operations completed successfully: %d\n", divCounter);
  printf("The program will be terminated.\n");
  exit(0);
}

/*
 * Handler for the SIGINT signal. Prints the number of divisions successfully done
 * up until that point. And a last message saying the program will
 * be terminated.
 * 
 * no pre conditions
 * no parameters
 * no return value
 */
void handler_SIGINT() {
  printf("\nTotal number of operations completed successfully: %d\n", divCounter);
  printf("The program will be terminated.\n");
  exit(0);
}

/*
 * The main fuction to perform divisons in. Also handles the SIGFPE and
 * SIGINT handlers.
 * 
 * no pre conditions
 * no parameters
 * return int value of 0
 */
int main() {
  // Creates new sigaction struct and sets it to 0 with memset
  struct sigaction sigfpe;
  memset(&sigfpe, 0, sizeof(sigfpe));
  // updating handler
  sigfpe.sa_handler = handler_SIGFPE;

  // bind the SIGFPE signal to handler
  if (sigaction(SIGFPE, &sigfpe, NULL) != 0) {
    printf("Error binding to signal: SIGFPE\n");
    exit(1);
  }

  // Creates new sigaction struct and sets it to 0 with memset
  struct sigaction sigint;
  memset(&sigint, 0, sizeof(sigint));
  // updating handler
  sigint.sa_handler = handler_SIGINT;

  // bind the SIGINT signal to handler
  if (sigaction(SIGINT, &sigint, NULL) != 0) {
    printf("Error binding SIGINT handler\n");
    exit(1);
  }
  int BUFFER = 100;     // buffer for reading input
  char input1[BUFFER];  // used to read first int value
  char input2[BUFFER];  // used to read second int value

  // declare variables to be used in the remaining method
  int firstInt, secondInt;
  int result, remainder;

  while (1) {
    // prompt for ints and get the input, convert it to integer
    
    // standard atoi return value, no checking for non numeric values
    printf("Enter first integer: ");
    fgets(input1, BUFFER, stdin);
    firstInt = atoi(input1);

    // standard atoi return value, no checking for non numeric values
    printf("Enter second integer: ");
    fgets(input2, BUFFER, stdin);
    secondInt = atoi(input2);

    // compute results for result and remainder
    result = firstInt / secondInt;
    remainder = firstInt % secondInt;

    // display the same
    printf("%d / %d is %d with a remainder of %d\n", firstInt, secondInt,
           result, remainder);

    divCounter = divCounter + 1;  // increment divCounter after 
                                  // successful operation
  }

  return 0;
}
