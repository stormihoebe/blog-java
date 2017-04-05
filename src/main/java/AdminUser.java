import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class AdminUser extends User {
  public static final String DATABASE_TYPE = "admin";

  public AdminUser(String name, String description) {
    this.name = name;
    this.description = description;
    type = DATABASE_TYPE;
  }

  public static List<AdminUser> all() {
    String sql = "SELECT * FROM users where type ='admin'";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql)
     .throwOnMappingFailure(false)
     .executeAndFetch(AdminUser.class);
    }
  }

  public static AdminUser find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users where id=:id";
      AdminUser user = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(AdminUser.class);
      return user;
    }
  }
}
