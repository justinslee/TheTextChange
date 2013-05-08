package controllers;


import static play.data.Form.form;

import java.util.List;
import views.html.offerCreate;
import views.html.offerEdit;
import views.html.search;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Offer extends Controller{
  
  /**
   * Displays a form with default values for creating a new product.
   * @return A product form with default values. 
   */
  public static Result create() {
    models.Offer defaults = new models.Offer("Offer1", "New", "$30", "ICS Book", "1");
    Form<models.Offer> offerForm = form(models.Offer.class).fill(defaults);
    return ok(offerCreate.render(offerForm));
  }
  
  /**
   * Stores a newly created offer defined by user.
   * @return The home page. 
   */
  public static Result save() {
    Form<models.Offer> offerForm = form(models.Offer.class).bindFromRequest();
    if (offerForm.hasErrors()) {
      return badRequest(offerCreate.render(offerForm));
    }
    models.Offer offer = offerForm.get();
    offer.save();
    return redirect(routes.Application.index());
  }

  /**
   * Displays a offer's data for updating.
   * @param primaryKey The PK used to retrieve the offer. 
   * @return An filled offer form.
   */
  public static Result edit(Long primaryKey) {
    models.Offer offer = models.Offer.find().byId(primaryKey);
    Form<models.Offer> offerForm = form(models.Offer.class).fill(offer);
    return ok(offerEdit.render(primaryKey, offerForm));
  }
  /**
   * Saves an updated version of the stockItem data provided by user. 
   * @param primaryKey The PK to the stockItem.
   * @return The home page. 
   */
  public static Result update(Long primaryKey) {
    Form<models.Offer> offerForm = form(models.Offer.class).bindFromRequest();
    if (offerForm.hasErrors()) {
      return badRequest(offerEdit.render(primaryKey, offerForm));
    }
    offerForm.get().update(primaryKey);
    return redirect(routes.Application.index());
  }
  
  /**
   * Deletes the offer. 
   * @param primaryKey The PK to the offer to be deleted.
   * @return The home page. 
   */
  public static Result delete(Long primaryKey) {
    models.Offer.find().byId(primaryKey).delete();
    return redirect(routes.Application.index());
  }
  /**
   * Finds offers of a specific book needs to be replaced with a better method
   * @param bookName
   * @return
   */
  public static Result find(String bookName) {
    
    models.Book b= models.Book.find().where().eq("name","My Book").findUnique();
    List<models.Offer> offers =  models.Offer.find().all();
    List<models.Offer> results = null;
    for (models.Offer o: offers) {
      if(o.getBook().equals(b)) {
        results.add(o);
      }
    }
    return ok(search.render(offers));
  }
  
}