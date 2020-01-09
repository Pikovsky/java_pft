package ru.stqa.pft.sandbox.MyOnly.MethodReferences;

// Конструкторы через ::
// определим бин с несколькими конструкторами
public class Person {
  String firstName;
  String lastName;

  Person() {}

  Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "Person{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }
}
