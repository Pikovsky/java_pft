package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {

  private int id;

  private final String firstname;

  private final String secondname;

  private final String group;

  public ContactData(int id, String firstname, String secondname, String group){
    this.id = id;
    this.firstname = firstname;
    this.secondname = secondname;
    this.group = group;
  }

  public ContactData(String firstname, String secondname, String group){
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.secondname = secondname;
    this.group = group;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return secondname;
  }

  public String getGroup() {
    return group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(firstname, that.firstname) &&
            Objects.equals(secondname, that.secondname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, secondname);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", secondname='" + secondname + '\'' +
            '}';
  }
}
