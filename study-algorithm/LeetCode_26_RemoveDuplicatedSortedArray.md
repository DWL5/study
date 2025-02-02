# LeetCode 26번 문제: Remove Duplicates from Sorted Array

## 문제 설명
주어진 **정렬된 배열**에서 중복된 요소를 제거하고, 중복되지 않은 요소들로 배열을 구성해야 합니다.  
최종 결과는 새로운 배열의 길이를 반환하며, **추가 메모리 사용 없이 제자리(in-place)**에서 처리해야 합니다.

- 배열의 처음 `k`개의 요소는 중복이 제거된 상태여야 합니다.
- 새 길이 `k` 이후의 요소들은 무시되어도 됩니다.

---

## 문제 조건

### 입력
- 정렬된 정수 배열 `nums`
- 배열의 크기 `n` (0 ≤ n ≤ 10⁴)

### 출력
- **중복 제거 후 배열의 새 길이** `k`
- 배열의 **첫 `k`개의 요소**가 중복이 제거된 상태여야 함.

### 제약 사항
- 메모리 공간: O(1)
- 배열은 항상 오름차순으로 정렬되어 있음.

---

## 예제

### 예제 1
**Input**:  
`nums = [1, 1, 2]`

**Output**:  
`2, nums = [1, 2, _]`  
(`_`는 무시되는 값)

---

### 예제 2
**Input**:  
`nums = [0,0,1,1,1,2,2,3,3,4]`

**Output**:  
`5, nums = [0, 1, 2, 3, 4, _, _, _, _, _]`

---

## 내가 푼 코드 
```
class Solution {
    public int removeDuplicates(int[] nums) {
        int idx = 0;
        int currentNum = nums[idx];

        for(int i = 1; i < nums.length; i++) {
           if (currentNum != nums[i]) {
            idx++;
            nums[idx] = nums[i];
            currentNum = nums[i];

           }
        }

        return idx + 1;
    }
}
```

## GPT
```
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int i = 1; // 중복되지 않은 요소를 기록할 위치
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i - 1]) { // 중복되지 않은 값 발견
                nums[i] = nums[j]; // 중복되지 않은 값을 위치 i에 저장
                i++; // 다음 위치로 이동
            }
        }

        return i; // 중복 제거 후 배열의 길이 반환
    }
}
```
---

## 해결 아이디어
1. 배열이 **정렬되어 있음**을 활용:
   - 중복된 값은 연속해서 나타난다는 점을 이용.
2. **Two Pointers(투 포인터)** 방법:
   - `i`: 중복되지 않은 요소를 기록할 위치.
   - `j`: 배열을 순회하며 현재 요소를 가리키는 위치.
3. **중복 확인 및 덮어쓰기**:
   - `nums[j]`와 `nums[i-1]`를 비교.
   - 값이 다르면 `nums[i]`에 `nums[j]`를 저장하고 `i`를 증가.

---
