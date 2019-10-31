package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoPageTests extends TestBase {

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

  @Test//(enabled = false)
  public void testContactViewForm() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactFromEditForm = app.contact().infoFromEditForm(contact);
    List<String> contactInfoFromViewForm = app.contact().infoFromViewForm(contact);
    List<String> contactInfoFromEditForm = mergeAttributes(contactFromEditForm);

    assertThat(contactInfoFromViewForm, equalTo(contactInfoFromEditForm));
  }

  private List<String> mergeAttributes(ContactData contact) {
    String fullName = contact.getFirstname() + " " + contact.getLastname();
    String[] address = contact.getAddress().split("\n");
    String homePhone =   (contact.getHomePhone().isEmpty())   ? "" : "H: " + contact.getHomePhone();
    String mobilePhone = (contact.getMobilePhone().isEmpty()) ? "" : "M: " + contact.getMobilePhone();
    String workPhone =   (contact.getWorkPhone().isEmpty())   ? "" : "W: " + contact.getWorkPhone();
    String email = contact.getEmail();
    String email2 = contact.getEmail2();
    String email3 = contact.getEmail3();

    List<String> info = new ArrayList<>();
    info.add(fullName);
    info.addAll((Arrays.asList(address)));
    info.addAll(Arrays.asList(homePhone, mobilePhone, workPhone, email, email2, email3));
    return info
            .stream()
            .map(ContactInfoPageTests::cleaned)
            .filter(s -> ! s.equals(""))
            .collect(Collectors.toList());
  }

  public static String cleaned(String attribute){
    return attribute
            .replaceAll("^\\s+|\\s+$", "")  // убираем "пробелы" вначале и конце адреса.
            .replaceAll(" +\\n", "\n")      // убираем пробелы перед переводом строки.
            .replaceAll("\\n +", "\n")      // убираем пробелы после переводом строки.
            .replaceAll("\\s{2,}", " ");    // заменяем сдвоеные "пробелы" на один пробел.
  }
}
