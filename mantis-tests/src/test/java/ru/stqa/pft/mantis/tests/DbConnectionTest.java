package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.sql.*;

public class DbConnectionTest {

  @Test
  public void testDbConnection() throws SQLException {

    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?user=root&password=&serverTimezone=UTC");

      Users users = new Users();
      st = conn.createStatement();
      rs = st.executeQuery("select id, username, email, password from mantis_user_table");
      while (rs.next()) {
        users.add(new UserData()
                .withId(rs.getInt("id"))            //ResultSet-древний интерфейс по этому нормального цикла нет.
                .withUserName(rs.getString("username"))
                .withEmail(rs.getString("email"))
                .withPassword((rs.getString("password"))));
      }

      System.out.println(users);

    } catch (SQLException ex) {
      // handle any errors:
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }  finally {
      if (st != null) {
        st.close();
      }
      if (conn != null) {
        conn.close();
      }
      if (rs != null) {
        rs.close();
      }
    }
  }
}
