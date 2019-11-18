package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table(name="addressbook")
public class ContactData {

  @XStreamOmitField
  @Id
  @Column(name = "id") // Эту аннотацию можно было не указывать, т.к. имена класса и столбца совпадают
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "firstname") // Эту аннотацию можно было не указывать, т.к. имена класса и столбца совпадают
  private String firstname;

  @Expose
  @Column(name = "lastname")
  private String secondname;

//  @Expose
//  @Transient
//  private String group;
//  //transient private String group;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="address_in_groups", joinColumns=@JoinColumn(name="id"), inverseJoinColumns = @JoinColumn(name="group_id"))
  private Set<GroupData> groups = new HashSet<>();

  @Expose
  @Column(name = "home")
  @Type(type="text")
  private String homePhone;

  @Expose
  @Column(name = "mobile")
  @Type(type="text")
  private String mobilePhone;

  @Expose
  @Column(name = "work")
  @Type(type="text")
  private String workPhone;

  @XStreamOmitField
  @Transient
  private String allPhones;

  @Expose
  @Column(name = "address") // Эту аннотацию можно было не указывать, т.к. имена класса и столбца совпадают
  @Type(type="text")
  private String address;

  @Expose
  @Column(name = "email")
  @Type(type="text")
  private String Email;

  @Expose
  @Column(name = "email2")
  @Type(type="text")
  private String Email2;

  @Expose
  @Column(name = "email3")
  @Type(type="text")
  private String Email3;

  @XStreamOmitField
  @Transient
  private String allEmails;

  @Expose
  @Column(name = "photo") // Эту аннотацию можно было не указывать, т.к. имена класса и столбца совпадают
  @Type(type="text")
  private String photo; // В БД этот столбец типа mediumtext, что ближе к Strng. По этому

  //private File photo; // меняем тип поля, а преобразования проводим в getter/setter-ах.
  public File getPhoto() {
    return new File(photo);
    //return photo;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }

  public String getAddress() {
    return address;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public String getEmail() {
    return Email;
  }

  public ContactData withEmail(String Email) {
    this.Email = Email;
    return this;
  }

  public String getEmail2() {
    return Email2;
  }

  public ContactData withEmail2(String Email2) {
    this.Email2 = Email2;
    return this;
  }

  public String getEmail3() {
    return Email3;
  }

  public ContactData withEmail3(String Email3) {
    this.Email3 = Email3;
    return this;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public String getFirstname() {
    return firstname;
  }

  public ContactData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public String getLastname() {
    return secondname;
  }

  public ContactData withLastname(String secondname) {
    this.secondname = secondname;
    return this;
  }

  public Groups getGroups() {
    return new Groups(groups);
  }

//  public String getGroup() {
//    return group;
//  }

//  public ContactData withGroup(String group) {
//    this.group = group;
//    return this;
//  }

  public String getHomePhone() {
    return homePhone;
  }

  public ContactData withHomePhone(String homePhone) {
    this.homePhone = homePhone;
    return this;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public ContactData withMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }

  public String getWorkPhone() {
    return workPhone;
  }

  public ContactData withWorkPhone(String workPhone) {
    this.workPhone = workPhone;
    return this;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", secondname='" + secondname + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(secondname, that.secondname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, secondname);
  }

  public ContactData inGroup(GroupData group) {
    groups.add(group);
    return this;
  }
}
