package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    attach(By.name("photo"), contactData.getPhoto());

    if(creation){
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size()==1);
        String ourGroup = contactData.getGroups().iterator().next().getName();
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(ourGroup);
      }
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

  public void submitContactModification(){
    click(By.name("update"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
  }

  public void addContactToGroup() {
//    wd.findElement(By.xpath("//input[@type='submit']")).click();  //было в краденом.
//    wd.findElement(By.xpath("//input[@name='add']")).click();
    wd.findElement(By.name("add")).click(); //.xpath("//input[@name='add']")).click();
  }
  public void removeContactFromGroup() {
//    wd.findElement(By.xpath("//input[@name='remove']")).click();   //было в краденом.
    wd.findElement(By.name("remove")).click();
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
    this.contactCache = null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    this.contactCache = null;
    returnToHomePage();
  }

  public void delete(ContactData contactDeleted) {
    selectContactById(contactDeleted.getId());
    deleteSelectedContact();
    this.contactCache = null;
    acceptDeletionContact();
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (this.contactCache != null) {
      return new Contacts(this.contactCache);
    }
    this.contactCache = new Contacts();
    List<WebElement> rows =  wd.findElements(By.name("entry"));
    for(WebElement row : rows){
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      this.contactCache.add(new ContactData()
              .withId(id)
              .withFirstname(firstname)
              .withLastname(lastname)
              .withAddress(address)
              .withAllEmails(allEmails)
              .withAllPhones(allPhones));
    }
    return new Contacts(this.contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getText();
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFirstname(firstname)
            .withLastname(lastname)
            .withAddress(address)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3);
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
    //3 альтернативных селектора (для изучения):
//    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a",id))).click();
//    wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a",id))).click();
//    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s",id))).click();
  }

  public List<String> infoFromViewForm(ContactData contact) {
    initContactViewById(contact.getId());
    List<String> result = Arrays.asList(wd.findElement(By.xpath("//div[@id='content']"))
            .getText()
            .split("\n"))
            .stream()
            .filter(s -> ! s.equals(""))
            .collect(Collectors.toList());
    wd.navigate().back();
    return result;
  }

  private void initContactViewById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s",id))).click();
  }
}
