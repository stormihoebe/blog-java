import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class PostTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void post_instantiatesCorrectly_true(){
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    assertTrue(testPost instanceof Post);
  }

  @Test
  public void getTitle_instantiatesCorrectly_true(){
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    assertEquals("Summer Fun", testPost.getTitle());
  }

  @Test
  public void getText_instantiatesCorrectly_true(){
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    assertEquals("Summer is great!", testPost.getText());
  }

  @Test
  public void getuserId_instantiatesCorrectly_true(){
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    assertEquals(1, testPost.getUserId());
  }



}
