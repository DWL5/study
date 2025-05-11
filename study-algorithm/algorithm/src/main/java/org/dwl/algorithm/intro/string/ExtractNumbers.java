package org.dwl.algorithm.intro.string;

import java.util.Scanner;

// 9.숫자만 추출
public class ExtractNumbers {

    public String solve(String str) {
        int answer = 0;
        char[] strChar = str.toCharArray();
        for (int i = 0; i < strChar.length; i++) {
            if (Character.isDigit(strChar[i])) {
                answer = (answer * 10) + Integer.parseInt(String.valueOf(strChar[i]));
            }
        }

        return String.valueOf(answer);
    }


    public static void main(String[] args) {
        ExtractNumbers extractNumbers = new ExtractNumbers();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(extractNumbers.solve(str));
    }
}
