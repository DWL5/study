### 문제 요약
1. 주어진 것
- 두 개의 정렬된 배열 nums1과 nums2.
- 배열 nums1은 충분한 공간(0으로 채워진 여유 공간)을 가지고 있고, 최종 결과도 nums1에 저장해야 함.
- nums1의 처음 m개 요소와 nums2의 n개 요소를 병합해야 함.
  
2. 목표
- nums1과 nums2를 병합해서 정렬된 하나의 배열로 만들기.
- 최종 결과는 nums1에 저장.

3. 병합 알고리즘
- 포인터 초기화
  -  i는 nums1의 끝 (m-1)
  -  j는 nums2의 끝 (n-1)
  -  k는 nums1의 마지막 인덱스 (m+n-1)
-  큰 값부터 비교
   -  nums1[i]와 nums2[j]를 비교.
   -  더 큰 값을 nums1[k]에 넣고 포인터를 이동.
-  남은 요소 처리
   - nums2에 요소가 남아 있다면 nums1에 복사.

```
public class MergeSortedArrays {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1; // nums1의 유효 요소 마지막 인덱스
        int j = n - 1; // nums2의 마지막 인덱스
        int k = m + n - 1; // nums1의 마지막 인덱스

        // nums1과 nums2를 뒤에서부터 비교하며 병합
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }

        // nums2에 남은 요소가 있으면 nums1로 복사
        while (j >= 0) {
            nums1[k] = nums2[j];
            j--;
            k--;
        }
    }
}
```

### 단계별 설명
```
nums1 = [1, 2, 3, 0, 0, 0], m = 3
nums2 = [2, 5, 6], n = 3
//i = 2
//j = 2
//k = 5
```

- 첫번째 비교
  - nums1[2] = 3, nums2[2] = 6 → 6이 더 큼
  - nums1[k]에 6을 채움 → nums1 = [1, 2, 3, 0, 0, 6]
  - 포인터 이동: j--, k--
- 두번째 비교
  - nums1[2] = 3, nums2[1] = 5 → 5가 더 큼
  - nums1[k]에 5를 채움 → nums1 = [1, 2, 3, 0, 5, 6]
  - 포인터 이동: j--, k--
- 세번째 비교
  - nums1[2] = 3, nums2[0] = 2 → 3이 더 큼
  - nums1[k]에 3을 채움 → nums1 = [1, 2, 3, 3, 5, 6]
  - 포인터 이동: i--, k--
- 네번째 비교
  - nums1[1] = 2, nums2[0] = 2 → 같음
  - nums1[k]에 2를 채움 → nums1 = [1, 2, 2, 3, 5, 6]
  - 포인터 이동: j--, k--
- 남은 값 복사
  - nums2에 남은 값이 없으니 끝

### 요약
- 이 문제의 주요 아이디어는 배열의 뒤쪽부터 값을 채운다는 점
- nums1의 뒤쪽에는 병합 후 사용할 빈 공간이 이미 마련되어 있으므로 큰 값부터 뒤쪽으로 채워 넣으면, 기존의 값들을 덮어쓰지 않고 안전하게 병합이 가능
