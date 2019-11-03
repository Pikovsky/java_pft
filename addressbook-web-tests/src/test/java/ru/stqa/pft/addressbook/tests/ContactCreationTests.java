package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test//(enabled=false)
  public void testContactCreation(){
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withFirstname("test_name")
            .withLastname("test_surname")
            .withPhoto(photo)
            .withGroup("test1");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo( before.size() + 1));

    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testPublicDir(){
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());

    File file = new File("src/test/resources/stru.png");
    System.out.println(file.getAbsolutePath());
    System.out.println(file.exists());
  }
}
