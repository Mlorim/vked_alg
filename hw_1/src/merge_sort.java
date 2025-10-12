import java.util.ArrayList;
import java.util.Scanner;


// Версия без третьего массива

// Пример ввода:
/*
[3, 8, 10, 11]
[1, 7, 9]
*/

public class merge_sort {
    public static void swap(int first, int second, ArrayList<Integer> arr) {
        int temp = arr.get(first);
        arr.set(first, arr.get(second));
        arr.set(second, temp);
    }
    
    public static void merge(ArrayList<Integer> arr_1, ArrayList<Integer> arr_2) {
        int size_1 = arr_1.size();
        int size_2 = arr_2.size();
        
        int pnt_1 = size_1 - size_2 - 1;
        int pnt_2 = size_2 - 1;
        int pnt_res = size_1 - 1;

        while (pnt_2 >= 0) {
            if (pnt_1 >= 0 && arr_1.get(pnt_1) >= arr_2.get(pnt_2)) {
                arr_1.set(pnt_res, arr_1.get(pnt_1));
                pnt_1--;
            } else {
                arr_1.set(pnt_res, arr_2.get(pnt_2));
                pnt_2--;
            }
            pnt_res--;
        }
        
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line_1 = scanner.nextLine().trim();

        line_1 = line_1.replaceAll("[\\[|\\]]", "");

        String[] nums_1 = line_1.split("[,\\s]+");

        ArrayList<Integer> array_1 = new ArrayList<>();

        for (String num : nums_1) {
            if (!num.isEmpty()) {
                array_1.add(Integer.parseInt(num));
            }
        }

        String line_2 = scanner.nextLine().trim();

        line_2 = line_2.replaceAll("[\\[|\\]]", "");

        String[] nums_2 = line_2.split("[,\\s]+");

        ArrayList<Integer> array_2 = new ArrayList<>();

        for (String num : nums_2) {
            if (!num.isEmpty()) {
                array_2.add(Integer.parseInt(num));
                array_1.add(0);
            }
        }

        merge(array_1, array_2);

        System.out.println(array_1.toString());

        scanner.close();
    }
}

