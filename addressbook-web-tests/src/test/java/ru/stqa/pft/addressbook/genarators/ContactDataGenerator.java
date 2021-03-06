package ru.stqa.pft.addressbook.genarators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file; // jcommander не поддерживает напрямую работу с типом File.

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
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
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")){
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));}
    else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)){
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    //xstream.alias("contact", ContactData.class);  // <contact> - tag name for reflection of class ContactData in xml.
    xstream.processAnnotations(ContactData.class);  // это другой способ именовать теги для хранения данных.
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)){
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    try (Writer writer = new FileWriter(file)){
      for (ContactData contact: contacts) {
        writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                contact.getFirstname(),
                contact.getLastname(),
                contact.getHomePhone(),
                contact.getWorkPhone(),
                contact.getMobilePhone(),
                contact.getAddress(),
                contact.getEmail(),
                contact.getEmail2(),
                contact.getEmail3(),
                // contact.getGroup(), // временно комментируем эту строку из-за того что мы удалили атрибут group.
                contact.getPhoto().getPath()
                ));
      }
    }
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withFirstname(String.format("firstname %s", i))
              .withLastname(String.format("lastname %s", i))
              .withHomePhone(String.format("11%s", i))
              .withWorkPhone(String.format("22%s", i))
              .withMobilePhone(String.format("33%s", i))
              .withAddress(String.format("address %s", i))
              .withEmail(String.format("a%s@gmail.com", i))
              .withEmail2(String.format("b%s@gmail.com", i))
              .withEmail3(String.format("c%s@gmail.com", i))
              // .withGroup("group1") // временно комментируем эту строку из-за того что мы удалили атрибут group.
              .withPhoto(new File("src/test/resources/stru.png"))
      );
    }
    return contacts;
  }
}
