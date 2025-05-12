package org.dwl.algorithm.intro.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fibonacci {

    public List<Integer> solve(int n) {
        List<Integer> res = new ArrayList<>();
        res.add(1);
        res.add(1);

        for (int i = 2; i < n; i++) {
            int a = res.get(i - 2);
            int b = res.get(i - 1);
            res.add(a + b);
        }

        return res;
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> res = fibonacci.solve(n);
        for (Integer integer : res) {
            System.out.print(integer + " ");
        }
    }
}
