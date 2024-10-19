import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException();
        }
        int low = 0, high = a.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) {
                high = mid - 1;
            }
            else if (compare > 0) {
                low = mid + 1;
            }
            else {
                high = mid;
            }
        }

        if (high >= 0 && comparator.compare(key, a[high]) == 0) {
            return high;
        }
        return -1;

    }


    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException();
        }
        int low = 0, high = a.length - 1;
        while (low < high) {
            int mid = low + (high - low + 1) / 2;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) {
                high = mid - 1;
            }
            else if (compare > 0) {
                low = mid + 1;
            }
            else {
                low = mid;
            }
        }

        if (low < a.length && comparator.compare(key, a[low]) == 0) {
            return low;
        }
        return -1;
    }


    // unit testing (required)
    public static void main(String[] args) {
        String[] a = { "B", "B", "C", "G", "G", "T" };
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index = BinarySearchDeluxe.firstIndexOf(a, "A", comparator);
        System.out.println("first index: " + index);
        index = BinarySearchDeluxe.lastIndexOf(a, "A", comparator);
        System.out.println("last index: " + index);


        int n = Integer.parseInt(args[0]);
        int tempIndex = 0;
        Integer[] testArray = new Integer[5 * n];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 5; j++) {
                testArray[tempIndex++] = i;
            }
        }
        int key = StdRandom.uniform(1, n + 1);
        System.out.println("key's value is " + key);

        // Expected result of firstIndexOf w/ linear search
        int expectedFirstIndex = -1;
        int expectedLastIndex = -1;
        for (int i = 0; i < testArray.length; i++) {
            if (testArray[i] == key) {
                if (expectedFirstIndex == -1) {
                    expectedFirstIndex = i;
                }
                expectedLastIndex = i;
            }
        }
        int findIndex = firstIndexOf(testArray, key, Integer::compareTo);
        if (findIndex != expectedFirstIndex) {
            System.out.println("Error!");
        }


        int findIndex2 = lastIndexOf(testArray, key, Integer::compareTo);
        if (findIndex2 != expectedLastIndex) {
            System.out.println("Error!");
        }
    }
}

