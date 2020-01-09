// Functional interfaces provide target types for lambda expressions and method references
// https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
// https://www.tutorialspoint.com/java8/java8_functional_interfaces.htm
package ru.stqa.pft.sandbox.MyOnly.Interfaces.FunctionalInterfaces;

import java.util.function.*;

public class FunctionalInterfacesLesson {

    public static void main(String[] args) {

    // Это пример не стандартного функционального интерфейса (не java.util.function.*)
    Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
    Integer converted = converter.convert("123");
    System.out.println(converted);    // 123


    // Пример полной записи инициализации функционального интерфейса с помощью анонимного класса:
    Predicate<Integer> bigPredicate = new Predicate<Integer>(){
      @Override
      public boolean test(Integer integer) {
        return integer > 5;
      }
    };

    System.out.println(bigPredicate.test(8));

    Predicate<String> smallPredicate = String::isEmpty;
    System.out.println(smallPredicate.test("Putin Hello"));

    System.out.println("==================");
    //===========================================================


    // Это пример стандартного функционального интерфейса (java.util.function.*)
    Predicate<Integer> predicate = x -> x > 5;
    Consumer<Integer> consumer = x -> System.out.println(x);
    Function<Integer, String> function = x -> x.toString();
    Supplier<Integer> supplier = () -> new Integer(5);
    UnaryOperator<Double> unaryOperator = Math::sqrt;
    BinaryOperator<Integer> binaryOperator = (x, y) -> x * y;



    System.out.println(predicate.test(3));
    consumer.accept(33);
    System.out.println(function.apply(30));
    System.out.println(supplier.get());
    System.out.println(unaryOperator.apply(121.0));
    System.out.println(binaryOperator.apply(5, 3));
  }


  /**
   * Interface	Description
   *
   * BiConsumer<T,U>
   * Represents an operation that accepts two input arguments and returns no result.
   *
   * BiFunction<T,U,R>
   * Represents a function that accepts two arguments and produces a result.
   *
   * BinaryOperator<T>
   * Represents an operation upon two operands of the same type, producing a result of the same type as the operands.
   *
   * BiPredicate<T,U>
   * Represents a predicate (boolean-valued function) of two arguments.
   *
   * BooleanSupplier
   * Represents a supplier of boolean-valued results.
   *
   * Consumer<T>
   * Represents an operation that accepts a single input argument and returns no result.
   *
   * DoubleBinaryOperator
   * Represents an operation upon two double-valued operands and producing a double-valued result.
   *
   * DoubleConsumer
   * Represents an operation that accepts a single double-valued argument and returns no result.
   *
   * DoubleFunction<R>
   * Represents a function that accepts a double-valued argument and produces a result.
   *
   * DoublePredicate
   * Represents a predicate (boolean-valued function) of one double-valued argument.
   *
   * DoubleSupplier
   * Represents a supplier of double-valued results.
   *
   * DoubleToIntFunction
   * Represents a function that accepts a double-valued argument and produces an int-valued result.
   *
   * DoubleToLongFunction
   * Represents a function that accepts a double-valued argument and produces a long-valued result.
   *
   * DoubleUnaryOperator
   * Represents an operation on a single double-valued operand that produces a double-valued result.
   *
   * Function<T,R>
   * Represents a function that accepts one argument and produces a result.
   *
   * IntBinaryOperator
   * Represents an operation upon two int-valued operands and producing an int-valued result.
   *
   * IntConsumer
   * Represents an operation that accepts a single int-valued argument and returns no result.
   *
   * IntFunction<R>
   * Represents a function that accepts an int-valued argument and produces a result.
   *
   * IntPredicate
   * Represents a predicate (boolean-valued function) of one int-valued argument.
   *
   * IntSupplier
   * Represents a supplier of int-valued results.
   *
   * IntToDoubleFunction
   * Represents a function that accepts an int-valued argument and produces a double-valued result.
   *
   * IntToLongFunction
   * Represents a function that accepts an int-valued argument and produces a long-valued result.
   *
   * IntUnaryOperator
   * Represents an operation on a single int-valued operand that produces an int-valued result.
   *
   * LongBinaryOperator
   * Represents an operation upon two long-valued operands and producing a long-valued result.
   *
   * LongConsumer
   * Represents an operation that accepts a single long-valued argument and returns no result.
   *
   * LongFunction<R>
   * Represents a function that accepts a long-valued argument and produces a result.
   *
   * LongPredicate
   * Represents a predicate (boolean-valued function) of one long-valued argument.
   *
   * LongSupplier
   * Represents a supplier of long-valued results.
   *
   * LongToDoubleFunction
   * Represents a function that accepts a long-valued argument and produces a double-valued result.
   *
   * LongToIntFunction
   * Represents a function that accepts a long-valued argument and produces an int-valued result.
   *
   * LongUnaryOperator
   * Represents an operation on a single long-valued operand that produces a long-valued result.
   *
   * ObjDoubleConsumer<T>
   * Represents an operation that accepts an object-valued and a double-valued argument, and returns no result.
   *
   * ObjIntConsumer<T>
   * Represents an operation that accepts an object-valued and a int-valued argument, and returns no result.
   *
   * ObjLongConsumer<T>
   * Represents an operation that accepts an object-valued and a long-valued argument, and returns no result.
   *
   * Predicate<T>
   * Represents a predicate (boolean-valued function) of one argument.
   *
   * Supplier<T>
   * Represents a supplier of results.
   *
   * ToDoubleBiFunction<T,U>
   * Represents a function that accepts two arguments and produces a double-valued result.
   *
   * ToDoubleFunction<T>
   * Represents a function that produces a double-valued result.
   *
   * ToIntBiFunction<T,U>
   * Represents a function that accepts two arguments and produces an int-valued result.
   *
   * ToIntFunction<T>
   * Represents a function that produces an int-valued result.
   *
   * ToLongBiFunction<T,U>
   * Represents a function that accepts two arguments and produces a long-valued result.
   *
   * ToLongFunction<T>
   * Represents a function that produces a long-valued result.
   *
   * UnaryOperator<T>
   * Represents an operation on a single operand that produces a result of the same type as its operand.*/

}
