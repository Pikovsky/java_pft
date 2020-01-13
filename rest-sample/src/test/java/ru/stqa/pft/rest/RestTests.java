package ru.stqa.pft.rest;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.*;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue()
            .withSubject("Test issue")
            .withDescription("New test issue");
    int issueId = createIssue(newIssue);
    skipIfNotFixed(issueId);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue
            .withId(issueId));
    assertEquals(oldIssues, newIssues);
  }

  private Set<Issue> getIssues() throws IOException {
    String json = getExecutor()
            .execute(Request.Get("http://demo.bugify.com/api/issues.json?limit=3000"))
            .returnContent()
            .asString()
            ;
    // JsonElement parsed = new JsonParser().parse(json); //deprecated
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType()); // Set<Issue>.class - нельзя.
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("52c5391605f6ce7dc50ef9619ea2b354", "");
  }

  private int createIssue(Issue newIssue) throws IOException {
    Request request = Request.Post("http://demo.bugify.com/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),  // можно и так
                          new BasicNameValuePair("description", newIssue.getDescription()));
//            .bodyForm(Form.form()                                                      // можно и так
//                            .add("subject", newIssue.getSubject())
//                            .add("description", newIssue.getDescription())
//                            .build());

    Response response = getExecutor().execute(request);

    Content content = response.returnContent();

    String json = content.asString();

    JsonElement jsonElement = JsonParser.parseString(json);

    return jsonElement.getAsJsonObject().get("issue_id").getAsInt();
  }

  public int createIssue2(Issue newIssue) throws IOException { //версия без FluentInterface
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("subject", newIssue.getSubject()));
    params.add(new BasicNameValuePair("description", newIssue.getDescription()));
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
    Request post = Request.Post("http://demo.bugify.com/api/issues.json").body(entity);
    Response response = getExecutor().execute(post);
    String json = response.returnContent().toString();
    return JsonParser.parseString(json).getAsJsonObject().get("issue_id").getAsInt();
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    String status = getBugStatus(issueId);
    System.out.println("FYI, status of attached bug with id = " + issueId + " is " + status);
    return !status.equals("closed");
  }

  public String getBugStatus(int issueId) throws IOException {
    String linkBody = "http://demo.bugify.com/api/issues/";
    String linkIssueId = String.valueOf(issueId);
    String linkSuffix = ".json?";
    Request request = Request.Get(linkBody + linkIssueId + linkSuffix);
    Response response = getExecutor().execute(request);
    Content content = response.returnContent();
    String json = content.toString();
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issuesJson = parsed.getAsJsonObject().get("issues");
    Set<Issue> setOfIssues = new Gson().fromJson(issuesJson, new TypeToken<Set<Issue>>() {}.getType());
    Iterator<Issue> iterator = setOfIssues.iterator();
    return iterator.next().getState_name();
  }
}
