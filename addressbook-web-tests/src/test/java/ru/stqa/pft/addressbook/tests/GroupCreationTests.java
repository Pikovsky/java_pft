package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Set;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("test2");
    app.group().create(group);
    Set<GroupData>  after = app.group().all();
    Assert.assertEquals(after.size(), before.size() + 1);
//    group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId() )).get().getId());
//    group.setId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());
    group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(group);
    Assert.assertEquals(before, after);
  }
}
