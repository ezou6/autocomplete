Programming Assignment 3: Autocomplete


/* *****************************************************************************
 *  Describe how your firstIndexOf() method in BinarySearchDeluxe.java
 *  finds the first index of a key that is equal to the search key.
 **************************************************************************** */




/* *****************************************************************************
 *  Suppose you know that the terms in some sample file are evenly
 *  distributed, i.e. the number of terms starting with any given
 *  letter are roughly the same (so the number of words starting with 'a'
 *  is very similar to the ones starting with 'b', and so on).
 *
 *  How would you modify your implementation of firstIndexOf/lastIndexOf
 *  to be more efficient *in practice* for this distribution of terms?
 *  More efficient in practice means that it would be faster for most
 *  keys, but in the worst case it could still require 1 + log n compares.
 *
 *  Note that you don't have to write any Java code, your answer can be
 *  a description of how you'd modify your binary search solution.
 **************************************************************************** */

Since we know that the number of terms starting with any given letter (from a to z) is evenly distributed
among the sample file array, we can divide the number of entries by 26 and sort them into buckets of uniform size.
Then, instead of starting from the middle index of the array, we start from the first index that contains the first letter
or character of the search term. For example, say we're searching for the query "cars" in an array with 2600 terms. We know that
the first index containing "c" will be at index 2600/26 * 2 (2 since c is the third letter in the alphabet and we know
that the array starts at index 0) or 200 and the last index containing 'c' as its first character must be
2600/26 * 3 - 1 (since we don't want to encroach on 'd') or 299. Then we can use firstIndexOf and lastIndexOf as implemented
in the BinarySearchDeluxe class.


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

