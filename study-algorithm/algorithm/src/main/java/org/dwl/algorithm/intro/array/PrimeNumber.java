package org.dwl.algorithm.intro.array;

import java.util.Scanner;

public class PrimeNumber {
    /**
     * 소수
     * 에라토스테네스 체
     * 자연수 N이 입력되면 1부터 N까지의 소수의 개수를 출력하는 프로그램을 작성하세요.
     */

    public int solution(int N) {
        int answer = 0;
        int[] ch = new int[N + 1];

        for (int i = 2; i <= N; i++) {
            if (ch[i] == 0) {
                answer++;
                for (int j = i; j <= N; j = j+i) {
                    ch[j] = 1;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        PrimeNumber primeNumber = new PrimeNumber();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(primeNumber.solution(n));
    }
}
