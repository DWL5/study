package org.dwl.algorithm.intro.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student {
    /**
     * 선생님이 N명의 학생을 일렬로 세웠습니다. 일렬로 서 있는 학생의 키가 앞에서부터 순서대로 주어질 때, 맨 앞에
     * 서 있는 선생님이 볼 수 있는 학생의 수를 구하는 프로그램을 작성하세요.
     * 어떤 학생이 자기 앞에서 있는 학생들보다 크면 그 학생은 보이고 작거나 같으면 보이지 않습니다.
     */

    public int solve(int n, int[] arr) {
        int answer = 0;
        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                answer++;
                continue;
            }

            if (arr[i] > prev) {
                answer++;
                prev = arr[i];
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        Student student = new Student();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int result = student.solve(n, arr);
        System.out.println(result);
    }
}
