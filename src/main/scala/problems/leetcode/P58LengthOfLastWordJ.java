package problems.leetcode;


public class P58LengthOfLastWordJ {
    public static void main(String[] args) {
        P58LengthOfLastWordJ problem = new P58LengthOfLastWordJ();
        System.out.println("1: " + problem.lengthOfLastWord("Hello World"));
        System.out.println("2: " + problem.lengthOfLastWord("   fly me   to   the moon  "));
        System.out.println("3: " + problem.lengthOfLastWord("luffy is still joyboy"));
        System.out.println("4: " + problem.lengthOfLastWord("a"));
    }

    public int lengthOfLastWord(String s) {
        int first = 0;
        int last = 0;
        int pointer = 0;
        boolean flag = false;
            char[] arr = s.toCharArray();
            while(pointer < arr.length) {
                if(' ' != arr[pointer] && !flag) {
                    first = pointer;
                    flag = true;
                }
                if(pointer == arr.length - 1 && flag) {
                    last = pointer + 1;
                }
                if(' ' == arr[pointer] && flag) {
                    last = pointer;
                    flag = false;
                }
                pointer++;
            }
        return last - first;
    }
}
