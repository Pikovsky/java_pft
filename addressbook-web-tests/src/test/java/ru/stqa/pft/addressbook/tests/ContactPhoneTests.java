package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

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
  public void testContactPhones() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

  public static String cleaned(String phone){
    return phone.replaceAll("\\s","" ).replaceAll("[-()]", "");
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream()
            .filter(s -> ! s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  // то же самое, что и mergePhones, только другой стиль.
  private String mergePhones2(ContactData contact) {
    String result = "";
    if (!contact.getHomePhone().isEmpty())   result = result + cleaned(contact.getHomePhone())   + "\n";
    if (!contact.getMobilePhone().isEmpty()) result = result + cleaned(contact.getMobilePhone()) + "\n";
    if (!contact.getWorkPhone().isEmpty())   result = result + cleaned(contact.getWorkPhone());
    return result;
  }
}
