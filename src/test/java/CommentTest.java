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

public class CommentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Comment_instantiatesCorrectly_true(){
    Comment testComment = new Comment(1, 1, "this is my cool comment!");
    assertTrue(testComment instanceof Comment);
  }

  @Test
  public void getUserId_instantiatesCorrectly_true(){
    Comment testComment = new Comment(1, 1, "this is my cool comment!");
    assertEquals(1, testComment.getUserId());
  }

  @Test
  public void getPostId_instantiatesCorrectly_true(){
    Comment testComment = new Comment(1, 1, "this is my cool comment!");
    assertEquals(1, testComment.getPostId());
  }

  @Test
  public void getText_instantiatesCorrectly_true(){
    Comment testComment = new Comment(1, 1, "this is my cool comment!");
    assertEquals("this is my cool comment!", testComment.getText());
  }

  @Test
  public void save_savesToTheDatabase_true() {
    Comment testComment = new Comment(1, 1, "this is my cool comment!");
    testComment.save();
    assertTrue(Comment.all(1).get(0).equals(testComment));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void save_throwsExceptionIfcharactsAreMoreThanMax(){
    Comment testComment = new Comment(1, 1, "this is my cool comment!this is my cool comment!cool comment!");
    testComment.save();
  }

  @Test
  public void all_returnsAllInstancesOfComment_true() {
    Comment firstComment = new Comment(1, 1, "this is my cool comment!");
    firstComment.save();
    Comment secondComment = new Comment(1, 1, "this is my cool comment!");
    secondComment.save();
    assertEquals(true, Comment.all(1).get(0).equals(firstComment));
    assertEquals(true, Comment.all(1).get(1).equals(secondComment));
  }


    @Test
    public void find_returnsCommentWithSameId_secondComment() {
      Comment firstComment = new Comment(1, 1, "this is my cool comment!");
      firstComment.save();
      Comment secondComment = new Comment(1, 1, "this is my cool comment!");
      secondComment.save();
      assertEquals(Comment.find(secondComment.getId()), secondComment);
    }

    @Test
    public void delete_deletesComment_true(){
      Comment firstComment = new Comment(1, 1, "this is my cool comment!");
      firstComment.save();
      firstComment.delete();
      assertFalse(Comment.all(1).contains(firstComment));
      }


}
