package org.dwl.algorithm.intro.stack;

import java.util.Scanner;
import java.util.Stack;

public class Parentheses {

    public String solution(String str) {
        String answer = "YES";
        Stack<Character> stack = new Stack<>();
        for (char x : str.toCharArray()) {
            if (x == '(') {
                stack.push(x);
            } else {
                if (stack.isEmpty()) {
                    answer = "NO";
                    stack.pop();
                }
            }
        }

        if (!stack.isEmpty()) {
            answer = "NO";
        }

        return answer;
    }

    public static void main(String[] args) {
        Parentheses parentheses = new Parentheses();
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        System.out.println(parentheses.solution(str));
    }
}
