## 문제 설명
정수 배열 `nums`와 정수 `val`이 주어집니다. 배열에서 모든 `val` 값을 **in-place**로 제거하고, 유효한 요소의 개수를 반환하세요.

배열의 순서는 상관없으며, 배열의 앞부분에 유효한 요소를 저장해야 합니다. **추가적인 배열을 생성하지 않고** 작업을 수행해야 합니다.

---

## 입력 형식
- `nums`: 정수 배열 (0 ≤ `nums.length` ≤ 100, `-50 ≤ nums[i], val ≤ 50`)
- `val`: 제거할 정수 값

---

## 출력 형식
- `k`: `nums` 배열에서 `val`을 제거한 후 남은 유효한 요소의 개수
- 배열 `nums`의 앞부분에 `k`개의 유효한 요소가 저장되어야 함

---

### 내가 푼 코드
```
class Solution {
    public int removeElement(int[] nums, int val) {
        int k = nums.length;
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                k--;
            } else {
                nums[idx] = nums[i];
                idx++;
            }
        }
        return k;
    }
}
```

- k와 idx는 같은 값을 가지므로 둘 다 관리 할 필요는 없었다.
- k는 전체 리스트 사이즈 - 제거되어야 하는 사이즈 =  남아야하는 사이즈
- idx는 남아야 하는 사이즈

### 개선 된 코드
```
class Solution {
    public int removeElement(int[] nums, int val) {
        int idx = 0; // 새로운 유효 위치
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[idx] = nums[i]; // 유효한 값을 앞으로 이동
                idx++;
            }
        }
        return idx; // 유효한 요소의 개수 반환
    }
}
```
