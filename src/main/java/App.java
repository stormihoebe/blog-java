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
      model.put("users", User.getUsers());
      model.put("currentUser", request.session().attribute("currentUser"));

      model.put("template", "templates/new-user-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/users/welcome", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      System.out.println("users/welcome");
      model.put("users", User.getUsers());
      System.out.println("Hello");
      model.put("currentUser", request.session().attribute("currentUser"));
      model.put("template", "templates/new-user-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }
}
