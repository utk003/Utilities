import io.github.utk003.util.function.lambda.*;

public class Main {
    public static void main(String[] args) {
        Lambda3<Integer, Integer, Integer, Integer> lambda = (Integer i1, Integer i2, Integer i3) -> i1 + i2 + i3;
        System.out.println(lambda.get(5,6,7));
    }
}
