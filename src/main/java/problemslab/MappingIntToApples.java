package problemslab;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
The task, based on the provided code, is to create a program that transforms a list of integer weights
into a list of apples,where each apple is created with a weight from the list. This demonstrates the use of
higher-order methods and constructors in Java. Here is a detailed description of the task and the steps for
its implementation:

Task: Program for Creating Apples with Weights
Objective:
Create a program that transforms a list of weights into a list of Apple objects, where each object is initialized
with the weight from the corresponding list item.

Tasks:
Creating the Apple class:

The Apple class should have a private field 'weight' of type int.
The class must have a constructor that accepts 'weight' and initializes the field.
Add a getter for the 'weight' field.
Implementing the map method:

Create a static 'map' method that takes a list of integer values (List<Integer>) and
a functional interface (Function<Integer, Apple>). The method should transform each number in the list into
an Apple object using the provided function and return a list of these apples.
Creating test data and running the program:

Create a list of weights and use the 'map' method to create a list of apples.
Output the results to the console, showing the weights of the created apples.
*/
public class MappingIntToApples {
  public static void main(String[] args) {
    List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7);
    List<Apple> apples = map(integers, Apple::new);
    apples.forEach(System.out::println);
  }

  public static List<Apple> map(List<Integer> numbers, Function<Integer, Apple> f) {
    return numbers.stream().map(f).collect(Collectors.toList());
  }
}

class Apple {

  private int weight;

  public Apple(int weight) {
    this.weight = weight;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return "Apple{" +
        "weight: " + this.weight +
        "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Apple that = (Apple) o;
    return this.weight == that.weight;
  }

  @Override
  public int hashCode() {
    return 31 * weight;
  }


}