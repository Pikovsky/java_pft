package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Comparator;
import java.util.List;

public class DbHelper {
  SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
     sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
  }

  public Groups groups(){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    // Способ получения списка групп из лекции (подчёркнут красным класс GroupData, но тест работает)
    List<GroupData> result = session.createQuery( "from GroupData").list();
    // Альтернативный способ получения списка групп (подчёркнуто красным слово from).
    //List<GroupData> result = session.createQuery( "from GroupData", GroupData.class ).getResultList();
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }

  public Contacts contacts(){
    Session session = sessionFactory.openSession();
    session.getTransaction().begin(); //
   // session.beginTransaction();
    List<ContactData> result = session.createQuery( "from ContactData where deprecated = '0000-00-00'").getResultList();//.list();
            //session.createQuery("from ContactData where deprecated = '0000-00-00'", ContactData.class).getResultList();
    session.getTransaction().commit();
    session.close();
    return new Contacts(result);
  }

  public GroupData getGroupWithMaxIdFromDB2(){ // Эту спёртую  функцию я переделал.
    Groups groupList = groups();
    int maxId = 0;
    GroupData groupWithLastId = null;
    for (GroupData currentGroup: groupList){
      //System.out.println("currentGroup = " + currentGroup);
      if (currentGroup.getId() > maxId){
        // System.out.println("currentGroup.getId() = " + currentGroup.getId());
        maxId = currentGroup.getId();
        groupWithLastId = currentGroup;
      }
    }
    //System.out.println("maxId: "+ maxId);
    //System.out.println("groupWithLastId = " + groupWithLastId);
    return groupWithLastId;
  }

  public GroupData getGroupWithMaxIdFromDB(){
    return this.groups()
            .stream()
            .max(Comparator.comparingInt(GroupData::getId))
            .get()
            ;
  }

  public ContactData contactFromDB(int id) {
    Session session = sessionFactory.openSession();
    session.getTransaction().begin();
    List<ContactData> result =
            session.createQuery( "from ContactData where id = '"+id+"'", ContactData.class).getResultList();
    session.getTransaction().commit();
    session.close();
    return result.iterator().next();
  }
}
