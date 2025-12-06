package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class Dinprog {

    public static int b_sequences(int N) {
        ArrayList<Integer> dp = new ArrayList<>(List.of(1, 2));
        for (int i = 2; i <= N; i++) {
            dp.add(dp.get(i-1) + dp.get(i-2));
        }
        return dp.get(N);
    }

    public static int count_sequences(int N) {
        ArrayList<Integer> dp = new ArrayList<>(List.of(1, 2, 4));
        for (int i = 3; i <= N; i++) {
            dp.add(dp.get(i-1) + dp.get(i-2));
        }
        return dp.get(N);
    }

    public static int findLIS(int[] nums) {
        int len = nums.length;
        if (len== 0) {
            return 0;
        }
        if (len == 1) {
            return 1;
        }
        ArrayList<Integer> dp = new ArrayList<>(Collections.nCopies(len, 1));

        for (int i = 1; i < len; i++) {
            if (nums[i - 1] < nums[i]) {
                dp.set(i, dp.get(i-1) + 1);
            }
        }

        int max = dp.getFirst();
        for (int i = 1; i < dp.size(); i++) {
            if (dp.get(i) > max) {
                max = dp.get(i);
            }
        }
        return max;
    }

    public static int[][] pascal_triangle(int N) {
        int[][] dp = new int[N][];
        for (int i = 0; i < N; i++) {
            int[] temp = new int[i + 1];
            Arrays.fill(temp, 1);
            dp[i] = temp;
        }

        for (int row = 1; row < N; row++) {
            for (int col = 1; col < row; col++) {
                dp[row][col] = dp[row-1][col-1] + dp[row-1][col];
            }
        }

        return dp;
    }

    public static int maxProfit(int[] prices) {
        int profit = 0;
        int min_price = prices[0];
        for (int i = 1; i  < prices.length; i++) {
            profit = max(profit, prices[i] - min_price);
            min_price = min(min_price, prices[i]);
        }
        return profit;
    }

    public static int coinChange(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1;
        }

        int minCoins = Integer.MAX_VALUE;

        for (int coin: coins) {
            int res = coinChange(coins, amount-coin);
            if (res > 0 && res < minCoins) {
                minCoins = res + 1;
            }
        }

        if (minCoins == Integer.MAX_VALUE) {
            return -1;
        }

        return minCoins;
    }

    public static int coinChangeDP(int[] coins, int amount) {
        int[] dp = new int[amount+1];

        for (int i = 0; i <= amount; i++) {
            dp[i] =  Integer.MAX_VALUE;
        }
        dp[0] = 0;

        for (int i=1; i<= amount; i++) {
            for (int coin: coins) {
                if (coin < i) {
                    dp[i] = min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        if (dp[amount] == Integer.MAX_VALUE) {
            return -1;
        }
        return dp[amount];
    }

    public static void expandAroundCenter(String s, int left, int right, int[] currentMax) {
        int L = left;
        int R = right;

        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            if ((R - L) > (currentMax[1] - currentMax[0])) {
                currentMax[0] = L;
                currentMax[1] = R;
            }
            L--;
            R++;
        }
    }

    public static String longestPalindrome(String s) {
        int[] currentMax = {0, 0}; // [left, right]

        for (int i = 0; i < s.length(); i++) {
            expandAroundCenter(s, i, i, currentMax);
            expandAroundCenter(s, i, i + 1, currentMax);
        }

        return s.substring(currentMax[0], currentMax[1] + 1);
    }

    public static String longestPalindromeDP(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = i; j < n; j++) {
                dp[j-i][j] = (s.charAt(j-i) == s.charAt(j)) && dp[j-i+1][j-1];
            }
        }

        String res = "";
        for (int i = n-1; i >= 0; i--) {
            if (!res.isEmpty()) {
                break;
            }
            for (int j = i; j < n; j++) {
                if (dp[j-i][j]) {
                    res = s.substring(j-i, j+1);
                    break;
                }
            }
        }

        return res;
    }



    public static void main(String[] args) {
        String test = "babab";

        System.out.println("Тестирование longestPalindromeDP на слове " + test);
        System.out.println(longestPalindromeDP(test));

        System.out.println("\nТестирование b_sequences:");
        System.out.println("b_sequences(5) = " + b_sequences(5));
        System.out.println("b_sequences(10) = " + b_sequences(10));

        System.out.println("\nТестирование count_sequences:");
        System.out.println("count_sequences(5) = " + count_sequences(5));
        System.out.println("count_sequences(10) = " + count_sequences(10));

        System.out.println("\nТестирование findLIS:");
        int[] nums1 = {1, 3, 5, 4, 7};
        System.out.println("findLIS([1, 3, 5, 4, 7]) = " + findLIS(nums1));
        int[] nums2 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("findLIS([10, 9, 2, 5, 3, 7, 101, 18]) = " + findLIS(nums2));

        System.out.println("\nТестирование pascal_triangle:");
        int[][] triangle = pascal_triangle(5);
        System.out.println("pascal_triangle(5):");
        for (int i = 0; i < triangle.length; i++) {
            System.out.println(Arrays.toString(triangle[i]));
        }

        System.out.println("\nТестирование maxProfit:");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("maxProfit([7, 1, 5, 3, 6, 4]) = " + maxProfit(prices1));
        int[] prices2 = {7, 6, 4, 3, 1};
        System.out.println("maxProfit([7, 6, 4, 3, 1]) = " + maxProfit(prices2));

        System.out.println("\nТестирование coinChange:");
        int[] coins1 = {1, 3, 4};
        System.out.println("coinChange([1, 3, 4], 6) = " + coinChange(coins1, 6));
        int[] coins2 = {2};
        System.out.println("coinChange([2], 3) = " + coinChange(coins2, 3));

        System.out.println("\nТестирование coinChangeDP:");
        int[] coins3 = {1, 3, 4};
        System.out.println("coinChangeDP([1, 3, 4], 6) = " + coinChangeDP(coins3, 6));
        int[] coins4 = {2};
        System.out.println("coinChangeDP([2], 3) = " + coinChangeDP(coins4, 3));

        System.out.println("\nТестирование longestPalindrome:");
        String test2 = "babad";
        System.out.println("longestPalindrome(\"" + test2 + "\") = " + longestPalindrome(test2));
        String test3 = "cbbd";
        System.out.println("longestPalindrome(\"" + test3 + "\") = " + longestPalindrome(test3));

    }
}
