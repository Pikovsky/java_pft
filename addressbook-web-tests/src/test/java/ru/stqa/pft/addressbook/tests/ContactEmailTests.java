package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

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

  @Test//(enabled = false)
  public void testContactEmails() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream()
            .filter(s -> ! s.equals(""))
            .collect(Collectors.joining("\n"));
  }
}
