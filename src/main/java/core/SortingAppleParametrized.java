package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingAppleParametrized {
  public static void main(String[] args) {
    AppleBuilder<Apple> builder = new AppleBuilderImpl();
    Apple apple1 = builder.setType("t1").setSize(3).setWeight(0.1).build();
    Apple apple2 = builder.setType("t2").setSize(2).setWeight(0.2).build();
    Apple apple3 = builder.setType("t3").setSize(1).setWeight(0.3).build();

    List<Apple> apples = new ArrayList<>(List.of(apple1, apple2, apple3));
    Collections.sort(apples, new Comparator<Apple>() {
      public int compare(Apple a1, Apple a2) {
        return Double.compare(a1.getWeight(), a2.getWeight());
      }
    });
    apples.forEach(System.out::println);

    apples.sort(Comparator.comparing(Apple::getSize));
    apples.forEach(System.out::println);
  }
}

interface AppleBuilder<T> {
  AppleBuilder<T> setType(String type);

  AppleBuilder<T> setSize(int size);

  AppleBuilder<T> setWeight(double weight);

  T build();
}

class AppleBuilderImpl implements AppleBuilder<Apple> {
  private String type;
  private int size;
  private double weight;

  public AppleBuilderImpl() {
  }

  @Override
  public AppleBuilder<Apple> setType(String type) {
    this.type = type;
    return this;
  }

  @Override
  public AppleBuilder<Apple> setSize(int size) {
    this.size = size;
    return this;
  }

  @Override
  public AppleBuilder<Apple> setWeight(double weight) {
    this.weight = weight;
    return this;
  }

  @Override
  public Apple build() {
    return new Apple(type, size, weight);
  }
}

class Apple {
  private String type;
  private int size;
  private double weight;

  public Apple(String type, int size, double weight) {
    this.type = type;
    this.size = size;
    this.weight = weight;
  }

  public double getWeight() {
    return weight;
  }
  public int getSize() {
    return size;
  }

  public String toString() {
    return "Apple{" +
        "type='" + type +
        "', size=" + size +
        ", weight=" + weight +
        "}";
  }
}
