package ru.stqa.pft.mantis.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Objects;

import javax.persistence.*;

@XStreamAlias("user")
@Entity
@Table(name="mantis_user_table")
public class UserData {

  @XStreamOmitField
  @Id
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "username")
  private String userName;

  @Expose
  private String email;

  @Expose
  private String password;

  public void setId(int id) {
    this.id = id;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public UserData withUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserData withPassword(String password) {
    this.password = password;
    return this;
  }

  public int getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "UserData{" +
            "id=" + id +
            ", name='" + userName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData groupData = (UserData) o;
    return id == groupData.id &&
            Objects.equals(userName, groupData.userName) &&
            Objects.equals(email, groupData.email) &&
            Objects.equals(password, groupData.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userName, email, password);
  }
}

