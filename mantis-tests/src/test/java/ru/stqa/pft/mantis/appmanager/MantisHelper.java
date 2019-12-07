package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class MantisHelper extends HelperBase{

  public MantisHelper(ApplicationManager app) {
    super(app);
  }

  public void requestToChangeUserPassword(String user) {
    loginAsAdministrator();
    gotoUserManagerPage();
    manageUser(user);
    sendResetPasswordMail();
  }

  public void login(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.xpath("//input[@value='Login']"));
  }

  public void loginAsAdministrator() {
    login("Administrator", "root");
  }

  public void gotoUserManagerPage(){
    if (isElementPresent(By.xpath("//strong[contains(text(),'All']")) &&
            isElementPresent(By.xpath("//input[@value='Manage User']"))){
      return;
    }
    if(isElementPresent(By.linkText("Manage users"))){
      click(By.linkText("Manage users"));
    } else {
      gotoManagePage();
      click(By.linkText("Manage Users"));
    }
  }

  // Что бы зайти на страницу 'Manage User' с любой страницы  надо сначала попасть на эту.
  public void gotoManagePage() {
    if (isElementPresent(By.xpath("//td[contains(text(),'Site Information'")) &&
            isElementPresent(By.xpath("//td[contains(text(),'MantisBT Version'"))){
      return;
    }
    if(isElementPresent(By.linkText("Manage"))){
      click(By.linkText("Manage"));
    }
  }

  public void manageUser(String user){
    //wd.findElement(By.linkText(user)).click(); // просто альтернатива
    type(By.name("username"), user);
    click(By.xpath("//input[@value='Manage User']"));
  }

  public void sendResetPasswordMail(){
    click(By.xpath("//input[@value='Reset Password']"));
  }

  public void setNewPassword(String confirmationLink, String newPassword) {
    wd.get(confirmationLink);
    type(By.name("password"), newPassword);
    type(By.name("password_confirm"), newPassword);
    click(By.cssSelector("input[value='Update User']"));
  }
}
