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
}
