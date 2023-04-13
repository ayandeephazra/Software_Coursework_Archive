////////////////////////////////////////////////////////////////////////////////
// Main File:        sendsig.c
// This File:        sendsig.c
// Other Files:      division.c, mySigHandler.c
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

/*
 * The main fuction to send signals in through.
 * Acts as the function that sends and kills other
 * processes.
 * Handles "u" or user processes and "i" or
 * interrupt processes. If none of the above is
 * entered then prints usage message.
 * 
 * no pre conditions
 * parameter argc - number of arguments
 * paramter argv - array of arguments
 * return int value of 0
 */
int main(int argc, char *argv[]) {
  // show correct usage if incorrect
  if (argc != 3 || strlen(argv[1]) != 2) {
    printf("Usage: <signal type> <pid>\n");
    exit(1);
  }

  int pid = atoi(argv[2]);  // get pid
  char sig = argv[1][1];    // get sig flag

  // if sig is user signal
  if (sig == 'u') {
    // kill process and store result
    int killResult = kill(pid, SIGUSR1);
    // if not 0, error
    if (killResult) {
      printf("ERROR sending signal: SIGUSR1\n");
    }
    // if sig is interrupt signal
  } else if (sig == 'i') {
    // kill process and store result
    int killResult = kill(pid, SIGINT);
    // if not 0, error
    if (killResult) {
      printf("ERROR sending signal: SIGINT\n");
    }
    // if sig is neither of the above
  } else {
    // usage message
    printf("Usage: <signal type> <pid>\n");
    exit(1);
  }

  // return 0 if everything functions correctly
  return 0;
}
