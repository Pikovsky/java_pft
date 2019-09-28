package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;

public class HelperBase {
  protected WebDriver wd;
  private boolean acceptNextAlert = true;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  protected void type(By locator, String text) {
    click(locator);
    if (text != null){
      wd.findElement(locator).clear();
      wd.findElement(locator).sendKeys(text);
    }
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  private boolean isAlertPresent() {        // Эта ф-я была и у Баранцева в Selenium Builder.
    try {                                   // Баранцев сказал, что её можно было и удалить.
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private boolean isElementPresent(By by) {  // а остальных и не было.
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() { // а остальных и не было.
    try {
      Alert alert = wd.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
