import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;

import models.Book;
import models.Offer;
import models.Request;
import models.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.stop;

public class ModelTest {
  private FakeApplication application;
  
  @Before 
  public void startApp() {
    application = fakeApplication(inMemoryDatabase());
    start(application);
  }

  @After
  public void stopApp() {
    stop(application);
  }
  
  @Test
  public void testModel() {
    
    //Create 1 of each Model
    Book book = new Book("Book-01", "book", "edition", "1234567890123", "200");
    Student student = new Student("Student-01", "student", "email");
    Offer offer = new Offer("Offer-01", "new", "100", "book", "edition");
    Request request = new Request("Request-01", "old", "50", "book", "edition");
    
    //Associate Models
    book.getRequests().add(request);
    request.setBook(book);
    request.setStudent(student);
    
    book.getOffers().add(offer);
    offer.setBook(book);
    offer.setStudent(student);
    
    //Persist the sample model by saving all entities and relationships
    student.save();
    book.save();
    offer.save();
    request.save();
    
    //Retrieve the entire model from the database.
    
    List<Student> students = Student.find().findList();
    List<Book> books = Book.find().findList();
    List<Request> requests = Request.find().findList();
    List<Offer> offers = Offer.find().findList();
    
    //Check that we've recovered all our entities.
    assertEquals("Checking student", students.size(), 1);
    assertEquals("Checking book", books.size(), 1);
    assertEquals("Checking offer", offers.size(), 1);
    assertEquals("Checking request", requests.size(), 1);
    
    //Check that we've recovered all relationships.
    assertEquals("Student-Request", students.get(0).getRequests().get(0), requests.get(0));
    assertEquals("Request-Student", requests.get(0).getStudent(), students.get(0));
    assertEquals("Book-Request", books.get(0).getRequests().get(0), requests.get(0));
    assertEquals("Request-Book", requests.get(0).getBook(), books.get(0));
    assertEquals("Student-Offer", students.get(0).getOffers().get(0), offers.get(0));
    assertEquals("Offer-Student", offers.get(0).getStudent(), students.get(0));
    assertEquals("Book-Offer", books.get(0).getOffers().get(0), offers.get(0));
    assertEquals("Offer-Book", offers.get(0).getBook(), books.get(0));  

    // Testing for model manipulation and cascading
    // Show that all entries exist in database
    assertTrue("Requests in database", !Request.find().findList().isEmpty());    
    assertTrue("Offers in database", !Offer.find().findList().isEmpty());
    assertTrue("Students in database", !Student.find().findList().isEmpty());
    assertTrue("Books in database", !Book.find().findList().isEmpty());

    // Show that offer exists in database
    assertTrue("Book has no offer", !Book.find().findList().get(0).getOffers().isEmpty());
    assertTrue("Student has no offer", !Student.find().findList().get(0).getOffers().isEmpty());
    
    // Delete all offers
    List<Offer> offerClear = Offer.find().findList();
    for (Offer o: offerClear) {
      o.delete();
    }
  
    // Show that offers 
    assertTrue("Previously retrieved book still has offer", !books.get(0).getOffers().isEmpty());
    assertTrue("Book has no offers", Book.find().findList().get(0).getOffers().isEmpty());
    assertTrue("Offer has no book", Offer.find().findList().isEmpty());

 
    // Show that offer does not exist in database
    assertTrue("Student has an offer", Student.find().findList().get(0).getOffers().isEmpty());
    assertTrue("No more offers in database", Offer.find().findList().isEmpty());
  
    }
  
}
