package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

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

  @Test//(enabled = false)
  public void testContactAddress() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
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
