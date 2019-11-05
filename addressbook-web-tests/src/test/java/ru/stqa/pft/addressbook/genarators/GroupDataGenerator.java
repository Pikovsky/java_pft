package ru.stqa.pft.addressbook.genarators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Group count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file; // jcommander не поддерживает напрямую работу с типом File.

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator();
    JCommander jCommander = JCommander.newBuilder()
            .addObject(generator)
            .build();

    try {
      jCommander.parse(args);
    }
    catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    generator.run();
  }

  private void run() throws IOException {
    List<GroupData> groups = generateGroups(count);
    if (format.equals("csv")){
      saveAsCsv(groups, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(groups, new File(file));}
      else if (format.equals("json")) {
      saveAsJson(groups, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(groups);
    try (Writer writer = new FileWriter(file)){
      writer.write(json);
    }
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    XStream xstream = new XStream();
    //xstream.alias("group", GroupData.class);   // <group> - tag name for reflection of class GroupData in xml.
    xstream.processAnnotations(GroupData.class); // это другой способ именовать теги для хранения данных.
    String xml = xstream.toXML(groups);
    try (Writer writer = new FileWriter(file)){
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    try (Writer writer = new FileWriter(file)){
      for (GroupData group: groups) {
        writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
      }
    }

  }

  private List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData()
              .withName(String.format("test %s", i))
              .withHeader(String.format("header\n%s", i))
              .withFooter(String.format("footer\n%s", i)));
    }
    return groups;
  }
}
