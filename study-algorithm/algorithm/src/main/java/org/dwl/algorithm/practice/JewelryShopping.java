package org.dwl.algorithm.practice;

import java.util.*;

/**
 * 보석쇼핑
 */
class JewelryShopping {
    public int[] solution(String[] gems) {
        int[] answer = new int[2];
        Set<String> set = new HashSet<>(Arrays.asList(gems));
        Map<String, Integer> map = new HashMap<>();
        int len = Integer.MAX_VALUE;
        int left= 0;
        int right = 0;

        while (true) {
            if (map.size() == set.size()) {
                if (len > right - left) {
                    len = right - left;
                    answer[0] = left + 1;
                    answer[1] = right;
                }

                map.put(gems[left], map.getOrDefault(gems[left], 0) - 1);
                if (map.get(gems[left]) == 0) {
                    map.remove(gems[left]);
                }
                left++;
            } else {
                if (right >= gems.length) {
                    break;
                }
                map.put(gems[right], map.getOrDefault(gems[right], 0) + 1);
                right++;
            }
        }
        return answer;
    }
}
