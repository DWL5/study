package org.dwl.algorithm.intro;

import java.util.Scanner;

public class WordInSentence {

    public String solve(String str) {
        String answer = "";
        int m = Integer.MIN_VALUE;
        String[] s = str.split(" ");
        for (String x : s) {
            if (answer.length() < x.length()) {
                answer = x;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        WordInSentence wordInSentence = new WordInSentence();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(wordInSentence.solve(str));
    }
}
