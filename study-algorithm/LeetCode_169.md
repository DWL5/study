# LeetCode 169. Majority Element

## 문제 설명
배열 `nums`가 주어질 때, **과반수(majority) 요소**를 찾아 반환하는 문제입니다.  
과반수 요소란 배열의 길이 `n`의 **절반을 초과하여 등장하는 요소**를 의미합니다.  
문제에서 주어진 가정은 항상 과반수 요소가 존재한다는 것입니다.

--- 

## 내가 푼 코드
- HashMap 이용
```
class Solution {
    public int majorityElement(int[] nums) {
        int majorityTimes = nums.length / 2;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int count = map.getOrDefault(nums[i], 0);
            map.put(nums[i], count + 1);
        }

        int majority = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value > majorityTimes) {
                majority = key;
            }
        }

        return majority;
    }
}
```
- 시간 복잡도: O(n)
- 공간 복잡도: O(n)

## GPT

### HashMap 방식
- HashMap에 데이터를 넣어가며 바로 체크를 하고 리턴
```
public int majorityElement(int[] nums) {
    Map<Integer, Integer> countMap = new HashMap<>();
    for (int num : nums) {
        countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        if (countMap.get(num) > nums.length / 2) {
            return num;
        }
    }
    return -1;
}
```
- 시간 복잡도: O(n)
- 공간 복잡도: O(n)


### 보이어-무어 투표
- 하나의 후보를 유지하고, 현재 숫자가 후보와 같으면 카운트를 증가, 다르면 감소시킴
- 최종적으로 후보가 과반수 요소가 됨
```
public int majorityElement(int[] nums) {
    int candidate = nums[0];
    int count = 1;
    for (int i = 1; i < nums.length; i++) {
        if (count == 0) {
            candidate = nums[i];
        }
        count += (nums[i] == candidate) ? 1 : -1;
    }
    return candidate;
}
```

## 보이어-무어 투표 알고리즘 이란?
보이어-무어 다수결 투표 알고리즘(Boyer-Moore Voting Algorithm)은 배열 내에서 과반수를 차지하는 요소(majority element)를 효율적으로 찾는 알고리즘입니다. 이 알고리즘은 O(n) 시간 복잡도와 O(1) 공간 복잡도로 실행되어 매우 효율적입니다.

### 알고리즘 개요
보이어-무어 알고리즘은 다음 두 단계를 통해 다수 요소를 찾습니다.
1. 후보 선정 단계 (Candidate Selection)
- 하나의 후보를 선정하고, 카운트를 관리하면서 등장 횟수를 조정합니다.
- 후보와 같은 값이 나오면 카운트를 증가시키고, 다른 값이 나오면 감소시킵니다.
- 카운트가 0이 되면 새로운 후보를 설정합니다.

2. 검증 단계 (Verification)
- 과반수 요소가 항상 존재한다는 가정이 없을 경우, 두 번째 패스를 통해 후보가 실제 과반수인지 확인해야 합니다.

### 알고리즘 동작 과정
주어진 배열: `[3, 3, 4, 2, 3, 3, 3, 2, 4, 4]`

1. 후보 선정 단계
| 인덱스 | 요소 | 현재 후보 | 카운트 | 설명 |
|-------|------|----------|--------|------|
| 0     | 3    | 3        | 1      | 첫 번째 요소를 후보로 설정 |
| 1     | 3    | 3        | 2      | 후보와 같으므로 카운트 증가 |
| 2     | 4    | 3        | 1      | 후보와 다르므로 카운트 감소 |
| 3     | 2    | 3        | 0      | 후보와 다르므로 카운트 감소 (0이 되어 후보 변경 가능) |
| 4     | 3    | 3        | 1      | 후보를 새롭게 설정 |
| 5     | 3    | 3        | 2      | 후보와 같으므로 카운트 증가 |
| 6     | 3    | 3        | 3      | 후보와 같으므로 카운트 증가 |
| 7     | 2    | 3        | 2      | 후보와 다르므로 카운트 감소 |
| 8     | 4    | 3        | 1      | 후보와 다르므로 카운트 감소 |
| 9     | 4    | 3        | 0      | 후보와 다르므로 카운트 감소 (0이 되어 후보 변경 가능) |

최종 후보: `3`

2. 검증 단계
최종 후보가 진짜 과반수인지 확인하기 위해 배열을 순회하며 개수를 계산:

- 배열 `[3, 3, 4, 2, 3, 3, 3, 2, 4, 4]`에서 숫자 `3`의 등장 횟수 = `5`
- 배열의 길이 = `10`
- `5 > 10 / 2`이므로 후보 `3`이 과반수 요소임이 확인됨.

3. 최종 단계
배열 `[3, 3, 4, 2, 3, 3, 3, 2, 4, 4]`의 다수 요소(majority element)는 **`3`**.
