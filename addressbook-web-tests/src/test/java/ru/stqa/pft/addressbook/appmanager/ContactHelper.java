package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.ArrayList;
import java.util.List;

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

  public void initContactModification(int index){
    wd.findElements(By.cssSelector("img[alt='Edit']")).get(index).click();
  }

  public void submitContactModification(){
    click(By.name("update"));
  }

  public void selectContact(int index){
    wd.findElements(By.name("selected[]")).get(index).click();
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

  public void createContact(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    returnToHomePage();
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<>();
    List<WebElement> elements =  wd.findElements(By.cssSelector("tr[name='entry']"));
    for(WebElement element: elements){
      List<WebElement> tdsOfRow = element.findElements(By.tagName("td"));
      String name = tdsOfRow.get(2).getText();
      int id = Integer.parseInt(tdsOfRow.get(0).findElement(By.tagName("input")).getAttribute("id"));
      ContactData contact = new ContactData(id, name, null, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
