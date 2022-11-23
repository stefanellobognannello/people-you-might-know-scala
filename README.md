# people-you-might-know-scala


A program in Spark that implements a simple “People You Might Know” social network friendship recommendation algorithm. If two people have a lot of mutual friends, then the system should recommend they connect with each other.


The input file, contained in the project, contains the adjacency list and has multiple lines in the following format: where is a unique integer ID corresponding to a unique user and is a comma-separated list of unique IDs corresponding to the friends of the user with the unique ID .


The output is in the following format: where is a unique ID corresponding to a user and Recommendations is a comma-separated list of unique IDs corresponding to the algorithm’s recommendation of people that might know, ordered by decreasing number of mutual friends.
