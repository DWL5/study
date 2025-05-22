package org.dwl.algorithm.intro.sort;

import java.util.Scanner;

public class InsertSort {

    public int[] insertSort(int[] arr) {
        for(int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
        }

        return arr;
    }


    public static void main(String[] args) {
        InsertSort insertSort = new InsertSort();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        for (int x : insertSort.insertSort(arr)) System.out.println(x + " ");
    }
}
