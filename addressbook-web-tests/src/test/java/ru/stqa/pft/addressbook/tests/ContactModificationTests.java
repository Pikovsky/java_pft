package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Set;

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
    Set<ContactData> before = app.contact().all();
    ContactData contactModified = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(contactModified.getId())
            .withFirstname("test_name")
            .withLastname("test_surname");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());
    before.remove(contactModified);
    before.add(contact);
    Assert.assertEquals(after, before);
  }
}
