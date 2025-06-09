package org.dwl.algorithm.practice;

import java.util.Arrays;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42747
 */
public class HIndex {
    public int solution(int[] citations) {
        int[] sorted = Arrays.stream(citations)
                .boxed()
                .sorted((a,b) -> b - a)
                .mapToInt(Integer::intValue)
                .toArray();

        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] <= i + 1) {
                return i+1;
            }

        }
        return citations.length;
    }
}
