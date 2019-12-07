package ru.stqa.pft.mantis.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;

import java.util.List;

public class HbConnectionTest {

  private SessionFactory sessionFactory;

  @BeforeClass
  protected void setUp() throws Exception {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    try {
      sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }
    catch (Exception e) {
      e.printStackTrace();
      // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
      // so destroy it manually.
      StandardServiceRegistryBuilder.destroy( registry );
    }
  }

  @Test
  public void testHbConnectionUser(){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    // Способ получения списка групп из лекции (подчёркнут красным класс GroupData, но тест работает)
    List<UserData> result = session.createQuery( "from ru.stqa.pft.mantis.model.UserData").list();
    // При указании полного пути класса красных подчёркиваний нет
//    List<GroupData> result = session.createQuery( "from ru.stqa.pft.addressbook.model.GroupData").list();
    // Альтернативный способ получения списка групп (подчёркнуто красным слово from).
//    List<GroupData> result = session.createQuery( "from GroupData", GroupData.class ).getResultList();
    for (UserData user : result ) {
      System.out.println(user);
    }
    session.getTransaction().commit();
    session.close();
  }

  @Test(enabled = false)
  public void testHbConnection(){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery( "from UserData").list();

    session.getTransaction().commit();
    session.close();

    for (UserData user : result ) {
      System.out.println(user.getUserName());
      System.out.println(user.getEmail());
      System.out.println(user.getPassword());
    }
  }
}
