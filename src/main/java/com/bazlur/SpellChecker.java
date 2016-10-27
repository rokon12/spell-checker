package com.bazlur;

import java.util.*;

/**
 * @author Bazlur Rahman Rokon
 * @since 10/28/16.
 */
public class SpellChecker {
	private static final Map<String, Integer> DICTIONARY = new HashMap<>();
	private static final char START = '\u0985';
	private static final char END = '\u09DF';


	public void addCorrectWord(String word, int rank) {
		DICTIONARY.put(word, rank);
	}

	public String correct(String word) {

		if (DICTIONARY.containsKey(word)) {
			return word;
		}

		// getting all possible edits of input word
		List<String> edits = edits(word);

		// Here we can either iterate through list of edits and find the 1st word that is in dictionary and return.
		// Or we can go to below logic to return the word with most weight (that we would have already stored in dictionary map)

		// map to hold the possible matches
		Map<Integer, String> candidates = new HashMap<>();

		// keep checking the dictionary and populate the possible matches
		// it stores the count (or weight) of word and the actual word
		edits.stream()
			.filter(DICTIONARY::containsKey)
			.forEach(s -> candidates.put(DICTIONARY.get(s), s));

		// one we have found possible matches - we want to return most popular (most weight) word
		if (candidates.size() > 0) {
			return candidates.get(Collections.max(candidates.keySet()));
		}


		// If none matched.
		// We will pick every word from edits and again do edits (using same previous logic) on that to see if anything matches
		// We don't do recursion here because we don't the loop to continue infinitely if none matches
		// If even after doing edits of edits, we don't find the correct word - then return.

		edits.stream()
			.forEach(s -> edits(s)
				.stream()
				.filter(DICTIONARY::containsKey)
				.forEach(ns -> candidates.put(DICTIONARY.get(ns), ns)));

		return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : word;
	}

	// Word EDITS
	// Getting all possible corrections c of a given word w.
	// It is the edit distance between two words: the number of edits it would take to turn one into the other

	private static List<String> edits(String word) {
		if (StringUtils.isEmpty(word)) {

			return Collections.emptyList();
		}

		List<String> list = new ArrayList<>();

		String w;

		// deletes (remove one letter)
		for (int i = 0; i < word.length(); i++) {
			w = word.substring(0, i) + word.substring(i + 1); // ith word skipped
			list.add(w);
		}

		// transpose (swap adjacent letters)
		// note here i is less than word.length() - 1
		for (int i = 0; i < word.length() - 1; i++) { // < w.len -1 :: -1 because we swapped last 2 elements in go.
			w = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2); // swapping ith and i+1'th char
			list.add(w);
		}

		// replace (change one letter to another)
		for (int i = 0; i < word.length(); i++) {
			for (char c = START; c <= END; c++) {
				w = word.substring(0, i) + c + word.substring(i + 1); // replacing ith char with all possible alphabets
				list.add(w);
			}
		}

		// insert (add a letter)
		// note here i is less than and EQUAL to
		for (int i = 0; i <= word.length(); i++) { // <= because we want to insert the new char at the end as well
			for (char c = START; c <= END; c++) {
				w = word.substring(0, i) + c + word.substring(i); // inserting new char at ith position
				list.add(w);
			}
		}

		return list;
	}
}
