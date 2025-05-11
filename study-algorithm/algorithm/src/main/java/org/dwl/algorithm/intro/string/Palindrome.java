package org.dwl.algorithm.intro.string;

import java.util.Scanner;

public class Palindrome {

    public String solve(String str) {
        String strUpper = str.toUpperCase();
        for (int i = 0; i < strUpper.length() / 2; i++) {
            if (strUpper.charAt(i) != strUpper.charAt(str.length() - 1 - i)) {
                return "NO";
            }
        }
        return "YES";
    }


    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(palindrome.solve(str));
    }
}
