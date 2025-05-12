package org.dwl.algorithm.intro.array;

import java.util.ArrayList;
import java.util.Scanner;

public class ReversePrimeNumber {

    private boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }

        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> solve(int n, int[] arr) {
        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0 ; i< n; i++) {
            int tmp = arr[i];
            int res = 0;
            while (tmp > 0) {
                int t = tmp % 10;
                res = res * 10 + t;
                tmp /= 10;
            }

            if (isPrime(res)) {
                answer.add(res);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        ReversePrimeNumber reversePrimeNumber = new ReversePrimeNumber();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int [] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        for (int x : reversePrimeNumber.solve(n, arr)) {
            System.out.print(x + " ");
        }
    }
}
