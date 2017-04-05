import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class User {
  private int id;
  private String name;
  private String description;

  public User(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public String getDescription(){
    return description;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO users (name, description) VALUES (:name, :description)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("description", this.description)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<User> all() {
    String sql = "SELECT * FROM users";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users where id=:id";
      User user = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
      return user;
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

}
