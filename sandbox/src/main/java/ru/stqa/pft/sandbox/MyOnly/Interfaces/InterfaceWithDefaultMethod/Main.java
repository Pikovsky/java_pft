package ru.stqa.pft.sandbox.MyOnly.Interfaces.InterfaceWithDefaultMethod;

public class Main {
  public static void main(String[] args) {
    // https://www.youtube.com/watch?v=zxcXDVotj_4&list=PL786bPIlqEjRDXpAKYbzpdTaOYsWyjtCX&index=58
    // Это "Анонимный класс", см видио на тему...
    Formula formula = new Formula() {
      @Override
      public double calculate(int a) {
        return sqrt(a * -10);
      }
    };

    System.out.println(formula.calculate(-10));     // 10.0
    System.out.println(formula.sqrt(16));           // 4.0

  }
}

