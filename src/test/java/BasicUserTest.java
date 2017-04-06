import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class BasicUserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void user_instantiatesCorrectlyAsBasicUserClass_true(){
    BasicUser testBasicUser = new BasicUser("Bloggerina", "Blogging Princess");
    assertTrue(testBasicUser instanceof BasicUser);
  }

  @Test
  public void getName_userInstantiatesWithName_true(){
    BasicUser testBasicUser = new BasicUser("Bloggerina", "Blogging Princess");
    assertEquals("Bloggerina", testBasicUser.getName());
  }

  @Test
  public void getDescription_userInstantiatesWithDescription_true(){
    BasicUser testBasicUser = new BasicUser("Bloggerina", "Blogging Princess");
    assertEquals("Blogging Princess", testBasicUser.getDescription());
  }

  @Test
  public void getId_userInstantiatesWithId_true(){
    BasicUser testBasicUser = new BasicUser("Bloggerina", "Blogging Princess");
    testBasicUser.save();
    assertTrue(0 <  testBasicUser.getId());
  }

  @Test
  public void all_allReturnsListOfAll_AllBasicUsers(){
    BasicUser userOne = new BasicUser("Bloggerina", "Blogging Princess");
    userOne.save();
    BasicUser userTwo = new BasicUser("Bloggerella", "Blogging Queen");
    userTwo.save();
    System.out.println(BasicUser.all());
    assertEquals(true, BasicUser.all().get(0).equals(userOne));
    assertEquals(true, BasicUser.all().get(1).equals(userTwo));
  }

  @Test
  public void find_returnsMerchProductWithSameId_secondMerchProduct() {
    BasicUser userOne = new BasicUser("Bloggerina", "Blogging Princess");
    userOne.save();
    BasicUser userTwo = new BasicUser("Bloggerella", "Blogging Queen");
    userTwo.save();
    assertEquals(BasicUser.find(userOne.getId()), userOne);
  }
  @Test
  public void updateDescription_changesBasicUserDescription_true(){
    BasicUser testBasicUser = new BasicUser("Bloggerina", "Blogging Princess");
    testBasicUser.save();
    testBasicUser.updateDescription("Blog Queen");
    assertEquals("Blog Queen", BasicUser.find(testBasicUser.getId()).getDescription());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void updateName_changesBasicUserName_true(){
    BasicUser testBasicUser = new BasicUser("Bloggerina", "Blogging Princess");
    testBasicUser.save();
    testBasicUser.updateName("Bloggere!");
  }

}
