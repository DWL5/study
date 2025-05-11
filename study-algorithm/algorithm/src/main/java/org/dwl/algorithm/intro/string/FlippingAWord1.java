package org.dwl.algorithm.intro.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 4.단어 뒤집기
public class FlippingAWord1 {

    public List<String> solve(int n, String[] str) {
        ArrayList<String> answer = new ArrayList<>();
        for (String x : str) {
            char[] s = x.toCharArray();
            int lt = 0;
            int rt = x.length() - 1;

            while (lt < rt) {
                char tmp = s[lt];
                s[lt] = s[rt];
                s[rt] = tmp;
                lt++;
                rt--;
            }

            answer.add(String.valueOf(s));
        }

        return answer;
    }

    public static void main(String[] args) {
        FlippingAWord1 flippingAWord = new FlippingAWord1();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] str = new String[n];
        for (int i = 0; i < n; i++) {
            str[i] = scanner.next();
        }

        for (String x : flippingAWord.solve(n, str)) {
            System.out.println(x);
        }
    }
}
