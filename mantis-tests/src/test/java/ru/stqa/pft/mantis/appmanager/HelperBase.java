package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.*;
import java.io.File;

public class HelperBase {

  protected final ApplicationManager app;
  protected WebDriver wd;
  private boolean acceptNextAlert = true;

  public HelperBase(ApplicationManager app) {
    this.app = app;
    this.wd = app.getDriver();
  }

  protected void type(By locator, String text) {
    click(locator);
    if (text != null){
      String existingText = wd.findElement(locator).getAttribute("value");
      if (! text.equals(existingText)){
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(text);
      }
    }
  }

  protected void attach(By locator, File file) {
    if (file != null){
        wd.findElement(locator).sendKeys(file.getAbsolutePath());
    }
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  // Эта функция определяет вылетели ли мы в окно alert?
  public boolean isAlertPresent() {        // Эта ф-я была и у Баранцева в Selenium Builder.
    try {                                   // Баранцев сказал, что её можно было и удалить,
      wd.switchTo().alert();                // но потом объяснял на ней механизм исключений.
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  // Думаю, что функци получает сообщение окна alert, и выходит из него.
  public String closeAlertAndGetItsText() { // а остальных и не было.
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

  // функция тестирует наличие элемента на странице.
  public boolean isElementPresent(By locator) {  // а остальных и не было.
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}
