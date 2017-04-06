import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public abstract class User {
  public int id;
  public String name;
  public String description;
  public String type;



  public int getId(){
    return id;
  }
  public String getType(){
    return type;
  }

  public String getName(){
    return name;
  }

  public String getDescription(){
    return description;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO users (name, description, type) VALUES (:name, :description, :type)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("description", this.description)
      .addParameter("type", this.type)
      .executeUpdate()
      .getKey();
    }
  }

  public void updateDescription(String description){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET description = :description WHERE id=:id";
      con.createQuery(sql)
      .addParameter("description", description)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void updateName(String name){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET name = :name WHERE id=:id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }


  @Override
  public boolean equals(Object otherUser){
    if(!(otherUser instanceof User)){
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getName().equals(newUser.getName()) &&
             this.getDescription().equals(newUser.getDescription()) && this.getId() == newUser.getId();
    }
  }

  public static List<User> getUsers() {
    List<User> allUsers = new ArrayList<User>();

    try(Connection con = DB.sql2o.open()) {
      String sqlAdmin = "SELECT * FROM users WHERE type = 'admin';";
      List<AdminUser> adminUsers = con.createQuery(sqlAdmin)
      .throwOnMappingFailure(false)
      .executeAndFetch(AdminUser.class);
      allUsers.addAll(adminUsers);

      String sqlBasicUser = "SELECT * FROM users WHERE type = 'basic';";
      List<BasicUser> basicUsers = con.createQuery(sqlBasicUser)
      .throwOnMappingFailure(false)
      .executeAndFetch(BasicUser.class);
      allUsers.addAll(basicUsers);
    }
    return allUsers;
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users where id=:id";
      User user = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(User.class);
      return user;
    }
  }
}
