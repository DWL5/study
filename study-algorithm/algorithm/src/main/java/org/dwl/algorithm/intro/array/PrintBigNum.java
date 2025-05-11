package org.dwl.algorithm.intro.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrintBigNum {
    /**
     * 큰수 출력하기
     * N(1<= N <= 100)개의 정수를 입력받아, 자신의 바로 앞 수보다 큰 수만 출력하는 프로그램을 작성하세요.
     * 첫 번째 수는 무조건 출력
     */

    public List<Integer> solve(int n, int[] arr) {
        ArrayList<Integer> answer = new ArrayList<>();
        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                answer.add(arr[i]);
                prev = arr[i];
                continue;
            }

            if (arr[i] > prev) {
                answer.add(arr[i]);
            }
            prev = arr[i];
        }

        return answer;
    }

    public static void main(String[] args) {
        PrintBigNum printBigNum = new PrintBigNum();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        for (int x : printBigNum.solve(n, arr)) {
            System.out.print(x + " ");
        }
    }
}
