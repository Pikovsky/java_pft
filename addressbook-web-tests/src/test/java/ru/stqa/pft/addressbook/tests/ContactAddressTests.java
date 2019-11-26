package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

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
  public void testContactAddress() {
    app.goTo().homePage();
//    ContactData contact = app.contact().all().iterator().next();
    ContactData contact = app.db().contacts().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(), equalTo(cleaned(contactInfoFromEditForm.getAddress())));
  }

  public static String cleaned(String address){
    return address
            .replaceAll("^\\s+|\\s+$", "")  // убираем "пробелы" вначале и конце адреса.
            .replaceAll(" +\\n", "\n")      // убираем пробелы перед переводом строки.
            .replaceAll("\\n +", "\n")      // убираем пробелы после переводом строки.
            .replaceAll("\\s{2,}", " ");    // заменяем сдвоеные "пробелы" на один пробел.
  }
}
