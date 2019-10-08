package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

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
    Set<ContactData> before = app.contact().all();
    ContactData contactDeleted = before.iterator().next();
    app.contact().delete(contactDeleted);
    app.goTo().homePage();
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(contactDeleted);
    Assert.assertEquals(before, after);
  }
}
