package org.dwl.algorithm.intro.string;

import java.util.Scanner;

// 2. 대소문자 변환
public class ChangeLowerUpper {

    public String solve(String str) {
        StringBuilder answer = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                answer.append(Character.toLowerCase(c));
            } else {
                answer.append(Character.toUpperCase(c));
            }
        }

        return answer.toString();
    }

    public static void main(String[] args) {
        ChangeLowerUpper changeLowerUpper = new ChangeLowerUpper();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        System.out.println(changeLowerUpper.solve(str));
    }
}
