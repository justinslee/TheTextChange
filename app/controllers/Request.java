package controllers;

import static play.data.Form.form;

import java.util.List;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class Request extends Controller{
  
  public static Result index() {
    List<models.Request> requests = models.Request.find().findList();
    return ok(requests.isEmpty() ? "No requests" : requests.toString());
  }
  
  public static Result details(String requestId) {
    models.Request request = models.Request.find().where().eq("requestId", requestId).findUnique();
    return (request == null) ? notFound("No requests") : ok(request.toString());
  }

  public static Result newRequest() {
    // Create a new Request form and bind the request variables to it..
    Form<models.Request> requestForm = form(models.Request.class).bindFromRequest();
    //validate
    if (requestForm.hasErrors()) {
      return badRequest("Request error:" + requestForm.errors().toString());
    }
    //passed validation, now can create request entity 
    models.Request request = requestForm.get();
    request.save();
    return ok(request.toString());
  }
  
  public static Result delete(String requestId) {
    models.Request request = models.Request.find().where().eq("requestId", requestId).findUnique();
    if (request != null) {
      request.delete();
    }
    return ok();
  }
}