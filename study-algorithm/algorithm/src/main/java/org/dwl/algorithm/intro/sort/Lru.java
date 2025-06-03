package org.dwl.algorithm.intro.sort;

import java.util.Scanner;

public class Lru {
    public int[] solve(int a, int b, int[] c) {
        int[] cache = new int[a];
        for (int i = 0; i < b; i++) {
            int input = c[i];
            int index = -1;
            for (int j = 0; j < a; j++) {
                if (cache[j] == input) {
                    index = j;
                    break;
                }
            }

            if (index == -1) {
                for (int k = a - 1; k >= 1; k--) {
                    cache[k] = cache[k - 1];
                }
            } else {
                for (int k = index; k >= 1; k--) {
                    cache[k] = cache[k - 1];
                }
            }
            cache[0] = input;
        }

        return cache;
    }


    public static void main(String[] args) {
        Lru lru = new Lru();
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int[] c = new int[b];
        for (int i = 0; i < b; i++) {
            c[i] = sc.nextInt();
        }
        for (int x : lru.solve(a, b, c)) {
            System.out.println(x);
        }
    }
}

