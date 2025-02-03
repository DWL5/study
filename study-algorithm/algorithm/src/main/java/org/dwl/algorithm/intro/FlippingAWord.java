package org.dwl.algorithm.intro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlippingAWord {

    public List<String> solve(int n, String[] str) {
        ArrayList<String> answer = new ArrayList<>();
        for (String x : str) {
            String tmp = new StringBuilder(x).reverse().toString();
            answer.add(tmp);
        }

        return answer;
    }

    public static void main(String[] args) {
        FlippingAWord flippingAWord = new FlippingAWord();
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
