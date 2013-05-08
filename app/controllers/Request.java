package controllers;


import static play.data.Form.form;

import java.util.List;
import views.html.requestCreate;
import views.html.requestEdit;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Request extends Controller{
  
  /**
   * Displays a form with default values for creating a new product.
   * @return A product form with default values. 
   */
  public static Result create() {
    models.Request defaults = new models.Request("Request1", "New", "$30", "ICS Book", "1");
    Form<models.Request> requestForm = form(models.Request.class).fill(defaults);
    return ok(requestCreate.render(requestForm));
  }
  
  /**
   * Stores a newly created request defined by user.
   * @return The home page. 
   */
  public static Result save() {
    Form<models.Request> requestForm = form(models.Request.class).bindFromRequest();
    if (requestForm.hasErrors()) {
      return badRequest(requestCreate.render(requestForm));
    }
    models.Request request = requestForm.get();
    request.save();
    return redirect(routes.Application.index());
  }

  /**
   * Displays a request's data for updating.
   * @param primaryKey The PK used to retrieve the request. 
   * @return An filled request form.
   */
  public static Result edit(Long primaryKey) {
    models.Request request = models.Request.find().byId(primaryKey);
    Form<models.Request> requestForm = form(models.Request.class).fill(request);
    return ok(requestEdit.render(primaryKey, requestForm));
  }
  /**
   * Saves an updated version of the stockItem data provided by user. 
   * @param primaryKey The PK to the stockItem.
   * @return The home page. 
   */
  public static Result update(Long primaryKey) {
    Form<models.Request> requestForm = form(models.Request.class).bindFromRequest();
    if (requestForm.hasErrors()) {
      return badRequest(requestEdit.render(primaryKey, requestForm));
    }
    requestForm.get().update(primaryKey);
    return redirect(routes.Application.index());
  }
  
  /**
   * Deletes the request. 
   * @param primaryKey The PK to the request to be deleted.
   * @return The home page. 
   */
  public static Result delete(Long primaryKey) {
    models.Request.find().byId(primaryKey).delete();
    return redirect(routes.Application.index());
  }
}