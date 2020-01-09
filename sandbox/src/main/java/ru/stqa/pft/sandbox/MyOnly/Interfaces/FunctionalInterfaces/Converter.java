package ru.stqa.pft.sandbox.MyOnly.Interfaces.FunctionalInterfaces;

// Аннотация гарантирует, что мы больше одного метода в функциональный интерфейс на захреначим...
// Код останется корректным даже если потом убрать аннотацию @FunctionalInterface.
@FunctionalInterface
public interface Converter<F, T> {
  T convert(F from);
}
