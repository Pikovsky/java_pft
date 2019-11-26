package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size()==0) {
      app.goTo().groupPage();
      app.group().create(new GroupData()
              .withName("group1")
              .withHeader("hiiiii")
              .withFooter("fooo"));
    }
    Groups groups = app.db().groups();
//    app.goTo().homePage();
//    if (app.contact().all().size() == 0) {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstname("test_name")
              .withLastname("test_surname")
              .withAddress("Earth")
              .withMobilePhone("+382345678901")
              .withEmail("alfa@beta.com")
              .inGroup(groups.iterator().next()));
    }
  }

  @Test//(enabled=false)
  public void testContactModification(){
//    Contacts before = app.contact().all();
    Contacts before = app.db().contacts();
    ContactData contactModified = before.iterator().next();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withId(contactModified.getId())
            .withFirstname("test_name")
            .withLastname("test_surname")
            .withPhoto(photo);
    app.goTo().homePage();
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
//    Contacts after = app.contact().all();
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(contactModified).withAdded(contact)));
    verifyContactListInUI();
  }
}
