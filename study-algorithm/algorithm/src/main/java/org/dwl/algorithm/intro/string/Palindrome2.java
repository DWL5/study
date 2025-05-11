package org.dwl.algorithm.intro.string;

import java.util.Scanner;

public class Palindrome2 {
    public String solve(String str) {
        String reverse = new StringBuilder(str).reverse().toString();
        if (str.equalsIgnoreCase(reverse)) {
            return "YES";
        }

        return "NO";
    }


    public static void main(String[] args) {
        Palindrome2 palindrome2 = new Palindrome2();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(palindrome2.solve(str));
    }
}
