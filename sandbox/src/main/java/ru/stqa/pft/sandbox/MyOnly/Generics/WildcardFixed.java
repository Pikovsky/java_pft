package ru.stqa.pft.sandbox.MyOnly.Generics;

import java.util.List;

public class WildcardFixed {
  void foo(List<?> i) {
    fooHelper(i);
  }

  // Helper method created so that the wildcard can be captured
  // through type inference.
  private  <T> void fooHelper(List<T> l) {
    T temp = l.get(0);
    l.set(0, l.get(1));
    l.set(1, temp);
  }
}

