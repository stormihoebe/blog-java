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

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void user_instantiatesCorrectlyAsUserClass_true(){
    User testUser = new User("Bloggerina", "Blogging Princess");
    assertTrue(testUser instanceof User);
  }

  @Test
  public void getName_userInstantiatesWithName_true(){
    User testUser = new User("Bloggerina", "Blogging Princess");
    assertEquals("Bloggerina", testUser.getName());
  }
}
