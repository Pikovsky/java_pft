package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

public class ChangeUserPasswordTests extends TestBase {

  @BeforeMethod
  public void StartMailServer(){
    app.mail().start();
  }

  @Test
  public void testChangeUserPassword() throws IOException {

    UserData randomUser = app.db().getRandomUser();
    String newPassword = "NewPassword2";
    System.out.println(randomUser);
    app.mantis().requestToChangeUserPassword(randomUser.getUserName());
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, randomUser.getEmail());
    app.mantis().setNewPassword(confirmationLink, newPassword);
    assertTrue(app.newSession().login(randomUser.getUserName(), newPassword));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages
            .stream()
            .filter((m) -> m.to.equals(email))
            .findFirst()
            .get()
            ;
    VerbalExpression regex = VerbalExpression.regex()
            .find("http://")
            .nonSpace()
            .oneOrMore()
            .build();
    return regex.getText(mailMessage.text);
  }

  private String findConfirmationLink2(List<MailMessage> mailMessages, String email) {
    // этот патерн сгенерировала аналогичная ф-я (вверху которая).
    String regExp = "(?:http\\:\\/\\/)(?:\\S)+";
    Pattern pattern = Pattern.compile(regExp);
    String mailBody = mailMessages
            .stream()
            .filter(mailMessage -> mailMessage.to.equals(email))
            .findAny()
            .get()
            .text
            ;
    Matcher matcher = pattern.matcher(mailBody);
    String resultLink = null;
    if (matcher.find()) {
      resultLink = matcher.group(0);
    }
    return resultLink;
  }

  @AfterMethod(alwaysRun=true)
  public void StopMailServer(){
    app.mail().stop();
  }

}
