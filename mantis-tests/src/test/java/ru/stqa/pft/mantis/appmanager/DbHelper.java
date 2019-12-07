package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class DbHelper {

  SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
     sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
  }

  public Users users(){
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery( "from UserData").list();
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }

  public Users usersWithoutAdministrator(){
    Session session = sessionFactory.openSession();
    session.getTransaction().begin();
    List<UserData> result =
            session.createQuery( "from UserData where userName != 'administrator'", UserData.class).getResultList();
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }

  public UserData getRandomUser() {
    Random random = new Random();
    Users users = usersWithoutAdministrator();
    return users.get(random.nextInt(users.size() - 1));
  }

  public UserData getUserWithMaxIdFromDB(){
    return this.users()
            .stream()
            .max(Comparator.comparingInt(UserData::getId))
            .get()
            ;
  }

  public UserData userFromDB(int id) {
    Session session = sessionFactory.openSession();
    session.getTransaction().begin();
    List<UserData> result =
            session.createQuery( "from UserData where id = '"+id+"'", UserData.class).getResultList();
    session.getTransaction().commit();
    session.close();
    return result.iterator().next();
  }
}
