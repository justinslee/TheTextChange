package controllers;

import static play.data.Form.form;

import java.util.List;
import play.data.Form;
import views.html.bookCreate;
import views.html.bookEdit;
import play.mvc.Controller;
import play.mvc.Result;

public class Book extends Controller{
  /**
   * Displays a form with default values for creating a new book.
   * @return A book form with default values. 
   */
  public static Result create() {
    models.Book defaults = new models.Book("Book1", "My Book", "1", "1234567890123", "$60");
    Form<models.Book> bookForm = form(models.Book.class).fill(defaults);
    return ok(bookCreate.render(bookForm));
  }
  
  /**
   * Stores a newly created book defined by user.
   * @return The home page. 
   */
  public static Result save() {
    Form<models.Book> bookForm = form(models.Book.class).bindFromRequest();
    if (bookForm.hasErrors()) {
      return badRequest(bookCreate.render(bookForm));
    }
    models.Book book = bookForm.get();
    book.save();
    return redirect(routes.Application.admin());
  }
  
  /**
   * Displays a book's data for updating.
   * @param primaryKey The PK used to retrieve the book. 
   * @return An filled book form.
   */
  public static Result edit(Long primaryKey) {
    models.Book book = models.Book.find().byId(primaryKey);
    Form<models.Book> bookForm = form(models.Book.class).fill(book);
    return ok(bookEdit.render(primaryKey, bookForm));
  }
  
 
  /**
   * Saves an updated version of the book data provided by user. 
   * @param primaryKey The PK to the book.
   * @return The home page. 
   */
  public static Result update(Long primaryKey) {
    Form<models.Book> bookForm = form(models.Book.class).bindFromRequest();
    if (bookForm.hasErrors()) {
      return badRequest(bookEdit.render(primaryKey, bookForm));
    }
    bookForm.get().update(primaryKey);
    return redirect(routes.Application.admin());
  }
  
  /**
   * Deletes the book. 
   * @param primaryKey The PK to the book to be deleted.
   * @return The home page. 
   */
  public static Result delete(Long primaryKey) {
    models.Book.find().byId(primaryKey).delete();
    return redirect(routes.Application.index());
  }
  
}
