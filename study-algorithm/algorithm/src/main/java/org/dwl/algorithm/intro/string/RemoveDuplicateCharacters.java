package org.dwl.algorithm.intro.string;

import java.util.Scanner;

// 6. 중복문자 제거
public class RemoveDuplicateCharacters {

    public String solve(String str) {
        String answer = "";
        for (int i = 0; i < str.length(); i++) {
            if (i == str.indexOf(str.charAt(i))) {
                answer += str.charAt(i);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        RemoveDuplicateCharacters removeDuplicateCharacters = new RemoveDuplicateCharacters();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(removeDuplicateCharacters.solve(str));
    }
}
