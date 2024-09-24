import java.util.Arrays;
import java.util.Comparator;
import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // Corner case: Any of the parameters are null
        if (a == null || key == null || c == null) {
            // Throw an error
            throw new NullPointerException("a, key, or c is null");
        }

        // Set the lo and hi variables to the first and last indices of the array
        int lo = 0;
        int hi = a.length - 1;

        // Set the index to -1 and returns it if the key is not found
        int index = -1;

        // Binary search for the key
        while (lo <= hi) {
            // Set the mid variable to the middle index of the array
            int mid = lo + (hi - lo) / 2;
            // Compare the key to the middle element of the array
            int cmp = c.compare(key, a[mid]);

            if (cmp < 0) {
                // If key less than the middle element, set the hi variable to one under middle
                hi = mid - 1;
            } else if (cmp > 0) {
                // If key greater than the middle element, set the lo variable to one over middle
                lo = mid + 1;
            } else {
                // If the key is equal to the middle element, set the index to the middle element
                index = mid;
                // Set the hi variable to one under middle
                hi = mid - 1;
            }
        }

        return index;
    }

    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // Corner case: Any of the parameters are null
        if (a == null || key == null || c == null) {
            // Throw an error
            throw new NullPointerException("a, key, or c is null");
        }

        // Set the lo and hi variables to the first and last indices of the array
        int lo = 0;
        int hi = a.length - 1;

        // Set the index to -1 and returns it if the key is not found
        int index = -1;

        // Binary search for the key
        while (lo <= hi) {
            // Set the mid variable to the middle index of the array
            int mid = lo + (hi - lo) / 2;
            // Compare the key to the middle element of the array
            int cmp = c.compare(key, a[mid]);

            if (cmp < 0) {
                // If key less than the middle element, set the hi variable to one under middle
                hi = mid - 1;
            } else if (cmp > 0) {
                // If key greater than the middle element, set the lo variable to one over middle
                lo = mid + 1;
            } else {
                // If the key is equal to the middle element, set the index to the middle element
                index = mid;
                // Set the lo variable to one over middle
                lo = mid + 1;
            }
        }

        return index;
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
