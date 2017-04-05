import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class AdminUserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void user_instantiatesCorrectlyAsAdminUserClass_true(){
    AdminUser testAdminUser = new AdminUser("Bloggerina", "Blogging Princess");
    assertTrue(testAdminUser instanceof AdminUser);
  }

  @Test
  public void getName_userInstantiatesWithName_true(){
    AdminUser testAdminUser = new AdminUser("Bloggerina", "Blogging Princess");
    assertEquals("Bloggerina", testAdminUser.getName());
  }

  @Test
  public void getDescription_userInstantiatesWithDescription_true(){
    AdminUser testAdminUser = new AdminUser("Bloggerina", "Blogging Princess");
    assertEquals("Blogging Princess", testAdminUser.getDescription());
  }

  @Test
  public void getId_userInstantiatesWithId_true(){
    AdminUser testAdminUser = new AdminUser("Bloggerina", "Blogging Princess");
    testAdminUser.save();
    assertTrue(0 <  testAdminUser.getId());
  }

  @Test
  public void all_allReturnsListOfAll_AllAdminUsers(){
    AdminUser userOne = new AdminUser("Bloggerina", "Blogging Princess");
    userOne.save();
    AdminUser userTwo = new AdminUser("Bloggerella", "Blogging Queen");
    userTwo.save();
    assertEquals(true, AdminUser.all().get(0).equals(userOne));
    assertEquals(true, AdminUser.all().get(1).equals(userTwo));
  }

  @Test
  public void find_returnsMerchProductWithSameId_secondMerchProduct() {
    AdminUser userOne = new AdminUser("Bloggerina", "Blogging Princess");
    userOne.save();
    AdminUser userTwo = new AdminUser("Bloggerella", "Blogging Queen");
    userTwo.save();
    assertEquals(AdminUser.find(userOne.getId()), userOne);
  }
  @Test
  public void updateDescription_changesAdminUserDescription_true(){
    AdminUser testAdminUser = new AdminUser("Bloggerina", "Blogging Princess");
    testAdminUser.save();
    testAdminUser.updateDescription("Blog Queen");
    assertEquals("Blog Queen", AdminUser.find(testAdminUser.getId()).getDescription());
  }

  @Test
  public void updateName_changesAdminUserName_true(){
    AdminUser testAdminUser = new AdminUser("Bloggerina", "Blogging Princess");
    testAdminUser.save();
    testAdminUser.updateName("Bloggere!");
    assertEquals("Bloggere!", AdminUser.find(testAdminUser.getId()).getName());
  }

}
