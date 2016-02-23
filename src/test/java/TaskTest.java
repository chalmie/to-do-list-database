import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;

public class TaskTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn", 1, "2016-02-25");
    Task secondTask = new Task("Mow the lawn", 1, "2016-02-25");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Task myTask = new Task("Mow the lawn", 1, "2016-02-25");
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("Mow the lawn", 1, "2016-02-25");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn", 1, "2016-02-25");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task myTask = new Task("Mow the lawn", myCategory.getId(), "2016-02-25");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }

  @Test
  public void allSorted_returnsAllTasksSoonestFirst() {
    Task firstTask = new Task("Mow the lawn", 1, "2016-02-25");
    firstTask.save();
    Task secondTask = new Task("Wash dishes", 1, "2016-02-23");
    secondTask.save();
    assertTrue(Task.allSorted().get(0).equals(secondTask));
    assertTrue(Task.allSorted().get(1).equals(firstTask));
  }
}
