package ru.stqa.pft.addressbook.genarators;

import ru.stqa.pft.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {
  public static void main(String[] args) throws IOException {
    int count = Integer.parseInt(args[0]);
    File file = new File(args[1]);

    List<GroupData> groups = generateGroups(count);
    save(groups, file);
  }

  private static void save(List<GroupData> groups, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (GroupData group: groups) {
      writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
    }
    writer.close(); // В момент закрытия все закэшированые данные сбрасываются на диск.
  }

  private static List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData()
              .withName(String.format("test %s", i))
              .withHeader(String.format("header %s", i))
              .withFooter(String.format("footer %s", i)));

    }
    return groups;
  }
}
