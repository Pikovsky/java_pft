package ru.stqa.pft.sandbox.MyOnly.Generics;

import java.util.List;

// Снять коментарий и получишь wildcard ошибку:
public class WildcardError {
  void foo(List<?> i) {
    // i.set(0, i.get(0)); // Wrong 2nd argument type. Found: '?', required: '?'
  }
}
