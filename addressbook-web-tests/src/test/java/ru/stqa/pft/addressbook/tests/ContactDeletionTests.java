package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("test_name")
              .withLastname("test_surname")
              .withGroup("test1"));
    }
  }

  @Test//(enabled=false)
  public void testContactDeletion(){
    Contacts before = app.contact().all();
    ContactData contactDeleted = before.iterator().next();
    app.contact().delete(contactDeleted);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo( before.size() - 1));

    Contacts after = app.contact().all();
    assertThat(before.without(contactDeleted), equalTo(after));
  }
}
