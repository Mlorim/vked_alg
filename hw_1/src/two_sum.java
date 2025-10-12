import java.util.Scanner;
import java.util.ArrayList;


// Пример ввода:
/*
[3, 8, 9, 11, 16, 18, 19, 21]
25
 */

public class two_sum {
    public static ArrayList<Integer> twoSum(ArrayList<Integer> arr, int target) {
       int left = 0;
       int right = arr.size()-1;
       ArrayList<Integer> res = new ArrayList<Integer>();
       while (left < right) {
           int sum = arr.get(left) + arr.get(right);
           if (sum < target) {
               left++;
           } else if (sum > target){
               right--;
           } else {
               res.add(left);
               res.add(right);
               break;
           }
       }
       return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine().trim();

        line = line.replaceAll("[\\[|\\]]", "");

        String[] nums = line.split("[,\\s]+");

        ArrayList<Integer> array = new ArrayList<>();

        for (String num : nums) {
            if (!num.isEmpty()) {
                array.add(Integer.parseInt(num));
            }
        }

        int target =  scanner.nextInt();

        System.out.println(twoSum(array, target).toString());

        scanner.close();
    }
}
