package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() == 0) {  // проверяем наличие групп через БД.
      app.goTo().groupPage(); // мы в предусловиях переместили эту команду в блок if
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupModification(){
    Groups before = app.db().groups();  // получаем группы через БД.
    GroupData groupModified = before.iterator().next();
    GroupData group = new GroupData()
            .withId(groupModified.getId())
            .withName("test1")
            .withHeader("test2")
            .withFooter("test3");
    app.goTo().groupPage(); // эту команду теперь надо вызывать перед модификацией групп.
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();   // получаем группы через БД.
    assertThat(after, equalTo(before.without(groupModified).withAdded(group)));
    verifyGroupListInUa();
  }
}
