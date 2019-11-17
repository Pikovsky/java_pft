package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests  extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
//    if (app.group().all().size() == 0) {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
//    Groups before = app.group().all();
    Groups before = app.db().groups();
    GroupData groupDeleted = before.iterator().next();
    app.goTo().groupPage();
    app.group().delete(groupDeleted);
    assertThat(app.group().count(), equalTo(before.size() - 1));
//    Groups  after = app.group().all();
    Groups  after = app.db().groups();
    assertThat(after, equalTo(before.without(groupDeleted)));
  }
}
