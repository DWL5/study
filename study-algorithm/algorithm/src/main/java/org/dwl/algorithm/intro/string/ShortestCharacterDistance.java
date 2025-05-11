package org.dwl.algorithm.intro.string;

import java.util.Scanner;

// 10. 문자거리
public class ShortestCharacterDistance {
    // str1 : teachermode str2 : e
    public int[] solve(String str1, char c) {
        int[] answer = new int[str1.length()];
        int p = 1000;
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == c) {
                p = 0;
                answer[i] = p;
            } else {
                p++;
                answer[i] = p;
            }
        }

        for (int i = str1.length() - 1; i >= 0; i--) {
            if (str1.charAt(i) == c) {
                p = 0;
            } else {
                p++;
                answer[i] = Math.min(p, answer[i]);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        ShortestCharacterDistance shortestCharacterDistance = new ShortestCharacterDistance();
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.next();
        char c = scanner.next().charAt(0);
        for (int x : shortestCharacterDistance.solve(str1, c)) {
            System.out.print(x + " ");
        }
    }
}
