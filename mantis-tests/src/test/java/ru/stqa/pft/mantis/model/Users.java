package ru.stqa.pft.mantis.model;

import com.google.common.collect.ForwardingList;
import java.util.*;

public class Users extends ForwardingList<UserData> {

  private List<UserData> delegate;

  public Users(Collection<UserData> groups) {
    this.delegate = new ArrayList<>(groups);
  }

  public Users() {
    this.delegate = new ArrayList<>();
  }

  @Override
  protected List delegate() {
    return delegate;
  }

  public Users withAdded(UserData user){
    Users users = new Users(this);
    users.add(user);
    return users;
  }

  public Users without(UserData user){
    Users users = new Users(this);
    users.remove(user);
    return users;
  }
}
