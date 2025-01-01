package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortingPerson {
  public static void main(String[] args) {

    PersonBuilt personBuilt1 = new PersonBuilt.Builder().setName("p1").setAge(3).build();
    PersonBuilt personBuilt2 = new PersonBuilt.Builder().setName("p2").setAge(33).build();

    List<PersonBuilt> personBuilts = new ArrayList<>(List.of(personBuilt1, personBuilt2));

    personBuilts.sort(Comparator.comparing(PersonBuilt::getAge));
    personBuilts.forEach(System.out::println);
  }
}

class PersonBuilt {
  private String name;
  private int age;

  private PersonBuilt(Builder builder) {
    this.name = builder.name;
    this.age = builder.age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public static class Builder {

    private String name;
    private int age;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setAge(int age) {
      this.age = age;
      return this;
    }

    public PersonBuilt build() {
      return new PersonBuilt(this);
    }

  }

  public String toString() {
    return "Person{" +
        "name='" + name +
        "', age=" + age +
        "}";
  }
}

