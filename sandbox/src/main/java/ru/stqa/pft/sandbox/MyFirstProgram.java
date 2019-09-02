package ru.stqa.pft.sandbox;

public class MyFirstProgram {


    public static void main(String[] args) {
        hello("world");
        hello("user");
        hello("Alexei");

        Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area() );

        Rectangle r = new Rectangle(4, 6);
        System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area() );

        Point p1 = new Point(10, 10);
        Point p2 = new Point(6, 7);

        System.out.println("Расстояние между точками p1 и p2 = " + Point.distance(p1, p2) );




    }

    private static void hello(String somebody) {
        System.out.println("Hello " + somebody + "!");
    }


}

