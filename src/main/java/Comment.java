import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Comment {
  private int id;
  private int user_id;
  private int post_id;
  private Timestamp date;
  private String text;


  public static final int MAX_CHAR_COUNT = 50;


  public Comment( int user_id, int post_id, String text) {
    this.user_id = user_id;
    this.post_id = post_id;
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public int getUserId() {
    return user_id;
  }

  public int getPostId() {
    return post_id;
}

  public Timestamp getCommentDate() {
    return date;
  }

  public String getText() {
    return text;
  }

  public void save() {
    if (text.length() > MAX_CHAR_COUNT) {
      throw new UnsupportedOperationException("You cannot type more than " + MAX_CHAR_COUNT +" characters!");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO comments (user_id, post_id, text, date) VALUES (:user_id, :post_id, :text, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("user_id", this.user_id)
        .addParameter("text", this.text)
        .addParameter("post_id", this.post_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Comment> all(int post_id) {
    String sql = "SELECT * FROM comments WHERE post_id = :post_id;";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
      .addParameter("post_id", post_id)
      .executeAndFetch(Comment.class);
    }
  }

  public static Comment find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM comments where id=:id";
      Comment comment = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Comment.class);
      return comment;
    }
  }
  public void delete() {
   try(Connection con = DB.sql2o.open()) {
   String sql = "DELETE FROM comments WHERE id = :id;";
   con.createQuery(sql)
     .addParameter("id", this.id)
     .executeUpdate();
   }
  }

  @Override
   public boolean equals(Object otherComment){
     if (!(otherComment instanceof Comment)) {
       return false;
     } else {
     Comment newComment = (Comment) otherComment;
     return (this.getUserId() == newComment.getUserId());
    }
   }

}
