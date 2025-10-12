import java.util.ArrayList;
import java.util.Scanner;


// Пример ввода:
/*
[1, 2, 3, 4, 5, 6, 7]
3
 */

public class reverse_part {
    public static void swap(int first, int second, ArrayList<Integer> arr) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }

    public static void reverseArrPart(ArrayList<Integer> arr, int left, int right) {
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

        int size = array.size();
        int k = scanner.nextInt() % size;

        reverseArrPart(array, 0, size-1);
        reverseArrPart(array, 0, k-1);
        reverseArrPart(array, k, size-1);

        System.out.println(array.toString());

        scanner.close();
    }
}
