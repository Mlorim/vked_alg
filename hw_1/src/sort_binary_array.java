import java.util.Scanner;
import java.util.ArrayList;


// Пример ввода:
/*
[0, 1, 1, 0, 1, 0, 1, 0]
 */
public class sort_binary_array {
    public static void swap(int first, int second, ArrayList<Integer> arr) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }

    public static void binSort(ArrayList<Integer> arr) {

        int left = 0;
        int right = arr.size() - 1;

        while (left < right) {
            if (arr.get(left) == 0) {
                left++;
            } else if ((arr.get(right) == 1)){
                right--;
            } else {
                swap(left, right, arr);
                left++;
                right--;
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

        binSort(array);

        System.out.println(array.toString());

        scanner.close();
    }
}
