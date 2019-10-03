package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

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
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void submitContactCreation(){
    click(By.name("submit"));
  }

  public void returnToHomePage(){
    click(By.linkText("home page"));
  }

  public void initContactModification(){
    click(By.cssSelector("img[alt='Edit']"));
  }

  //3.10
  public void selectContact(int index){
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void submitContactModification(){
    click(By.name("update"));
  }

  public void deleteSelectedContact() {
    click(By.cssSelector("input[value='Delete']"));
  }

  public void acceptDeletionContact() {

    wd.switchTo().alert().accept();
  }
}
