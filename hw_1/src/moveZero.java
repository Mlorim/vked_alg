import java.util.Scanner;
import java.util.ArrayList;

// Пример ввода:
/*
[0, 0, 1, 0, 3, 12]
 */


public class moveZero {
    public static void swap(int first, int second, ArrayList<Integer> arr) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }

    public static void zeroMove(ArrayList<Integer> arr) {
        int size = arr.size();
        int pnt_zero = 0;
        int pnt_not_zero = 0;

        while (pnt_not_zero < size-1) {
            while (arr.get(pnt_zero) != 0) {
                if (pnt_zero == size-1) {
                    break;
                }
                pnt_zero++;
            }
            while (arr.get(pnt_not_zero) == 0 || pnt_not_zero <= pnt_zero) {
                pnt_not_zero++;
                if (pnt_not_zero == size-1) {
                    break;
                }
            }
            swap(pnt_zero, pnt_not_zero, arr);
        }
        if (arr.get(pnt_zero) == 0 && arr.get(pnt_zero) != 0) {
            swap(pnt_zero, pnt_not_zero, arr);
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

        zeroMove(array);

        System.out.println(array.toString());

        scanner.close();
    }
}
