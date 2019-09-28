package ru.stqa.pft.sandbox;

public class Equality {

  public static void main(String[] args) {
//    String s1 = "firefox";
//    String s2 = s1;
//    System.out.println(s1 == s2);       //true
//    System.out.println(s1.equals(s2));  //true

//    String s1 = "firefox";
//    String s2 = new String("firefox");
//    System.out.println(s1 == s2);       //false
//    System.out.println(s1.equals(s2));  //true

//    String s1 = "firefox";
//    String s2 = "firefox";
//    System.out.println(s1 == s2);       //true
//    System.out.println(s1.equals(s2));  //true

//    String s1 = "firefox";
//    String s2 = "fire" + "fox";         // будет склеено на этапе компилящии
//    System.out.println(s1 == s2);       //true
//    System.out.println(s1.equals(s2));  //true

    String s1 = "firefox 2.0";
    String s2 = "firefox " + Math.sqrt(4.0); // будет склеено в рантайме
    System.out.println(s1 == s2);       //false
    System.out.println(s1.equals(s2));  //true
  }
}
