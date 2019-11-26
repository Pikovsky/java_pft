package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.security.cert.CollectionCertStoreParameters;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); // с дефолтным значением.
//  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser")); // нужна командная строка или параметры в настройках запуска.

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p){
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod(alwaysRun = true)
  public void logTestStop(Method m, Object[] p){
    logger.info("Stop test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  public void verifyGroupListInUa() {
    if (Boolean.getBoolean("verifyUI")) { // Метод будет работать только при установке системного свойства.
      Groups dbGroups = app.db().groups();      // получаем группы через БД.
      Groups uiGroups = app.group().all();      // получаем группы из UI.
      assertThat(uiGroups, equalTo(dbGroups     // для сравнения убираем из dbGroups
              .stream()                         // атрибуты header и footer.
              .map((g) -> new GroupData()
                      .withId(g.getId())
                      .withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      //System.out.println("db" + dbContacts);
      Contacts uiContacts = app.contact().all();
      //System.out.println("ui" + uiContacts);
      assertThat(uiContacts, equalTo(dbContacts.stream()
              .map((g) -> new ContactData()
                      .withId(g.getId())
                      .withFirstname(g.getFirstname())
                      .withLastname(g.getLastname())
                      .withAllPhones(g.getAllPhones())
                      .withAllEmails(g.getAllEmails())
                      .withAddress(g.getAddress()))
              .collect(Collectors.toSet())));
    }
  }
}
