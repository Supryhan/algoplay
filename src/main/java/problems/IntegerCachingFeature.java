package problems;

public class IntegerCachingFeature {
    public static void main(String[] args) {
        Integer num1 = 40;
        Integer num2 = 40;
        Integer num3 = 180;
        Integer num4 = 180;

        if(num1 == num2)
            System.out.println("A");
        else
            System.out.println("B");

        if(num3 == num4)
            System.out.println("C");
        else
            System.out.println("D");
    }
}
