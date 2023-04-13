My partner is Nishit Saraf (nishit).

This assignment was to make a parallel zipping program which basically uses multiple threads in-order to compress every file using the rle encoding. Me and my partner started by giving every thread a single file and then using locks to make a single output.

 However, this ends up making the output not subsequent to the input of the files, as a result we decided to recurse through all of the files and use mmap to make a buffer of characters, where we ignore the '\0' character. By making different structs we were able to consider every thread as an object and the main thread as a class. We used many of the basic concurrency contructs like threads, join, wait, etc. In particular, by doing pthread_join we wait for all of the threads to end and by using locks we don't care about the concurrency issue.

We also defined many structs that help us pass data around the various functions like wzip and generate. wzip and generate together help us to decode the input and run threads to compress the input and output it. 

We did run into many "deadlocks" where there were issues with pairing up characters between subsequent files, we were not able to get that for a long time, but were able to pull through with some effort. The TA Office Hours helped a lot. One thing we did is to make a worker function that will automate the thread manufacturing process.

ON rockhopper-04/nobackup

real    0m14.040s
user    0m19.017s
sys     0m3.583s

