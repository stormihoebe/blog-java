import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BasicUser extends User {
  public static final String DATABASE_TYPE = "basic";

  public BasicUser(String name, String description) {
    this.name = name;
    this.description = description;
    type = DATABASE_TYPE;
  }

  public static List<BasicUser> all() {
    String sql = "SELECT * FROM users where type ='basic'";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql)
     .throwOnMappingFailure(false)
     .executeAndFetch(BasicUser.class);
    }
  }

  public static BasicUser find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users where id=:id";
      BasicUser user = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(BasicUser.class);
      return user;
    }
  }

  @Override
  public void updateName(String name){
    throw new UnsupportedOperationException("You must request a name change through a site admin.");
  }
}
