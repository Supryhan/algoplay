package problems;

public class LastIndexOf {

    public static void main(String[] args) {
        System.out.println(lastIndexOf("Hello", 'h'));
    }

    static int lastIndexOf(String s, char c) {
        char[] arr = s.toLowerCase().toCharArray();
        int result = -1;
        for(int i = 0; i < arr.length; i++) {
            if(c == arr[i]) {result = i; break;}
        }
        return result;
    }

}
