////////////////////////////////////////////////////////////////////////////////
// Main File:        mySigHandler.c
// This File:        mySigHandler.c
// Other Files:      division.c, sendsig.c
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
#include <time.h>
#include <unistd.h>

int timer = 3;
int countSIGUSR1 = 0;

/*
 * Handles SIGALRM. Checks time and sets it if not.
 * 
 * no pre conditions
 * no parameters
 * no return value
 */
void handler_SIGALRM() {
  time_t currentTime; 
  // check time if correct and set if incorrect
  if (time(&currentTime) > time(NULL)) {
	  time(&currentTime);
  }
 
  // print PID and time
  printf("PID: %d CURRENT TIME: %s", getpid(), ctime(&currentTime));
  alarm(timer); // set another alarm at end of 3s
}

/*
 * Handles SIGINT. Prints handler message and number of times
 * SIGUSR1 was handled.
 * 
 * no pre conditions
 * no parameters
 * no return value
 */
void handler_SIGINT() {
  printf("\nSIGINT handled.\n");
  printf("SIGUSR1 was handled %d times. Exiting now.\n", countSIGUSR1);
  exit(0);
}

/*
 * Handles SIGUSR1 and message that it was handled.
 * increments countSIGUSR1.
 * 
 * no pre conditions
 * no parameters
 * no return value
 */
void handler_SIGUSR1() {
  printf("SIGUSR1 handled and counted!\n");
  countSIGUSR1++; // increment SIGUSR1 signal count
}

/*
 * Main method to create signals. Creates sigalarm and sigint and
 * sigusr1. Also runs an infinite loop.
 *
 * no pre conditions
 * no parameters
 * return int value of 0
 */
int main() {
  // Creates new sigaction and sets it to 0
  struct sigaction sigalrm;
  memset(&sigalrm, 0, sizeof(sigalrm));
  // update the handler
  sigalrm.sa_handler = handler_SIGALRM;

  // binds SIGALRM to handler
  if (sigaction(SIGALRM, &sigalrm, NULL) != 0) {
    printf("Error binding SIGALRM handler\n");
    exit(1);
  }

  // Creates new sigaction and sets it to 0
  struct sigaction sigint;
  memset(&sigint, 0, sizeof(sigint));
  // update the handler
  
  sigint.sa_handler = handler_SIGINT;

  // binds SIGINT to handler
  if (sigaction(SIGINT, &sigint, NULL) != 0) {
    printf("Error binding SIGINT handler\n");
    exit(1);
  }

  // Creates new sigaction and sets it to 0
  struct sigaction sigusr1;
  memset(&sigusr1, 0, sizeof(sigusr1));
  // update the handler
  sigusr1.sa_handler = handler_SIGUSR1;

  // binds SIGUSR1 to handler
  if (sigaction(SIGUSR1, &sigusr1, NULL) != 0) {
    printf("Error binding SIGUSR1 handler\n");
    exit(1);
  }

  printf(
      "PID and time print every 3 seconds.\nType Ctrl-C to end the "
      "program.\n");
  alarm(timer);  // the first alarm and then access method.

  // infinite loop
  while (1) {
  }

  return 0;
}
