package org.dwl.algorithm.practice.leetcode;

public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";

        String answer = "";

        for (int center = 0; center < s.length(); center++) {
            // 홀수 길이
            String odd = expand(s, center, center);
            if (odd.length() > answer.length()) answer = odd;

            // 짝수 길이
            String even = expand(s, center, center + 1);
            if (even.length() > answer.length()) answer = even;
        }

        return answer;
    }

    private String expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }
}
