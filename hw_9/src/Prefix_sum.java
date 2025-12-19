import java.util.HashMap;
import java.util.Map;

public class Prefix_sum {
    public static Integer maxSubarraySum(int[] arr, int k) {
        if (arr.length < k) {
            return null;
        }

        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += arr[i];
        }
        int maxSum = currentSum;

        for (int i = k; i < arr.length; i++) {
            currentSum = currentSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    public static int subarraySum(int[] nums, int k) {
        int prefixSum = 0;
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);
        int count = 0;

        for (int num : nums) {
            prefixSum += num;
            if (prefixCount.containsKey(prefixSum - k)) {
                count += prefixCount.get(prefixSum - k);
            }
            prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }

    public static int findMaxLength(int[] nums) {
        int prefixSum = 0;
        int maxLen = 0;
        Map<Integer, Integer> indexMap = new HashMap<>();
        indexMap.put(0, -1);

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            prefixSum += (num == 0) ? -1 : 1;

            if (indexMap.containsKey(prefixSum)) {
                maxLen = Math.max(maxLen, i - indexMap.get(prefixSum));
            } else {
                indexMap.put(prefixSum, i);
            }
        }

        return maxLen;
    }

    public static int pivotIndex(int[] nums) {
        int totalSum = 0;
        int leftSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        for (int i = 0; i < nums.length; i++) {
            if (leftSum == totalSum - leftSum - nums[i]) {
                return i;
            }
            leftSum += nums[i];
        }

        return -1;
    }


    public static boolean canMakeValidWithDeletions(String s, int k) {
        int balance = 0;
        int extraClosedBalance = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                balance += 1;
            } else {
                if (balance > 0) {
                    balance -= 1;
                } else {
                    extraClosedBalance += 1;
                }
            }
        }

        int totalNeeded = balance + extraClosedBalance;
        return totalNeeded <= k;
    }

    public static void main(String[] args) {
        // Test maxSubarraySum
        int[] arr1 = {1, 4, 2, 10, 23, 3, 1, 0, 20};
        Integer result1 = maxSubarraySum(arr1, 4);
        System.out.println("maxSubarraySum([1,4,2,10,23,3,1,0,20], 4) = " + result1); // 39

        int[] arr2 = {100, 200, 300, 400};
        Integer result2 = maxSubarraySum(arr2, 2);
        System.out.println("maxSubarraySum([100,200,300,400], 2) = " + result2); // 700

        // Test subarraySum
        int[] nums1 = {1, 1, 1};
        int result4 = subarraySum(nums1, 2);
        System.out.println("subarraySum([1,1,1], 2) = " + result4); // 2

        int[] nums2 = {1, 2, 3};
        int result5 = subarraySum(nums2, 3);
        System.out.println("subarraySum([1,2,3], 3) = " + result5); // 2

        // Test findMaxLength
        int[] nums3 = {0, 1, 0, 0, 1, 1, 0};
        int result6 = findMaxLength(nums3);
        System.out.println("findMaxLength([0,1,0,0,1,1,0]) = " + result6); // 6

        int[] nums4 = {0, 1};
        int result7 = findMaxLength(nums4);
        System.out.println("findMaxLength([0,1]) = " + result7); // 2

        // Test pivotIndex
        int[] nums5 = {1, 7, 3, 6, 5, 6};
        int result8 = pivotIndex(nums5);
        System.out.println("pivotIndex([1,7,3,6,5,6]) = " + result8); // 3

        int[] nums6 = {1, 2, 3};
        int result9 = pivotIndex(nums6);
        System.out.println("pivotIndex([1,2,3]) = " + result9); // -1

        // Test canMakeValidWithDeletions
        String s1 = "())(";
        boolean result10 = canMakeValidWithDeletions(s1, 1);
        System.out.println("canMakeValidWithDeletions('())(' , 1) = " + result10); // true

        String s2 = ")(";
        boolean result11 = canMakeValidWithDeletions(s2, 1);
        System.out.println("canMakeValidWithDeletions(')(' , 1) = " + result11); // false
    }
}