package ru.stqa.pft.sandbox.MyOnly.OverrideExample;

class Parent {
  Parent method() {
    System.out.println("Parent method");
    return new Parent();
  }
}

class Child extends Parent {
  @Override
  Child method() {
    System.out.println("Child method");
    super.method();
    return new Child();
  }
}

public class OverrideExample {
  public static void main(String[] args) {
    Parent parent = new Parent();
    Parent child = new Child();
    parent.method();
    System.out.println("----------");
    child.method();
  }
}
