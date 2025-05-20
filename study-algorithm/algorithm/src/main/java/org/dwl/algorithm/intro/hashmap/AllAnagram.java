package org.dwl.algorithm.intro.hashmap;

import java.util.HashMap;
import java.util.Scanner;

public class AllAnagram {

    public int solution(String s, String t) {
        int answer = 0;
        HashMap<Character, Integer> tMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tMap.put(t.charAt(i), tMap.getOrDefault(t.charAt(i), 0) + 1);
        }


        HashMap <Character, Integer> sMap = new HashMap<>();
        for (int i = 0; i < t.length() - 1; i++) {
            sMap.put(t.charAt(i), sMap.getOrDefault(t.charAt(i), 0) + 1);
        }

        int lt = 0;
        for (int rt = t.length() - 1; rt < s.length(); rt++) {
            sMap.put(s.charAt(rt), sMap.getOrDefault(s.charAt(rt), 0) + 1);
            if (sMap.keySet().containsAll(tMap.keySet()) && sMap.values().containsAll(tMap.values())) {
                answer++;
            }
            sMap.put(s.charAt(lt), sMap.get(s.charAt(lt)) - 1);
            if (sMap.get(s.charAt(lt)) == 0) {
                sMap.remove(s.charAt(lt));
            }

            lt++;
        }

        return answer;
    }


    public static void main(String[] args) {
        AllAnagram allAnagram = new AllAnagram();
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String t = sc.next();
        System.out.println(allAnagram.solution(s, t));
    }
}
