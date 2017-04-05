import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Tag {
  private int id;
  private String name;

  public Tag(String name) {
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Tag> all() {
    String sql = "SELECT * FROM tags";
    try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  public void addPost(Post post) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO tags_posts (tag_id, post_id) VALUES (:tag_id, :post_id)";
       con.createQuery(sql)
       .addParameter("tag_id", this.getId())
       .addParameter("post_id", post.getId())
       .executeUpdate();
     }
   }

   public List<Post> getPosts() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT post_id FROM tags_posts WHERE tag_id = :tag_id";
      List<Integer> postIds = con.createQuery(joinQuery)
        .addParameter("tag_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Post> posts = new ArrayList<Post>();

      for (Integer postId : postIds) {
        String postQuery = "SELECT * FROM posts WHERE id = :postId";
        Post post = con.createQuery(postQuery)
          .addParameter("postId", postId)
          .executeAndFetchFirst(Post.class);
        posts.add(post);
      }
      return posts;
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags where id=:id";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  @Override
   public boolean equals(Object otherTag){
     if (!(otherTag instanceof Tag)) {
       return false;
     } else {
     Tag newTag = (Tag) otherTag;
     return this.getName().equals(newTag.getName());
    }
  }

  public void delete() {
   try(Connection con = DB.sql2o.open()) {
   String sql = "DELETE FROM tags WHERE id = :id;";
   con.createQuery(sql)
     .addParameter("id", this.id)
     .executeUpdate();
   String joinDeleteQuery = "DELETE FROM tags_posts WHERE tag_id = :tagId";
   con.createQuery(joinDeleteQuery)
     .addParameter("tagId", this.getId())
     .executeUpdate();
   }
 }

 public void removePost(Post post){
  try(Connection con = DB.sql2o.open()){
    String joinRemovalQuery = "DELETE FROM tags_posts WHERE tag_id = :tagId AND post_id = :postId;";
    con.createQuery(joinRemovalQuery)
      .addParameter("tagId", this.getId())
      .addParameter("postId", post.getId())
      .executeUpdate();
  }
}

public void updateName(String name) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE tags SET name = :name WHERE id=:id";
    con.createQuery(sql)
    .addParameter("name", name)
    .addParameter("id", this.id)
    .executeUpdate();
  }
}


}
