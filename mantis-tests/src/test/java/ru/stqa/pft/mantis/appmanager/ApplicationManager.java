package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {
  private final Properties properties;
  private WebDriver wd;
  protected StringBuffer verificationErrors = new StringBuffer();
  private String browser;
  private RegistrationHelper registrationHelper;
  private FtpHelper ftp;
  private MailHelper mailHelper;
  private JamesHelper jamesHelper;
  private MantisHelper mantisHelper;
  private DbHelper dbHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

    System.setProperty("webdriver.gecko.driver",  properties.getProperty("gecko.driver","libs/geckodriver.exe"));
    System.setProperty("webdriver.chrome.driver", properties.getProperty("chrome.driver","libs/chromedriver.exe"));
    System.setProperty("webdriver.ie.driver",     properties.getProperty("ie.driver","libs/IEDriverServer.exe"));
  }

  public void stop() {
    if (this.wd != null){
      this.wd.quit();
    }
    String verificationErrorString = verificationErrors.toString(); // код далее - из Katalon-a.
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString); //Fails a test with the given message.
    }
  }

  public HttpSession newSession(){
    return new HttpSession(this);
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public DbHelper db(){
    if (this.dbHelper == null) {
      this.dbHelper = new DbHelper();
    }
    return this.dbHelper;
  }

  public MantisHelper mantis(){
    if (this.mantisHelper == null) {
      this.mantisHelper = new MantisHelper(this);
    }
    return this.mantisHelper;
  }

  public RegistrationHelper registration() {
    if (registrationHelper == null) {
      registrationHelper = new RegistrationHelper(this);
    }
    return registrationHelper;
  }

  public JamesHelper james(){
    if (this.jamesHelper == null) {
      this.jamesHelper = new JamesHelper(this);
    }
    return this.jamesHelper;
  }

  public MailHelper mail(){
    if (this.mailHelper == null) {
      this.mailHelper = new MailHelper(this);
    }
    return this.mailHelper;
  }

  public FtpHelper ftp(){
    if (this.ftp == null) {
      this.ftp = new FtpHelper(this);
    }
    return this.ftp;
  }

  public WebDriver getDriver() {
    if (this.wd == null){
      switch (browser) {
        case BrowserType.FIREFOX:
          this.wd = new FirefoxDriver();
          break;
        case BrowserType.CHROME:
          this.wd = new ChromeDriver();
          break;
        case BrowserType.IE:
          this.wd = new InternetExplorerDriver();
          break;
      }
      wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      wd.get(properties.getProperty("web.baseUrl"));
    }
    return this.wd;
  }
}
