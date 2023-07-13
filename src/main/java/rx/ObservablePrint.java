package rx;

import io.reactivex.rxjava3.core.*;

public class ObservablePrint {
    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);
    }
}
