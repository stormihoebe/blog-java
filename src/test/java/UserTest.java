import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class UserTest {

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

  @Test
  public void getDescription_userInstantiatesWithDescription_true(){
    User testUser = new User("Bloggerina", "Blogging Princess");
    assertEquals("Blogging Princess", testUser.getDescription());
  }

  @Test
  public void getId_userInstantiatesWithId_true(){
    User testUser = new User("Bloggerina", "Blogging Princess");
    testUser.save();
    assertTrue(0 <  testUser.getId());
  }

  @Test
  public void all_allReturnsListOfAll_AllUsers(){
    User userOne = new User("Bloggerina", "Blogging Princess");
    userOne.save();
    User userTwo = new User("Bloggerella", "Blogging Queen");
    userTwo.save();
    assertEquals(true, User.all().get(0).equals(userOne));
    assertEquals(true, User.all().get(1).equals(userTwo));
  }

  @Test
  public void find_returnsMerchProductWithSameId_secondMerchProduct() {
    User userOne = new User("Bloggerina", "Blogging Princess");
    userOne.save();
    User userTwo = new User("Bloggerella", "Blogging Queen");
    userTwo.save();
    assertEquals(User.find(userOne.getId()), userOne);
  }
  @Test
  public void updateDescription_changesUserDescription_true(){
    User testUser = new User("Bloggerina", "Blogging Princess");
    testUser.save();
    testUser.updateDescription("Blog Queen");
    assertEquals("Blog Queen", User.find(testUser.getId()).getDescription());
  }

  @Test
  public void updateName_changesUserName_true(){
    User testUser = new User("Bloggerina", "Blogging Princess");
    testUser.save();
    testUser.updateName("Bloggerella");
    assertEquals("Bloggerella", User.find(testUser.getId()).getName());
  }

}
