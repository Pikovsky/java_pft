package ru.stqa.pft.sandbox.MyOnly.MethodReferences;

// определим интерфейс фабрики, которая будет использоваться для создания новых персон
interface PersonFactory<P extends Person> {
  P create(String firstName, String lastName);
}