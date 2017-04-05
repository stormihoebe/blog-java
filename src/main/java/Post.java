import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Post {
  private String title;
  private String text;
  private int user_id;
  private Timestamp date;
  private int id;

  public Post(String title, String text, int user_id) {
    this.title = title;
    this.text = text;
    this.user_id = user_id;

  }

  public String getTitle() {
    return title;
  }

  public String getText(){
    return text;
  }

  public int getUserId(){
    return user_id;
  }

  public int getId() {
    return id;
  }

  public Timestamp getPostDate() {
    return date;
  }

  public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO posts (title, text, user_id, date) VALUES (:title, :text, :user_id, now())";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("title", this.title)
          .addParameter("text", this.text)
          .addParameter("user_id", this.user_id)
          .executeUpdate()
          .getKey();
      }
    }

    public static List<Post> all() {
      String sql = "SELECT * FROM posts";
      try(Connection con = DB.sql2o.open()){
        return con.createQuery(sql).executeAndFetch(Post.class);
      }
    }

    public static Post find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM posts where id=:id";
        Post post = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Post.class);
        return post;
      }
    }

    public void updateTitle(String title) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE posts SET title = :title WHERE id=:id";
        con.createQuery(sql)
        .addParameter("title", title)
        .addParameter("id", id)
        .executeUpdate();
      }
    }

    public void updateText(String text) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE posts SET text = :text WHERE id=:id";
        con.createQuery(sql)
        .addParameter("text", text)
        .addParameter("id", this.id)
        .executeUpdate();
      }
    }

  @Override
   public boolean equals(Object otherPost){
     if (!(otherPost instanceof Post)) {
       return false;
     } else {
     Post newPost = (Post) otherPost;
     return (this.getUserId() == newPost.getUserId());
    }
   }

}
