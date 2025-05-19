package org.dwl.algorithm.intro.twopointer;

import java.util.ArrayList;
import java.util.Scanner;

public class MergeTwoArray {

    /**
     * 두배열 합치기
     * @param n
     * @param m
     * @param a
     * @param b
     * @return
     */
    public ArrayList<Integer> solution(int n, int m, int[] a, int[] b) {
        ArrayList<Integer> answer = new ArrayList<>();
        int p1 = 0, p2 = 0;
        while (p1 < n && p2 < m) {
            if (a[p1] < b[p2]) {
                answer.add(a[p1]);
                p1++;
            } else {
                answer.add(b[p2]);
                p2++;
            }
        }

        while (p1 < n) {
            answer.add(a[p1++]);
        }

        while (p2 < m) {
            answer.add(b[p2++]);
        }

        return answer;
    }

    public static void main(String[] args) {
        MergeTwoArray mergeTwoArray = new MergeTwoArray();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        int m = sc.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = sc.nextInt();
        }

        for (int x : mergeTwoArray.solution(n, m, a, b)) {
            System.out.println(x + " ");
        }
    }
}
