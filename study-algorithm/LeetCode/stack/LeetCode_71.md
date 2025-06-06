```
import java.util.*;

class Solution {
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        stack.push(String.valueOf(path.charAt(0)));
        for (int i = 1; i < path.length() + 1; i++) {
            if (i == path.length() || path.charAt(i) == '/') {
                int dot = 0;
                while (stack.size() > 1 && !stack.peek().equals("/")) {
                    String peeked = stack.peek();
                    
                    if (!peeked.equals(".")) {
                        for(int k = 0; k < dot; k++) {
                            stack.push(".");
                        }
                        dot = 0;
                        break;
                    }
                    
                    if (peeked.equals(".")) {
                        dot++;
                        stack.pop();
                    } 
                }

                if (dot >= 3) {
                    for(int k = 0; k < dot; k++) {
                        stack.push(".");
                    }
                }

                if (dot == 2) {
                    int slash = 0;
                    while (stack.size() > 1 && slash < 2) {
                        if (stack.peek().equals("/")) {
                            slash++;
                        }
                        stack.pop();
                    }
                }

                if (stack.peek().equals("/")) {
                    continue;
                }

                if (i <  path.length()) {
                    stack.push(String.valueOf("/"));
                }
                
            } else {
                stack.push(String.valueOf(path.charAt(i)));
            }
        }
        
        if (stack.peek().equals("/")) {
            while(stack.size() > 1 && stack.peek().equals("/")) {
                stack.pop();
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String s : stack) {
            sb.append(s);
        }
        String result = sb.toString();
        return result;
    }
}
```
