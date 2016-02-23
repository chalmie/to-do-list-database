import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private int categoryId;
  private String description;
  private String dueDate;

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public String getDueDate() {
    return dueDate;
  }

  public Task(String description, int categoryId, String dueDate) {
    this.description = description;
    this.categoryId = categoryId;
    this.dueDate = dueDate;
  }

  @Override
  public boolean equals(Object otherTask) {
    if(!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.gegetDescription()) && this.getId() == newTask.getId() && this.getCategoryId() == newTask.getCategoryId() && this.getDueDate().equals(newTask.getDueDate());
    }
  }

  public static List<Task> all() {
    String sql = "SELECT id, description, categoryid, duedate FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description, categoryId, dueDate) VALUES (:description, :categoryId, :dueDate)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .addParameter("categoryId", categoryId)
        .addParameter("dueDate", dueDate)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM tasks WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static List<Task> allSorted() {
    String sql = "SELECT id, description, categoryId, dueDate FROM tasks ORDER BY dueDate ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }
}
