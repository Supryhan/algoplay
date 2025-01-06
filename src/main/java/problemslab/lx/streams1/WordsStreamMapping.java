package problemslab.lx.streams1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WordsStreamMapping {

  public static void main(String[] args) {

    List<String> strings = List.of("Hello,", "World");

    List<String> characters = strings
        .stream()
        .map(word -> word.split(""))
        .flatMap(Arrays::stream)
        .distinct()
        .toList();

    characters.forEach(System.out::println);


    boolean isHealthy = strings.stream()
        .allMatch(s -> s.length() < 6);
    Optional<Integer> max = strings.stream().map(String::length).reduce(Integer::max);
    System.out.println("Max: " + max.orElse(0));
  }
}
