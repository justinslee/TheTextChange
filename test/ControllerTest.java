import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import static play.test.Helpers.stop;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Book;
import models.Offer;
import models.Request;
import models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;

public class ControllerTest {
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
  // Controller routes and functions no longer exist.  Testing functions is now done in views and pages.
  /*
  @Test
  public void testStudentController() {
    // Test GET /student on an empty database.
    Result result = callAction(controllers.routes.ref.Student.index());
    assertTrue("Empty students", contentAsString(result).contains("No students"));
    
    // Test GET /student on a database containing a single student.
    String studentId = "Student-01";
    Student student = new Student(studentId, "John Doe", "johndoe@hawaii.edu");
    student.save();
    result = callAction(controllers.routes.ref.Student.index());
    assertTrue("One student", contentAsString(result).contains(studentId));
    
    // Test GET /student/Student-01
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertTrue("Student detail", contentAsString(result).contains(studentId));
    
    // Test GET /student/BadStudentId and make sure we get a 404
    result = callAction(controllers.routes.ref.Student.details("BadStudentId"));
    assertEquals("Student detail (bad)", NOT_FOUND, status(result));
        
    // Test POST /students (with simulated, valid form data).
    Map<String, String> studentData = new HashMap<String, String>();
    studentData.put("studentId", "Student-02");
    studentData.put("name", "Jane Doe");
    studentData.put("email", "janedoe@hawaii.edu");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(studentData);
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    assertEquals("Create new student", OK, status(result));
    
    // Test POST /students (with simulated, invalid form data).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Student.newStudent(), request);
    
    // Test DELETE /student/Student-01 (a valid StudentId)
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Delete current student OK", OK, status(result));
    result = callAction(controllers.routes.ref.Student.details(studentId));
    assertEquals("Deleted student gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Student.delete(studentId));
    assertEquals("Deleted missing student also OK", OK, status(result));
  }
  
  @Test
  public void testBookController() {
    // Test GET /book on an empty database.
    Result result = callAction(controllers.routes.ref.Book.index());
    assertTrue("Empty books", contentAsString(result).contains("No books"));
    
    // Test GET /book on a database containing a single book.
    String bookId = "Book-01";
    Book book = new Book(bookId, "New Book", "1", "1234567890123", "50");
    book.save();
    result = callAction(controllers.routes.ref.Book.index());
    assertTrue("One book", contentAsString(result).contains(bookId));
    
    // Test GET /book/Book-01
    result = callAction(controllers.routes.ref.Book.details(bookId));
    assertTrue("Book detail", contentAsString(result).contains(bookId));
    
    // Test GET /book/BadBookId and make sure we get a 404
    result = callAction(controllers.routes.ref.Book.details("BadBookId"));
    assertEquals("Book detail (bad)", NOT_FOUND, status(result));
        
    // Test POST /books (with simulated, valid form data).
    Map<String, String> bookData = new HashMap<String, String>();
    bookData.put("bookId", "Book-02");
    bookData.put("name", "Old Book");
    bookData.put("edition", "2");
    bookData.put("isbn", "1234512345123");
    bookData.put("priceNew", "100");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(bookData);
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    assertEquals("Create new book", OK, status(result));
    
    // Test POST /books (with simulated, invalid form data).
    request = fakeRequest();
    result = callAction(controllers.routes.ref.Book.newBook(), request);
    
    // Test DELETE /book/Book-01 (a valid BookId)
    result = callAction(controllers.routes.ref.Book.delete(bookId));
    assertEquals("Delete current book OK", OK, status(result));
    result = callAction(controllers.routes.ref.Book.details(bookId));
    assertEquals("Deleted book gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Book.delete(bookId));
    assertEquals("Deleted missing book also OK", OK, status(result));
  }
  
  @Test
  public void testOfferController() {
    // Test GET /offer on an empty database.
    Result result = callAction(controllers.routes.ref.Offer.index());
    assertTrue("Empty offers", contentAsString(result).contains("No offers"));
    
    // Test GET /offer on a database containing a single offer.
    String offerId = "Offer-01";
    Offer offer = new Offer(offerId, "new", "50");
    offer.save();
    result = callAction(controllers.routes.ref.Offer.index());
    assertTrue("One offer", contentAsString(result).contains(offerId));
    // Include Student and Book for later testing
    String bookId = "Book-01";
    Book book = new Book(bookId, "New Book", "1", "1234567890123", "50");
    book.save();
    String studentId = "Student-01";
    Student student = new Student(studentId, "John Doe", "johndoe@hawaii.edu");
    student.save();
    
    // Test GET /offer/Offer-01
    result = callAction(controllers.routes.ref.Offer.details(offerId));
    assertTrue("Offer detail", contentAsString(result).contains(offerId));
    
    // Test GET /offer/BadOfferId and make sure we get a 404
    result = callAction(controllers.routes.ref.Offer.details("BadOfferId"));
    assertEquals("Offer detail (bad)", NOT_FOUND, status(result));
        
    // Test POST /offers (with simulated, invalid form data).
    Map<String, String> offerData = new HashMap<String, String>();
    offerData.put("offerId", "Offer-02");
    offerData.put("condition", "used");
    offerData.put("price", "60");
    FakeRequest request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create new offer", BAD_REQUEST, status(result));
    
    
    // Test POST /offers (with simulated, valid form data, inclusion of books).
    
    offerData.put("bookName", "New Book");
    offerData.put("bookEdition", "1");
    offerData.put("studentEmail", "johndoe@hawaii.edu");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    assertEquals("Create new offer", OK, status(result));
    
    // Test New book creation 
    offerData.put("bookName", "42");
    request = fakeRequest();
    request.withFormUrlEncodedBody(offerData);
    assertEquals("Create new offer", OK, status(result));
    
    result = callAction(controllers.routes.ref.Offer.newOffer(), request);
    // Test DELETE /offer/Offer-01 (a valid OfferId)
    result = callAction(controllers.routes.ref.Offer.delete(offerId));
    assertEquals("Delete current offer OK", OK, status(result));
    result = callAction(controllers.routes.ref.Offer.details(offerId));
    assertEquals("Deleted offer gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Offer.delete(offerId));
    assertEquals("Deleted missing offer also OK", OK, status(result));
  }
  @Test
  public void testRequestController() {
    // Test GET /request on an empty database.
    Result result = callAction(controllers.routes.ref.Request.index());
    assertTrue("Empty requests", contentAsString(result).contains("No requests"));
    
    // Test GET /request on a database containing a single request.
    String requestId = "Request-01";
    Request request = new Request(requestId, "new", "50");
    request.save();
    result = callAction(controllers.routes.ref.Request.index());
    assertTrue("One request", contentAsString(result).contains(requestId));
    // Include Student and Book for later testing
    String bookId = "Book-01";
    Book book = new Book(bookId, "New Book", "1", "1234567890123", "50");
    book.save();
    String studentId = "Student-01";
    Student student = new Student(studentId, "John Doe", "johndoe@hawaii.edu");
    student.save();
    
    // Test GET /request/Request-01
    result = callAction(controllers.routes.ref.Request.details(requestId));
    assertTrue("Request detail", contentAsString(result).contains(requestId));
    
    // Test GET /request/BadRequestId and make sure we get a 404
    result = callAction(controllers.routes.ref.Request.details("BadRequestId"));
    assertEquals("Request detail (bad)", NOT_FOUND, status(result));
        
    // Test POST /requests (with simulated, invalid form data).
    Map<String, String> requestData = new HashMap<String, String>();
    requestData.put("requestId", "Request-02");
    requestData.put("condition", "used");
    requestData.put("price", "60");
    FakeRequest req = fakeRequest();
    req.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), req);
    assertEquals("Create new request", BAD_REQUEST, status(result));
    
    
    // Test POST /requests (with simulated, valid form data, inclusion of books).
    
    requestData.put("bookName", "New Book");
    requestData.put("bookEdition", "1");
    requestData.put("studentEmail", "johndoe@hawaii.edu");
    req = fakeRequest();
    req.withFormUrlEncodedBody(requestData);
    result = callAction(controllers.routes.ref.Request.newRequest(), req);
    assertEquals("Create new request", OK, status(result));
    
    // Test New book creation 
    requestData.put("bookName", "42");
    req = fakeRequest();
    req.withFormUrlEncodedBody(requestData);
    assertEquals("Create new request", OK, status(result));
    
    result = callAction(controllers.routes.ref.Request.newRequest(), req);
    // Test DELETE /request/Request-01 (a valid RequestId)
    result = callAction(controllers.routes.ref.Request.delete(requestId));
    assertEquals("Delete current request OK", OK, status(result));
    result = callAction(controllers.routes.ref.Request.details(requestId));
    assertEquals("Deleted request gone", NOT_FOUND, status(result));
    result = callAction(controllers.routes.ref.Request.delete(requestId));
    assertEquals("Deleted missing request also OK", OK, status(result));
  }*/
}

