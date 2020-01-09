// :: одинаково применяется к статическим, экземплярным методам, а так же и к конструкторам.
// https://www.youtube.com/watch?v=7b7_Pd2_iAM&list=PL786bPIlqEjRDXpAKYbzpdTaOYsWyjtCX&index=341

package ru.stqa.pft.sandbox.MyOnly.MethodReferences;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DoubleDvoetochie {
  public static void main(String[] args) {

    List<List<Integer>> list = Arrays.asList(
            Arrays.asList(1,2,3),
            Arrays.asList(4,5,6)
    );

    // Тупо без ключевого слова "::"
    List<Integer> allIntegers0 = list.stream()
            .flatMap(everyListOfInt -> everyListOfInt.stream())
            .collect(Collectors.toList())
            ;

    // Не тупо с ::
    List<Integer> allIntegers = list.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList())
            ;

    // Тупо без ::
    allIntegers.stream().forEach(element -> System.out.println(element));
    // Не тупо с ::
    allIntegers0.stream().forEach(System.out::print);

    // Умно с :: и без создания локальной переменной
    list.stream()
            .flatMap(List::stream)
            .forEach(System.out::println)
            ;

    // ==============================================================================================
    // Конструкторы через ::
    // Вместо реализации интерфейса мы соединяем все вместе при помощи ссылки на конструктор наследника
    PersonFactory<Person> personFactory = Employee::new;
    Person personOfEmployer = personFactory.create("Peter", "Parker");
    System.out.println(personOfEmployer);
  }
}
