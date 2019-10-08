package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {
  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void initContactCreation(){
    click(By.linkText("add new"));
  }

  public void fillContactForm(ContactData contactData, boolean creation){
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());

    if(creation){
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group"))); // В форме редактирования этого WebElement быть не должно!
    }
  }

  public void submitContactCreation(){
    click(By.name("submit"));
  }

  public void returnToHomePage(){
    click(By.linkText("home page"));
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cels = row.findElements(By.tagName("td"));
    cels.get(7).findElement(By.tagName("a")).click();

  }

  public void submitContactModification(){
    click(By.name("update"));
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
  }

  public void deleteSelectedContact() {
    click(By.cssSelector("input[value='Delete']"));
  }

  public void acceptDeletionContact() {
    wd.switchTo().alert().accept();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    returnToHomePage();
  }

  public void delete(ContactData contactDeleted) {
    selectContactById(contactDeleted.getId());
    deleteSelectedContact();
    acceptDeletionContact();
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public Set<ContactData> all() {
    Set<ContactData> contacts = new HashSet<>();
    List<WebElement> rows =  wd.findElements(By.name("entry"));
    for(WebElement row : rows){
      List<WebElement> cels = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cels.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String lastname = cels.get(1).getText();
      String firstname = cels.get(2).getText();
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }
}
