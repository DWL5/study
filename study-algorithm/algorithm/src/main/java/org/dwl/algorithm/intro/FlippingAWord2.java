package org.dwl.algorithm.intro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlippingAWord2 {

    public List<String> solve(int n, String[] str) {
        ArrayList<String> answer = new ArrayList<>();
        for (String x : str) {
            char[] s = x.toCharArray();
            int lt = 0;
            int rt = x.length() - 1;

            while (lt < rt) {
                char tmpLt = s[lt];
                char tmpRt = s[rt];
                if (Character.isAlphabetic(tmpLt) && Character.isAlphabetic(tmpRt)) {
                    s[lt] = tmpRt;
                    s[rt] = tmpLt;
                }
                lt++;
                rt--;
            }

            answer.add(String.valueOf(s));
        }

        return answer;
    }

    public static void main(String[] args) {
        FlippingAWord2 flippingAWord = new FlippingAWord2();
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
