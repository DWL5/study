package org.dwl.algorithm.intro.stack;

import java.util.Scanner;
import java.util.Stack;

public class RemoveLetterInParentheses {

    public String solution(String str) {
        System.out.println("start : " + str);
        String answer = "";
        Stack<Character> stack = new Stack<>();
        for (char x : str.toCharArray()) {
            if (x != ')') {
                stack.push(x);
            } else {
                while (stack.pop() != '(');
            }
        }

        for (char x : stack) {
            if (Character.isAlphabetic(x)) {
                answer += x;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        RemoveLetterInParentheses r = new RemoveLetterInParentheses();
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        System.out.println(r.solution(str));
    }
}
