package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
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
              //.withGroup("test1") // временно комментируем эту строку из-за того что мы удалили атрибут group.
      );
    }
  }

  @Test//(enabled=false)
  public void testContactModification(){
    Contacts before = app.contact().all();
    ContactData contactModified = before.iterator().next();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withId(contactModified.getId())
            .withFirstname("test_name")
            .withLastname("test_surname")
            .withPhoto(photo);
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.without(contactModified).withAdded(contact)));
  }
}
