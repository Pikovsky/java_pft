package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

  @BeforeClass
  public void init(){
    RestAssured.authentication = RestAssured.basic("52c5391605f6ce7dc50ef9619ea2b354", "");
  }

  @Test
  public void testCreateIssue() {
    Set<Issue> oldIssues = getIssues();
    System.out.println(oldIssues.size());// удалить
    Issue newIssue = new Issue()
            .withSubject("Test issue from RestAssure")
            .withDescription("New test issue from RestAssure");
    int issueId = createIssue(newIssue);
    System.out.println(issueId);// удалить
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue
            .withId(issueId));
    assertEquals(oldIssues, newIssues);
  }

  private Set<Issue> getIssues() {
    String json = RestAssured
            .get("http://demo.bugify.com/api/issues.json?limit=3000")
            .asString();

    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType()); // Set<Issue>.class - нельзя.
  }

  private int createIssue(Issue newIssue) {
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .post("http://demo.bugify.com/api/issues.json")
            .asString()
            ;
    System.out.println(json); // удалить
    return JsonParser.parseString(json).getAsJsonObject().get("issue_id").getAsInt();
  }
}
