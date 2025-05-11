package org.dwl.algorithm.intro.string;

import java.util.Scanner;

// 1. 문자 찾기
public class SearchCharacter {
    public int solve(String str, char t) {
        int answer = 0;
        str = str.toUpperCase();
        t = Character.toUpperCase(t);

        for (char c : str.toCharArray()) {
            if (c == t) {
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        SearchCharacter searchCharacter = new SearchCharacter();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        char c = scanner.next().charAt(0);
        System.out.println(searchCharacter.solve(str, c));
    }
}
