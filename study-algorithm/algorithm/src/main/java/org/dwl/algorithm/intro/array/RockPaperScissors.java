package org.dwl.algorithm.intro.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RockPaperScissors {
    /**
     * 1: 가위, 2: 바위, 3: 보
     * @param n
     * @param A
     * @param B
     * @return
     */

    public List<String> solve(int n, int[] A, int[] B) {
        int index = 0;
        List<String> result = new ArrayList<>();
        while (index < n) {
            int a = A[index];
            int b = B[index];

            if (a == b) {
                result.add("D");
            } else if (a == 1 && b == 3) {
                result.add("A");
            } else if (a == 2 && b == 1) {
                result.add("A");
            } else if (a == 3 && b == 2) {
                result.add("A");
            } else {
                result.add("B");
            }
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
        RockPaperScissors rockPaperScissors = new RockPaperScissors();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println("a ---");
        }

        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
            System.out.println("b ---");
        }

        var result = rockPaperScissors.solve(n, a, b);
        for (String str : result) {
            System.out.println(str);
        }
    }
}
