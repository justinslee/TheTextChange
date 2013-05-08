package controllers;

import java.util.List;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
  public static Result index() { 
    List<models.Student> students = models.Student.find().all();
    return ok(index.render(students));
  }
  
  public static Result admin() {
    List<models.Offer> offers = models.Offer.find().all();
    List<models.Student> students = models.Student.find().all();
    List<models.Book> books = models.Book.find().all();
    List<models.Request> requests = models.Request.find().all();   
      
    return ok(admin.render(offers, students, books, requests));
  }
}
