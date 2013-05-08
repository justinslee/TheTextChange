package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.studentCreate;
import views.html.studentEdit;
import static play.data.Form.form;
import java.util.List;

public class Student extends Controller {
  
  /**
   * Displays a form with default values for creating a new student.
   * @return A student form with default values. 
   */
  public static Result create() {
    models.Student defaults = new models.Student("Student1", "John Doe", "johndoe@hawaii.edu");
    Form<models.Student> studentForm = form(models.Student.class).fill(defaults);
    return ok(studentCreate.render(studentForm));
  }
  
  /**
   * Stores a newly created student.
   * @return The home page. 
   */
  public static Result save() {
    Form<models.Student> studentForm = form(models.Student.class).bindFromRequest();
    if (studentForm.hasErrors()) {
      return badRequest(studentCreate.render(studentForm));
    }
    models.Student student = studentForm.get();
    student.save();
    return redirect(routes.Application.admin());
  }
  
  /**
   * Displays a student's data for updating.
   * @param primaryKey The PK used to retrieve the student. 
   * @return An filled student form.
   */
  public static Result edit(Long primaryKey) {
    models.Student student = models.Student.find().byId(primaryKey);
    Form<models.Student> studentForm = form(models.Student.class).fill(student);
    return ok(studentEdit.render(primaryKey, studentForm));
  }
  
 
  /**
   * Saves an updated version of the student data provided by user. 
   * @param primaryKey The PK to the student.
   * @return The home page. 
   */
  public static Result update(Long primaryKey) {
    Form<models.Student> studentForm = form(models.Student.class).bindFromRequest();
    if (studentForm.hasErrors()) {
      return badRequest(studentEdit.render(primaryKey, studentForm));
    }
    studentForm.get().update(primaryKey);
    return redirect(routes.Application.admin());
  }
  
  /**
   * Deletes the student. 
   * @param primaryKey The PK to the student to be deleted.
   * @return The home page. 
   */
  public static Result delete(Long primaryKey) {
    models.Student.find().byId(primaryKey).delete();
    return redirect(routes.Application.index());
  }
  
}