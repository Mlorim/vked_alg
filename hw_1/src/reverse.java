import java.util.ArrayList;
import java.util.Scanner;


// Пример ввода:
/*
[1, 2, 3, 4, 5]
 */

public class reverse {
    public static void swap(int first, int second, ArrayList<Integer> arr) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }

    public static void reverseArr(ArrayList<Integer> arr) {
        int left = 0;
        int right = arr.size()-1;

        while (left < right) {
            swap(left, right, arr);
            left++;
            right--;
        }
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

        reverseArr(array);

        System.out.println(array.toString());

        scanner.close();
    }
}
