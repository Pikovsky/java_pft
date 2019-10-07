package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification(){
    Groups before = app.group().all();
    GroupData groupModified = (GroupData) before.iterator().next();
    GroupData group = new GroupData()
            .withId(groupModified.getId())
            .withName("test1")
            .withHeader("test2")
            .withFooter("test3");
    app.group().modify(group);
    Groups after = app.group().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(groupModified);
    before.add(group);
    Assert.assertEquals(before, after);
    assertThat(after.size(), equalTo(before.size()));

    assertThat(before, equalTo(before.without(groupModified).withAdded(group)));
  }
}
