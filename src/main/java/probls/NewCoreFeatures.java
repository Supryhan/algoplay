package probls;

import java.util.stream.Stream;

public class NewCoreFeatures {
    public static void main(String[] args) {
        NewCoreFeatures newCoreFeatures = new NewCoreFeatures();
        newCoreFeatures.newBreakFeature();
        newCoreFeatures.noneMatching();
    }

    void newBreakFeature() {
        System.out.println("this1");
        breakCond:
        if (true) {
            System.out.println("this2");
            if (true) {
                System.out.println("This3");
                break breakCond;
            }
            System.out.println("This4");
        }
        System.out.println("this5");
    }

    void noneMatching() {
        if (Stream.of(false, false, false, false).noneMatch(x -> x == true)) {
            System.out.println("All match");
        } else {
            System.out.println("False");
        }
    }

}
