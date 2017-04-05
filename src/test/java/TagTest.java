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
  public void tag_instantiatesCorrectly_true(){
    Tag testTag = new Tag("DIY");
    assertTrue(testTag instanceof Tag);
  }

  @Test
  public void getName_instantiatesCorrectly_true(){
    Tag testTag = new Tag("DIY");
    assertEquals("DIY", testTag.getName());
  }

  @Test
  public void getId_instantiatesCorrectly_true(){
    Tag testTag = new Tag("DIY");
    testTag.save();
    assertTrue(0 < testTag.getId());
  }

  @Test
  public void save_savesToTheDatabase_true() {
    Tag testTag = new Tag("DIY");
    testTag.save();
    assertTrue(Tag.all().get(0).equals(testTag));
  }

  @Test
  public void all_returnsAllInstancesOfTag_true() {
    Tag testTagOne = new Tag("DIY");
    testTagOne.save();
    Tag testTagTwo = new Tag("DIY");
    testTagTwo.save();
    assertEquals(true, Tag.all().get(0).equals(testTagOne));
    assertEquals(true, Tag.all().get(1).equals(testTagTwo));
  }

  @Test
  public void find_returnsTagWithSameId_secondTag() {
    Tag firstTag = new Tag("DIY");
    firstTag.save();
    Tag secondTag = new Tag("Dogs");
    secondTag.save();
    assertEquals(Tag.find(secondTag.getId()), secondTag);
  }

  @Test
  public void updateName_changesTagName_true(){
    Tag firstTag = new Tag("DIY");
    firstTag.save();
    firstTag.updateName("HOME IMPROVEMENT");
    assertEquals("HOME IMPROVEMENT", Tag.find(firstTag.getId()).getName());
  }

  @Test
  public void delete_deletesTag_true(){
    Tag firstTag = new Tag("DIY");
    firstTag.save();
    firstTag.delete();
    assertFalse(Tag.all().contains(firstTag));
    }

}
