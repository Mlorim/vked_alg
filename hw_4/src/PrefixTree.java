import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PrefixTree {
    public static class Trie {
        HashMap<Character, Trie> children;
        Boolean is_end_of_word;

        public Trie() {
            this.children = new HashMap<>();
            this.is_end_of_word = false;
        }

        public Trie(boolean is_end_of_word) {
            this.children = new HashMap<>();
            this.is_end_of_word = is_end_of_word;
        }

        public void insert(String word) {
            Trie current_node = this;
            for (int i = 0; i < word.length(); i++) {
                char curr_char = word.charAt(i);
                if (current_node.children.isEmpty() || !current_node.children.containsKey(curr_char)) {
                    current_node.children.put(curr_char, new Trie());
                }
                current_node = current_node.children.get(curr_char);
            }
            current_node.is_end_of_word = true;
        }

        public boolean search(String word) {
            Trie current_node = this;
            for (int i = 0; i < word.length(); i++) {
                char curr_char = word.charAt(i);
                if (current_node.children.isEmpty() || !current_node.children.containsKey(curr_char)) {
                    return false;
                }
                current_node = current_node.children.get(curr_char);
            }
            return current_node.is_end_of_word;
        }

        public boolean searchPrefix(String word) {
            Trie current_node = this;
            for (int i = 0; i < word.length(); i++) {
                char curr_char = word.charAt(i);
                if (current_node.children.isEmpty() || !current_node.children.containsKey(curr_char)) {
                    return false;
                }
                current_node = current_node.children.get(curr_char);
            }
            return true;
        }

        public void delete(String word) {
            Trie current_node = this;
            Stack<HashMap<Trie, Character>> stack = new Stack<>();
            for (int i = 0; i < word.length(); i++) {
                char curr_char = word.charAt(i);
                if (current_node.children.isEmpty() || !current_node.children.containsKey(curr_char)) {
                    return;
                }
                HashMap<Trie, Character> curr_pair = new HashMap<>();
                curr_pair.put(current_node, curr_char);
                stack.push(curr_pair);
                current_node = current_node.children.get(curr_char);
            }

            if (!current_node.is_end_of_word) {
                return;
            }
            current_node.is_end_of_word = false;

            if (current_node.children.isEmpty()) {
                while (!stack.isEmpty()) {
                    HashMap<Trie, Character> curr_pair = stack.pop();
                    Trie parent_node = curr_pair.keySet().iterator().next();
                    char parent_char = curr_pair.get(parent_node);
                    parent_node.children.remove(parent_char);
                    if (parent_node.is_end_of_word || !parent_node.children.isEmpty()) {
                        break;
                    }
                }
            }
        }

    }

    public static class RadixTreeNode {
        String prefix;
        HashMap<Character, Trie> children;
        Boolean is_end_of_word;

        public RadixTreeNode() {
            this.children = new HashMap<>();
            this.is_end_of_word = false;
            this.prefix = "";
        }

        public RadixTreeNode(String prefix) {
            this.children = new HashMap<>();
            this.is_end_of_word = false;
            this.prefix = prefix;
        }

        public RadixTreeNode(boolean is_end_of_word, String prefix) {
            this.children = new HashMap<>();
            this.is_end_of_word = is_end_of_word;
            this.prefix = prefix;
        }
    }

    public static class WordDictionary {
        HashMap<Character, WordDictionary> children;
        Boolean is_end_of_word;

        public WordDictionary() {
            this.children = new HashMap<>();
            this.is_end_of_word = false;
        }

        public WordDictionary(boolean is_end_of_word) {
            this.children = new HashMap<>();
            this.is_end_of_word = is_end_of_word;
        }

        public void addWord(String word) {
            WordDictionary current_node = this;
            for (int i = 0; i < word.length(); i++) {
                char curr_char = word.charAt(i);
                if (current_node.children.isEmpty() || !current_node.children.containsKey(curr_char)) {
                    current_node.children.put(curr_char, new WordDictionary());
                }
                current_node = current_node.children.get(curr_char);
            }
            current_node.is_end_of_word = true;
        }

        public boolean search(String word) {
            WordDictionary current_node = this;
            for (int i = 0; i < word.length(); i++) {
                char curr_char = word.charAt(i);

                if (current_node.children.isEmpty()) {
                    return false;
                }

                if (curr_char == '.') {
                    if ((i == word.length() - 1) && current_node.is_end_of_word) {
                        return true;
                    }

                    String rest = word.substring(i+1);
                    for (Character child: current_node.children.keySet()) {
                        if (current_node.children.get(child).search(rest)) {
                            return true;
                        }
                    }
                    return false;
                }

                if (!current_node.children.containsKey(curr_char)) {
                    return false;
                }
                current_node = current_node.children.get(curr_char);
            }
            return current_node.is_end_of_word;
        }
    }

    public static class Solution {
        private static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            String word;
        }

        private TrieNode buildTrie(String[] words) {
            TrieNode root = new TrieNode();
            for (String word : words) {
                TrieNode node = root;
                for (char c : word.toCharArray()) {
                    int index = c - 'a';
                    if (node.children[index] == null) {
                        node.children[index] = new TrieNode();
                    }
                    node = node.children[index];
                }
                node.word = word;
            }
            return root;
        }

        public List<String> findWords(char[][] board, String[] words) {
            List<String> result = new ArrayList<>();
            TrieNode root = buildTrie(words);

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    dfs(board, i, j, root, result);
                }
            }
            return result;
        }

        private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return;

            char c = board[i][j];
            if (c == '#' || node.children[c - 'a'] == null) return;

            node = node.children[c - 'a'];
            if (node.word != null) {
                result.add(node.word);
                node.word = null; // избегаем дубликатов
            }

            board[i][j] = '#';
            dfs(board, i + 1, j, node, result);
            dfs(board, i - 1, j, node, result);
            dfs(board, i, j + 1, node, result);
            dfs(board, i, j - 1, node, result);
            board[i][j] = c;
        }
    }

    public static void main(String[] args) {

        System.out.println("Custom tests (Задача 208. Implement Trie (Prefix Tree))");
        Trie root = new Trie();
        String word = "world";
        root.insert(word);
        System.out.println("After inserting " + word);
        System.out.println("'" + word + "'" + " found: " + root.search(word));
        System.out.println("'wor' found: " + root.search("wor"));
        System.out.println("prefix 'wor' found: " + root.searchPrefix("wor"));
        System.out.println("After deleting " + word);
        root.delete(word);
        System.out.println("'" + word + "'" + " found: " + root.search(word));

        System.out.println("\n" + "Задача 211. Design Add and Search Words Data Structure");

        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        System.out.println(wordDictionary.search("pad")); // return False
        System.out.println(wordDictionary.search("bad")); // return True
        System.out.println(wordDictionary.search(".ad")); // return True
        System.out.println(wordDictionary.search("b..")); // return True


    }
}