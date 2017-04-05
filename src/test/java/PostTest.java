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

  @Test
  public void getId_userInstantiatesWithId_true(){
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    testPost.save();
    assertTrue(0 <  testPost.getId());
  }

  @Test
  public void save_savesToTheDatabase_true() {
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    testPost.save();
    assertTrue(Post.all().get(0).equals(testPost));
  }

  @Test
  public void all_returnsAllInstancesOfPost_true() {
    Post firstPost = new Post("Summer Fun", "Summer is great!", 1);
    firstPost.save();
    Post secondPost = new Post("Summer Fun", "Summer is great!", 1);
    secondPost.save();
    assertEquals(true, Post.all().get(0).equals(firstPost));
    assertEquals(true, Post.all().get(1).equals(secondPost));
  }

  @Test
  public void find_returnsPostWithSameId_secondPost() {
    Post firstPost = new Post("Summer Fun", "Summer is great!", 1);
    firstPost.save();
    Post secondPost = new Post("Summer Fun", "Summer is great!", 1);
    secondPost.save();
    assertEquals(Post.find(secondPost.getId()), secondPost);
  }

  @Test
  public void updateTitle_changesPostTitle_true(){
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    testPost.save();
    testPost.updateTitle("Summer blast!");
    assertEquals("Summer blast!", Post.find(testPost.getId()).getTitle());
  }

  @Test
  public void updateText_changesPostText_true(){
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    testPost.save();
    testPost.updateText("Summer blast!");
    assertEquals("Summer blast!", Post.find(testPost.getId()).getText());
  }

  @Test
  public void save_recordsTimeOfPostInDatabase() {
    Post testPost = new Post("Summer Fun", "Summer is great!", 1);
    testPost.save();
    Timestamp savedTime = Post.find(testPost.getId()).getPostDate();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedTime.getDay());
  }

}
