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

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size()==0) {
      app.goTo().groupPage();
      app.group().create(new GroupData()
              .withName("  @BeforeMethod\n" +
                      "  public void ensurePreconditions() {\n" +
                      "    if (app.db().groups().size()==0) {\n" +
                      "      app.goTo().groupPage();\n" +
                      "      app.group().create(new GroupData()\n" +
                      "              .withName(\"test1\")\n" +
                      "              .withHeader(\"hiiiii\")\n" +
                      "              .withFooter(\"fooo\"));\n" +
                      "    }\n" +
                      "    Groups groups = app.db().groups();\n" +
                      "//    app.goTo().homePage();\n" +
                      "//    if (app.contact().all().size() == 0) {\n" +
                      "    if (app.db().contacts().size() == 0) {\n" +
                      "      app.goTo().homePage();\n" +
                      "      app.contact().create(new ContactData()\n" +
                      "              .withFirstname(\"test_name\")\n" +
                      "              .withLastname(\"test_surname\")\n" +
                      "              .withAddress(\"Earth\")\n" +
                      "              .withMobilePhone(\"+382345678901\")\n" +
                      "              .withEmail(\"alfa@beta.com\")\n" +
                      "              .inGroup(groups.iterator().next()));\n" +
                      "    }\n" +
                      "  }")
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
