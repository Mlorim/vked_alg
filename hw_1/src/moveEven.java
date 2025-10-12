import java.util.Scanner;
import java.util.ArrayList;

// Пример ввода:
/*
[3, 2, 4, 1, 11, 8, 9]
 */

public class moveEven {
    public static void swap(int first, int second, ArrayList<Integer> arr) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }

    public static void evenMove(ArrayList<Integer> arr) {
        int pnt_even = 0;
        for (int i=0; i<arr.size(); i++) {
            if (arr.get(i) % 2 == 0) {
                swap(i, pnt_even, arr);
                pnt_even++;
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

        evenMove(array);

        System.out.println(array.toString());

        scanner.close();
    }
}
