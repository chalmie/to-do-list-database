import java.util.List;
import org.sql2o.*;

public class Category {
  private int id;
  private String description;

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Category(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object otherCategory) {
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return this.getDescription().equals(newCategory.getDescription()) && this.getId() == newCategory.getId();
    }
  }

  public static List<Category> all() {
    String sql = "SELECT id, description FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql,true).addParameter("description", description).executeUpdate().getKey();
    }
  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories WHERE id=:id";
      Category category = con.createQuery(sql).throwOnMappingFailure(false).addParameter("id", id).executeAndFetchFirst(Category.class);
      return category;
    }
  }

  public List<Task> getTasks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks WHERE categoryId=:id";
      return con.createQuery(sql).addParameter("id",id).executeAndFetch(Task.class);
    }
  }

}
