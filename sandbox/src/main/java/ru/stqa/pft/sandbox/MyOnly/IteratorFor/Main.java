package ru.stqa.pft.sandbox.MyOnly.IteratorFor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Main {
  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      list.add(new Random().nextInt(50));
    }

    System.out.println(list);

    // В ForEach, так же как и в C#, нельзя удалять элементы коллекции.
    for (Integer tmp : list) {
      if (tmp < 25) {
        //list.remove(tmp);
      }
    }

    // ... а в цикле с помощью итератора - можна!!!
    for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext();) {
      if (iterator.next() < 25) iterator.remove();
    }

    //list.forEach(System.out::println);
    System.out.println(list);

    // Просто более простой путь для реализации предыдущей задачи:
    list.removeIf(element -> element < 35);
    System.out.println(list);
  }

}
