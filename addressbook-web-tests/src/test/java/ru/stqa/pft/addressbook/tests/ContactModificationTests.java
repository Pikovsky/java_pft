package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

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
  public void testContactModification(){
    Contacts before = app.contact().all();
    ContactData contactModified = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(contactModified.getId())
            .withFirstname("test_name")
            .withLastname("test_surname");
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.without(contactModified).withAdded(contact)));
  }
}
