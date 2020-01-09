package ru.stqa.pft.sandbox.MyOnly.Generics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

interface Flyer {
  void fly();
}

class HangGlider implements Flyer {
  public void fly() {
    System.out.println("dir-dir-dir");
  }
}

class Goose implements Flyer {
  public void fly() {
    System.out.println("krja-krja");
  }
}

public class Main {
  public static void main(String[] args) {
    //PECS stands for producer - extends, consumer - super
    //Do not use wildcard types as return types
    List<Flyer> listHangGlider = new ArrayList<>();
    List<Goose> listGeese = new ArrayList<>();

    listHangGlider.add(new HangGlider());
    listHangGlider.add(new Goose());

    listGeese.add(new Goose());

    anyFlyer(listHangGlider);
    //anyFlyer(listGeese); // Гусей нельзя добавить в список летунов.

    groupOfFlyers(listHangGlider);
    groupOfFlyers(listGeese);

//    List<IOException> exceptions = new ArrayList<IOException>();
    List<? super IOException> exceptions = new ArrayList<Exception>();
//    List<? super IOException> exceptions = new ArrayList<IOException>();
    exceptions.add(new IOException());
    exceptions.add(new FileNotFoundException());
    //exceptions.add(new Exception()); // DOES NOT COMPILE

    // ------------------------------------------
    // WildcardError  :
    final WildcardError wildcardError = new WildcardError();
    final WildcardFixed wildcardFixed = new WildcardFixed();
    List<Integer> list = new ArrayList<>();
    list.add(5);
    list.add(6);
    wildcardFixed.foo(list);
    list.stream().forEach(System.out::println);
  }

  public static void anyFlyer(List<Flyer> flyer) {

    flyer.stream().forEach(Flyer::fly);
  }
  private static void groupOfFlyers(List<? extends Flyer> flyer) {

    flyer.stream().forEach(Flyer::fly);
  }
}






