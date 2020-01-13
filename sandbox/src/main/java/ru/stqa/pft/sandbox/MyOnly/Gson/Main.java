package ru.stqa.pft.sandbox.MyOnly.Gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    String json = "{\"total\":1,\"page\":1,\"limit\":1,\"issues\":[{\"assignee_id\":0,\"category_id\":0,\"created\":\"2020-01-12T19:01:53+01:00\",\"creator\":{\"id\":\"1\",\"created\":\"2017-08-18T15:00:05+02:00\",\"updated\":\"2020-01-12T14:24:48+01:00\",\"firstname\":\"Attacker\",\"lastname\":\"User\",\"name\":\"Attacker User\",\"email\":\"dionaim30@gmail.com\",\"username\":\"demo\",\"notifications\":{\"creator\":true,\"assignee\":true,\"following\":true,\"commented\":true,\"mychange\":true,\"mentioned\":true,\"allcreates\":false},\"groups\":{\"10\":true,\"11\":true,\"12\":true},\"settings\":{\"language\":\"en_US\",\"page_size\":50,\"use_editor\":\"true\",\"favourite_filters\":\"[6]\",\"scm_usernames\":\"Ludmila\",\"default_project\":\"57\",\"draft_issue\":{\"date\":\"2020-01-12T13:30:18+01:00\",\"issue\":{\"project\":\"57\",\"category\":\"246\",\"priority\":\"1\",\"assignee\":\"1\",\"milestone\":\"14\",\"subject\":\"\\u0422\\u0435\\u0441\\u0442_\\u0414\\u0417\",\"description\":\"\\u0428\\u0430\\u0433\\u0438:\\r\\n1. \\r\\n2.\\r\\n3.\\r\\n\\r\\n\\u0420\\u0435\\u0437\\u0443\\u043b\\u044c\\u0442\\u0430\\u0442:\\r\\n\\r\\n\\u041e\\u0436\\u0438\\u0434\\u0430\\u0435\\u043c\\u044b\\u0439 \\u0440\\u0435\\u0437\\u0443\\u043b\\u044c\\u0442\\u0430\\u0442:\\r\\n\"}}},\"owner\":false,\"timezone\":\"Europe\\/Brussels\",\"state\":1},\"creator_id\":1,\"description\":\"New test issue\",\"id\":5409,\"labels\":[],\"milestone_id\":0,\"percentage\":0,\"priority\":\"1\",\"priority_name\":\"Normal\",\"project_id\":0,\"related_issue_ids\":[],\"resolved\":\"1970-01-01T01:00:00+01:00\",\"state\":\"0\",\"state_name\":\"Open\",\"subject\":\"Test issue\",\"updated\":\"2020-01-12T19:01:53+01:00\"}]}";
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issuesJson = parsed.getAsJsonObject().get("issues");
    List<Issue> setOfIssues = new Gson().fromJson(issuesJson, new TypeToken<List<Issue>>() {}.getType());

    Iterator<Issue> iterator = setOfIssues.iterator();

//    System.out.println(issue.getState_name());
    System.out.println(setOfIssues.size());
    System.out.println(iterator.next().getState_name());
  }
}

//class Issue {
//  String state_name;
//  String zeAss;
//
//  public String getZeAss() {
//    return zeAss;
//  }
//
//  public void setZeAss(String zeAss) {
//    this.zeAss = zeAss;
//  }
//
//  public Issue() {
//
//  }
//
//  public String getState_name() {
//    return state_name;
//  }
//
//  public void setState_name(String state_name) {
//    this.state_name = state_name;
//  }
//
//  public Issue(String state_name) {
//    this.state_name = state_name;
//  }
//}
