import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    // one private instance variable storing an array of Term objects
    private Term[] termsArray;

    // Initializes the data structure from the given array of terms.
    // runtime is nlogn from Array.sort default method
    public Autocomplete(Term[] terms) {
        // Error Handling
        if (terms == null) {
            throw new IllegalArgumentException();
        }
        // Create defensive array to prevent manipulation of terms
        termsArray = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            // error handling for invalid input
            if (terms[i] == null) {
                throw new IllegalArgumentException();
            }
            // defensive copying
            termsArray[i] = terms[i];
        }
        Arrays.sort(termsArray); // sorts by lexographical order
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    // use binary search
    // returns an array of Term objects containing all terms that contain the String prefix argument in their search queries
    // parameter is String prefix which is manually searched by the user
    public Term[] allMatches(String prefix) {
        // Error handling for invalid inputs
        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        // find the first index and last index of a term with a search query that matches the prefix
        int firstIndex = BinarySearchDeluxe.firstIndexOf(termsArray, new Term(prefix, 0),
                                                         Term.byPrefixOrder(prefix.length()));
        int lastIndex = BinarySearchDeluxe.lastIndexOf(termsArray, new Term(prefix, 0),
                                                       Term.byPrefixOrder(prefix.length()));
        // if no such matches exist, then we retun empty array of Term objects
        if (firstIndex == -1 || lastIndex == -1) {
            return new Term[0];
        }

        // otherwise, store all matches in new array
        Term[] matches = new Term[lastIndex - firstIndex + 1];
        int index = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            matches[index] = termsArray[i];
            index++;
        }

        // sort by descending weight
        // runtime is mlogm
        // comparator specifies that matches array will be sorted by descending weight
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    // takes parameter of type String prefix specified by user
    public int numberOfMatches(String prefix) {
        // Error handling for invalid input
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        int firstIndex = BinarySearchDeluxe.firstIndexOf(termsArray, new Term(prefix, 0),
                                                         Term.byPrefixOrder(prefix.length()));
        int lastIndex = BinarySearchDeluxe.lastIndexOf(termsArray, new Term(prefix, 0),
                                                       Term.byPrefixOrder(prefix.length()));
        // if no matches, return 0
        if (lastIndex == -1) return 0;
        // else return the number of matches between the first and last index found to account for all matches
        return lastIndex - firstIndex + 1;
    }


    // unit testing
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}
