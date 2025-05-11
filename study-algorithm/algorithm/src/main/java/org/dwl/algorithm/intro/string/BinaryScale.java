package org.dwl.algorithm.intro.string;

import java.util.Scanner;

public class BinaryScale {

    public String solve(int decimal) {
        StringBuilder result = new StringBuilder();
        while (decimal > 0) {
            result.insert(0, decimal % 2);
            decimal /= 2;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        BinaryScale binaryScale = new BinaryScale();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        System.out.println(binaryScale.solve(Integer.parseInt(str)));
    }
}
