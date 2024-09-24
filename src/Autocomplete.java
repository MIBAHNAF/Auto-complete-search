import java.util.Arrays;
import java.util.Comparator;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    Term[] terms; // Array of terms

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        // Corner case: Terms is null
        if (terms == null) {
            // Throw an error
            throw new NullPointerException("terms is null");
        }

        this.terms = terms; // Set the terms
        Arrays.sort(this.terms); // Sort the terms in lexicographic order
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        // Throw an error if prefix is null
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }

        // Corner case: There are no terms
        if (this.terms.length == 0) {
            return this.terms;
        }

        // Create a term with the prefix
        Term term = new Term(prefix);
        // Create a comparator for comparing terms by their prefixes of length r
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        // Find the first and last matches
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(this.terms, term, prefixOrder);

        // If there are no matches, return an empty array
        Term[] matches = new Term[j - i + 1];
        // Copy the matches into a new array
        for (int k = 0; k < matches.length; k++) {
            matches[k] = terms[i + k];
        }

        // Sort the matches in descending order of their weights
        Arrays.sort(matches, Term.byReverseWeightOrder());

        return matches;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        // throw an error if prefix is null
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }

        return this.allMatches(prefix).length;
    }


    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
