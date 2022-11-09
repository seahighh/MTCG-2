import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6);
        Stream<Integer> stream = integerList.stream().filter(i -> i>3); //筛选 456
        Stream<Integer> stream1 = integerList.stream().distinct(); //去重


        List<String> stringList = Arrays.asList("YYinHaichuan", "Lambdas", "In", "Action");
        List<String> c = stringList.stream()
                .distinct()
                .collect(Collectors.toList());
        List<Integer> collect = integerList.stream()
                .distinct()
                .map(i -> i*100)
                .distinct()
                .filter(i -> i<200)
                .collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(c);







    }

}
