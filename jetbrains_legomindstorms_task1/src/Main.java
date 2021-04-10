import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void solve() {
        List<String> list = new ArrayList<>();
        int n = in.nextInt();
        list.add(in.next());
        //Reading the words, and making oriented graph for letter sequences
        for (int i = 1; i < n; i++) {
            list.add(in.next());
            //Checking every two consistent words for first unequal character
            int cur = checkWords(list.get(i), list.get(i - 1));
            //Impossible alphabet
            if (cur == -2) {
                System.out.println("Impossible");
                return;
            }
            //Adding edge to oriented alphabet graph
            if (cur != -1) {
                alphabet.get((int) list.get(i - 1).charAt(cur) - (int)'a').add((int) list.get(i).charAt(cur) - (int)'a');
            }
        }
        // If there is a cycle alphabet is impossible
        if (checkCycle()) {
            System.out.println("Impossible");
            return;
        }
        // Using new class, which implement Comparable, so we can use "Arrays.sort()"
        ComparableChar[] answer = new ComparableChar[26];
        for (int i = 0; i < 26; i++) {
            answer[i] = new ComparableChar(i);
        }
        Arrays.sort(answer);
        for (int i = 0; i < 26; i++) {
            System.out.print(answer[i].ch);
        }
    }

    class ComparableChar implements Comparable<ComparableChar>{
        //Array where I store characters, which should be dextral from this character in result alphabet
        int[] higher;
        char ch;
        int index;
        private void fillHigher(int i) {
            higher[i] = 1;
            for (int j : alphabet.get(i)) {
                fillHigher(j);
            }
        }
        ComparableChar(int index) {
            this.index = index;
            this.ch = (char) ((int)'a' + index);
            higher = new int[26];
            for (int i = 0; i < 26; i++) {
                higher[i] = 0;
            }
            fillHigher(index);
            higher[index] = -1;
        }

        @Override
        public int compareTo(ComparableChar comparableChar) {
            //Checking if this character lower
            if (this.higher[comparableChar.index] == 1) return -1;
            // Checking if its higher
            if (comparableChar.higher[this.index] == 1) return 1;
            return 0;
        }
    }

    private int checkWords(String s1, String s2) {
        for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            // returning first unequal character
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        // -1 if s1 is always earlier than s2
        if (s1.length() < s2.length()) return -1;
        // -2 is s1 can't be earlier than s2
        return -2;
    }

    private boolean BFS(int i) {
        // Visited this vertex in current passage
        color[i] = 1;
        for (int j : alphabet.get(i)) {
            // Making sure we don't check one vertex twice
            if (color[j] != -1) {
                if (color[j] == 0) {
                     if (BFS(j)) return true;
                }
                // Found visited vertex --> We found cycle
                if (color[j] == 1) {
                    System.out.println(j);
                    return true;
                }
            }
        }
        //Vertex checked
        color[i] = -1;
        return false;
    }

    private boolean checkCycle() { // looking for a cycle with BFS
        for (int i = 0; i < 26; i++) {
            color[i] = 0;
        }
        for (int i = 0; i < 26; i++) {
            if (color[i] != -1 && BFS(i)) return true;
        }
        return false;
    }

    private Scanner in;
    private List<List<Integer>> alphabet;
    private int[] color;

    private void run() {
        in = new Scanner(System.in);
        alphabet = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            alphabet.add(new ArrayList<>());
        }
        color = new int[26];
        solve();
    }
}
