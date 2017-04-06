import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.ArrayList;
import java.text.DateFormat;


public class App {

  public static void main(String[] args) {


    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      System.out.println("i work!");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/new-user-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/welcome", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      String type = request.queryParams("type");
      if(type.equals("basic")){
        BasicUser newUser = new BasicUser(name, description);
        newUser.save();
        request.session().attribute("currentUser", newUser);
      } else {
        AdminUser newUser = new AdminUser(name, description);
        newUser.save();
        request.session().attribute("currentUser", newUser);
      }
      model.put("currentUser", request.session().attribute("currentUser"));
      model.put("posts", Post.all());
      model.put("template", "templates/user-welcome.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/welcome", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      System.out.println("users/welcome");
      model.put("users", User.getUsers());
      System.out.println("Hello");
      model.put("currentUser", request.session().attribute("currentUser"));
      model.put("posts", Post.all());
      model.put("template", "templates/user-welcome.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/posts/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("currentUser", request.session().attribute("currentUser"));

      model.put("template", "templates/post-new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/posts/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      String text = request.queryParams("text");
      int user_id = Integer.parseInt(request.queryParams("user_id"));
      Post post = new Post(title, text, user_id);
      post.save();
      model.put("currentUser", request.session().attribute("currentUser"));
      model.put("posts", Post.all());
      String url = String.format("/users/welcome");
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("posts/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Post post = Post.find(Integer.parseInt(request.params(":id")));
      model.put("post", post);
      model.put("users", User.getUsers());
      // model.put("comments", Comments.all(post.getId()));
      model.put("currentUser", request.session().attribute("currentUser"));
      model.put("template", "templates/post.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/sign-in", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/sign-in.vtl");
      model.put("users", BasicUser.all());

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/users/sign-in", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int user_id = Integer.parseInt(request.queryParams("user_id"));
      BasicUser newUser = BasicUser.find(user_id);
      request.session().attribute("currentUser", newUser);
      model.put("currentUser", request.session().attribute("currentUser"));
      model.put("posts", Post.all());
      model.put("template", "templates/user-welcome.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/posts", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/posts.vtl");
      model.put("users", User.getUsers());
      model.put("posts", Post.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
