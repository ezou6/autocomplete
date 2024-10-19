import java.util.Comparator;

public class Term implements Comparable<Term> {

    private String searchQuery; // stores String query of Term
    private long queryWeight; // stores weight or "relevance" of Term being searched

    // Initializes a term with the given query string and weight.
    // Two parameters: String search quey and long (numeric) weight
    public Term(String query, long weight) {

        // Error Handling for invalid arguments
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null.");
        }
        else if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        // initialize constructor values to passed in arguments
        searchQuery = query;
        queryWeight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        // implementation hidden in private QueryComparator abiding by Comparator Interface contract
        return new QueryComparator();
    }

    private static class QueryComparator implements Comparator<Term> {
        // compare method for specified data types takes two parameters each of the Term typo
        public int compare(Term t1, Term t2) {
            return (int) (t2.queryWeight - t1.queryWeight);
            // By descending order, we want to display the term with largest weight first
            // and terms with smallest weights last. Traditionally, with ascending order,
            // we would calculate t1.queryWeight - t2.queryWeight. But if we find that t2
            // has a greater weight than term 2, then we would return a positive number and consequently,
            // term 2 would be placed closer to the top than term 1
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query
    // parameter is integer r specifying how many characters of each query are being compared
    public static Comparator<Term> byPrefixOrder(int r) {
        // Error handling for invalid input
        if (r < 0) {
            throw new IllegalArgumentException("r cannot be negative.");
        }
        // must pass r parameter
        return new FirstRQueryComparator(r);
    }


    private static class FirstRQueryComparator implements Comparator<Term> {
        // private constructor for FirstRQueryComparator has one instance variable that will store passed in argument r
        private int r;

        public FirstRQueryComparator(int rChars) {
            // assign instance variable to passed in argument
            r = rChars;
        }

        public int compare(Term term1, Term term2) {
            int index = 0;

            // This while loop will continue to iterate as long as the current character doesn't surpass the number of
            // characters specified by the length of the prefix and the character of the first and second term at the index
            // are equal to each other.
            while (index < r) {
                // if the index surpasses the length of one or the other or both search queries (up to r first characters
                // are equal like "tra" and "traditional") then we will order the query with characters following the prefix
                // as coming after the query with an index surpassing its length
                //
                if (index >= term1.searchQuery.length() || index >= term2.searchQuery.length()) {
                    return term1.searchQuery.length() - term2.searchQuery.length();
                }
                // look for the first different character
                char t1char = term1.searchQuery.charAt(index);
                char t2char = term2.searchQuery.charAt(index);
                // term1char's first differing character comes after term2char's
                if (t1char > t2char) {
                    return 1;
                }
                // term1char's first differing character comes before term2char's
                else if (t1char < t2char) {
                    return -1;
                }
                index++;
            }
            // if search queries are exact same (including length), then return 0
            return 0;
        }

    }

    // Compares the two terms in lexicographic order by query.
    // Default compare method
    // takes one parameter of type Term which will be compared to the current Term
    public int compareTo(Term that) {
        // exploits built-in String compareTO() method
        return searchQuery.compareTo(that.searchQuery);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    // By implementing toString(), we can just use System.out.println(Autocomplete);
    public String toString() {
        return queryWeight + "\t" + searchQuery;
    }

    // unit testing (required)
    public static void main(String[] args) {

        Term p = new Term("AACGG", 4);
        Term q = new Term("AACGGCABDEKDIF", 0);
        System.out.println(
                p.byPrefixOrder(8).compare(p, q)); // expected: -9 aka char differing b/w p and q


        // Unit Testing - constructor
        Term test1 = new Term("abc", 2);
        Term test2 = new Term("xyz", 5);
        Term test3 = new Term("xyz", 2);
        Term test4 = new Term("train", 10);
        Term test5 = new Term("traditional", 1);
        Term test6 = new Term("xyzefg", 20);

        if (test1.searchQuery != "abc") {
            System.out.println("Failed!");
        }
        if (test2.queryWeight != 5) {
            System.out.println("Failed!");
        }
        // Unit Testing - toString()

        System.out.println(test1);
        // Expected result:
        // 2    "abc"
        // Passed!

        // Unit Testing - default compare based off lexographical order

        // Result should be negative since "abc" comes before "xyz" lexographically
        if (test1.compareTo(test2) >= 0) {
            System.out.println("Failed!");
        }
        // Result should be negative since "train" comes after "abc" lexographically
        if (test4.compareTo(test1) < 0) {
            System.out.println("Failed!");
        }
        // Equal since "xyz" is equal to "xyz"
        if (test2.compareTo(test3) != 0) {
            System.out.println("Failed!");
        }

        // Unit Testing - byReverseWeightOrder Comparator

        // Positive
        if (byReverseWeightOrder().compare(test1, test2) != 3) {
            System.out.println("Failed!");
        }
        // expect 5-2 = 3
        // positive number means that we expect test1 to come after term2

        // Negative
        if (byReverseWeightOrder().compare(test4, test3) != -8) {
            System.out.println("Failed!");
        }
        // expect 2-10 = -8
        // negative number means we expect test4 to come before test3

        // 0
        if (byReverseWeightOrder().compare(test1, test3) != 0) {
            System.out.println("Failed!");
        }

        // Unit Testing - ByPrefixOrder Comparator
        // Expect positive since "train" comes after "traditional"
        if (byPrefixOrder(4).compare(test4, test5) <= 0) {
            System.out.println("Failed!");
        }


        // Expect 0 since the first three characters of "xyz" and "xyzefg" are both "xyz"
        if (byPrefixOrder(3).compare(test3, test6) != 0) {
            System.out.println("Failed!");
        }

        // Expect negative since "abc" comes before "xyz"
        if (byPrefixOrder(3).compare(test1, test2) > 0) {
            System.out.println("Failed!");
        }


    }

}
