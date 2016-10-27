package com.bazlur;

/**
 * @author Bazlur Rahman Rokon
 * @since 10/28/16.
 */
public class MainApp {
	public static void main(String[] args) {
		SpellChecker checker = new SpellChecker();
		checker.addCorrectWord("ইতিহাস", 1);
		checker.addCorrectWord("বাংলাদেশ", 1);
		checker.addCorrectWord("জন্য", 1);

		String input = "বাংলদশ";
		String correct = checker.correct(input); // Output:  বাংলাদেশ

		System.out.println(correct);
	}
}
