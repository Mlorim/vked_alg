import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HashTables {
    public static int binarySearchSquare(int target) {
        int l = 0;
        int r = target;
        int middle;

        while (l <= r) {
            middle = (l+r) / 2;
            if (middle*middle > target) {
                r = middle-1;
            } else if (middle*middle < target) {
                l = middle+1;
            } else {
                return middle;
            }
        }
        return r;
    }

    public static int copyTime(int n, int x, int y) {
        int l = 0;
        int r = (n-1) * max(x, y);
        int middle;
        while (l < r-1) {
            middle = (l+r) / 2;
            if (middle/x + middle/y < (n - 1)) {
                l = middle;
            } else {
                r = middle;
            }
        }
        return r + min(x,y);
    }

    public static int feedAnimalsNaive(List<Integer> animals, List<Integer> food) {
        if (animals.isEmpty() || food.isEmpty()) {
            return 0;
        }

        int count = 0;

        List<Integer> fed_animals = new ArrayList<>();

        for (Integer meal : food) {
            for (int j = 0; j < animals.size(); j++) {
                if (!fed_animals.contains(j)) {
                    if (meal >= animals.get(j)) {
                        count++;
                        fed_animals.add(j);
                        break;
                    }
                }
            }
        }

        return count;
    }

    public static int feedAnimalsSort(List<Integer> animals, List<Integer> food) {
        if (animals.isEmpty() || food.isEmpty()) {
            return 0;
        }

        Collections.sort(animals);
        Collections.sort(food);

        int count = 0;

        for (Integer meal : food) {
            if (meal >= animals.get(count)) {
                count++;
            }

            if (count == animals.size()) {
                break;
            }
        }

        return count;
    }

    public static char extraLetter(String a, String b) {
        HashMap<Character, Integer> hashMapA = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            char curr = a.charAt(i);
            int curr_count = hashMapA.getOrDefault(curr, 0);
            hashMapA.put(curr, curr_count+1);
        }

        for (int i = 0; i < b.length(); i++) {
            char curr = b.charAt(i);
            if (hashMapA.containsKey(curr)) {
                hashMapA.put(curr, hashMapA.get(curr)-1);
                hashMapA.remove(curr, 0); // если ноль, удаляем
                continue;
            }
            return curr;
        }

        return '\0';
    }

    public static List<Integer> twoSum(List<Integer> data, int target) {
        HashMap<Integer, Integer> cash = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            cash.put(data.get(i), i);
        }
        for (int i = 0; i < data.size(); i++) {
            int diff = target - data.get(i);
            if (cash.containsKey(diff)) {
                return List.of(i, cash.get(diff));
            }
        }
        return List.of();
    }

    public static void swap(List<Integer> list, int a, int b) {
        int temp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, temp);
    }

    public static void shell_sort(List<Integer> list) {
        int gap = list.size() / 2;
        while (gap > 0) {
            for (int currenrt_pos = gap; currenrt_pos < list.size(); currenrt_pos++) {
                int m_gap = currenrt_pos;
                while (m_gap >= gap && list.get(m_gap) < list.get(m_gap-gap)) {
                    swap(list, m_gap, m_gap-gap);
                    m_gap -= gap;
                }
            }
            gap /= 2;
        }
    }

    public static int char_hash(int m, char c) {
        return ((int) c) % m;
    }

    public static int string_hash(int m, String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash += char_hash(m, str.charAt(i));
        }
        return hash;
    }

    public static List<List<String>> find_anagrams(List<String> words) {
        int m = 256;
        HashMap<Integer, List<String>> hashMap = new HashMap<>();

        for (String word: words) {
            int hash = string_hash(m, word);
            if (hashMap.containsKey(hash)) {
                hashMap.get(hash).add(word);
            } else {
                hashMap.put(string_hash(m, word), new ArrayList<>(List.of(word)));
            }
        }

        List<List<String>> res = new ArrayList<>();
        for (Integer key: hashMap.keySet()) {
            res.add(hashMap.get(key));
        }

        return res;
    }

    public static void main(String[] args) {

        int square_target = 17;
        System.out.println("1. binarySearchSquare test");
        System.out.println("output: " + binarySearchSquare(square_target));

        int documents = 11;
        System.out.println("2. copyTime test");
        System.out.println("output: " + copyTime(documents, 1, 2));

        List<Integer> animals_1 = new ArrayList<>(List.of(8, 2, 3, 2));;
        List<Integer> food_1 = new ArrayList<>(List.of(1, 4, 3, 8));
        System.out.println("3. feedAnimalsNaive test");
        System.out.println("output: " + feedAnimalsNaive(animals_1, food_1));
        System.out.println();

        List<Integer> animals_2 = new ArrayList<>(List.of(1, 3));
        List<Integer> food_2 = new ArrayList<>(List.of(7, 1));
        System.out.println("4. feedAnimalsSort test");
        System.out.println("output: " + feedAnimalsSort(animals_2, food_2));
        System.out.println();

        String a = "bab";
        String b = "abbb";
        System.out.println("5. extraLetter test");
        System.out.println("output: " + extraLetter(a, b));
        System.out.println();

        List<Integer> data = new ArrayList<>(List.of(1, 4, 5, 2, 8));
        int target = 7;
        System.out.println("6. twoSum test");
        System.out.println("output: " + twoSum(data, target).toString());
        System.out.println();

        List<Integer> list_to_sort = new ArrayList<>(List.of(9, 7, 3, 4, 1, 5, 2, 3));
        System.out.println("7. shell_sort test");
        shell_sort(list_to_sort);
        System.out.println("output: " + list_to_sort);
        System.out.println();

        List<String> words_1 = new ArrayList<>(List.of("eat","tea","tan","ate","nat","bat"));
        System.out.println("8.1 find_anagrams first test");
        System.out.println(find_anagrams(words_1));
        System.out.println();

        List<String> words_2 = new ArrayList<>(List.of("won","now","aaa","ooo","ooo"));
        System.out.println("8.2. find_anagrams second test");
        System.out.println(find_anagrams(words_2));
    }
}
