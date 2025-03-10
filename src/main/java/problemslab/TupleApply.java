package problemslab;

import scala.Function;
import scala.Function2;
import scala.Tuple2;
import scala.collection.immutable.WrappedString;
import scala.runtime.AbstractFunction2;

public class TupleApply {
    static final Function2<Integer, String, String> foo =
            new AbstractFunction2<Integer, String, String>() {
                public String apply(Integer x, String str) {
                    return new WrappedString(str.repeat(x)).toString();
                }
            };

    public static void main(String[] args) {
        Tuple2<Integer, String> tuple = new Tuple2<Integer, String>(3, "Hello, World! ");
        System.out.println(TupleApply.foo.tupled().apply(tuple));
        System.out.println(Function.tupled(TupleApply.foo).apply(tuple));
    }
}
