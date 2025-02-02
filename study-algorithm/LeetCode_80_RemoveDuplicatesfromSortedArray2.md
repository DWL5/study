# LeetCode 80번 문제: Remove Duplicates from Sorted Array II

## 문제 설명
정렬된 정수 배열에서 **중복된 값이 최대 2번까지 나타나도록** 배열을 수정해야 합니다.  
추가적인 메모리를 사용하지 않고 **제자리(in-place)**에서 처리해야 하며, 결과로 중복이 제거된 배열의 **새 길이**를 반환해야 합니다.

---

## 문제 조건

### 입력
- 정렬된 정수 배열 `nums`
- 배열의 크기 `n` (0 ≤ n ≤ 10⁴)

### 출력
- 중복을 최대 2번 허용한 배열의 새 길이 `k`.

### 제약 사항
- 배열은 항상 오름차순으로 정렬되어 있음.
- 추가 메모리 사용 금지 (O(1)).

---

## 예제

### 예제 1
**Input**:  
`nums = [1, 1, 1, 2, 2, 3]`  

**Output**:  
`5, nums = [1, 1, 2, 2, 3, _]`  
(`_`는 무시되는 값)

---

### 예제 2
**Input**:  
`nums = [0, 0, 1, 1, 1, 1, 2, 3, 3]`

**Output**:  
`7, nums = [0, 0, 1, 1, 2, 3, 3, _, _]`

---

### 내가 푼 코드 
```
class Solution {
    public int removeDuplicates(int[] nums) {
        int idx = 0;
        int count = 1;
        int currentNum = nums[idx];

        for(int i = 1; i < nums.length; i++) {
           if (currentNum != nums[i]) {
            idx++;
            nums[idx] = nums[i];
            currentNum = nums[i]; 
            count = 1;
           } else {
            count++;
            if (count <= 2) {
                idx++;
                nums[idx] = nums[i];
            }
           }
        }

        return idx + 1;
    }
}
```
---

### GPT
```
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) {
            return nums.length;
        }
        
        int i = 2; // 결과 배열의 위치
        for (int j = 2; j < nums.length; j++) {
            if (nums[j] != nums[i - 2]) { // 중복이 두 번을 초과하지 않는 경우
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }
}
```
---

## 해결 아이디어

1. **정렬된 배열 특성 활용**:
   - 같은 값들이 연속으로 나타난다는 점을 이용.
   - 각 숫자가 최대 두 번만 포함되도록 배열을 수정.

2. **Two Pointers(투 포인터)** 사용:
   - **`i`**: 결과 배열의 현재 위치를 나타냄.
   - **`j`**: 입력 배열을 순회하는 포인터.
   - `nums[j]`가 `nums[i-2]`와 다르면, `nums[i]`에 `nums[j]`를 저장하고 `i`를 증가.

---

### 느낀점
- 투포인터에 더 익숙해지기
