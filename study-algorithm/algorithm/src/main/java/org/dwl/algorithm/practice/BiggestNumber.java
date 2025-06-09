package org.dwl.algorithm.practice;

import java.util.Arrays;

public class BiggestNumber {
    public String solution(int[] numbers) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted((a, b) -> (b + a).compareTo(a + b))
                .forEach(sb::append);

        return sb.toString();
    }
}
