package ru.stqa.pft.sandbox.MyOnly.Interfaces.InterfaceWithDefaultMethod;

interface Formula {
  double calculate(int a);

  default double sqrt(int a) {
    return Math.sqrt(a);
  }
}
