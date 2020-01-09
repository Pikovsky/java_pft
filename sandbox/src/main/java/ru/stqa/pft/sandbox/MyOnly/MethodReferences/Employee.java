package ru.stqa.pft.sandbox.MyOnly.MethodReferences;

public class Employee extends Person {
  int id;

  @Override
  public String toString() {
    return "Employer{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", id=" + id +
            '}';
  }

  public Employee(String firstName, String lastName) {
    super(firstName, lastName);
    this.id = 5; // надо автоматически присваивать оригинальный id, но лень...
  }
}