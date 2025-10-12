import java.util.Scanner;
import java.util.ArrayList;

// Пример ввода:
/*
[2, 0, 2, 1, 1, 0]
 */


public class sortColors {
    public static void swap(int first, int second, ArrayList<Integer> arr) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }

    public static void colorSort(ArrayList<Integer> arr) {
        int left = 0;
        int middle = 0;
        int right = arr.size() - 1;

        while (middle <= right) {
            if (arr.get(middle) == 2) {
                swap(middle, right, arr);
                right--;
            } else if (arr.get(middle) == 0) {
                swap(middle, left, arr);
                left++;
                middle++;
            } else {
                middle++;
            }
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

        colorSort(array);

        System.out.println(array.toString());

        scanner.close();
    }
}
