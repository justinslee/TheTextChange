package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Offer extends Model {
  private static final long serialVersionUID = -244164648973164897L;
  @Id
  private Long primaryKey;
  @Column(unique=true)
  @Required
  private String offerId;
  @Required
  private String condition;
  @Required
  private String price;
  @ManyToOne
  private Student student;
  @Transient
  private String studentEmail;
  @ManyToOne
  private Book book;
  @Transient
  private String bookName;
  @Transient 
  private String bookEdition;
  
  public Offer(String offerId, String condition, String price) {
    this.offerId = offerId;
    this.condition = condition;
    this.price = price;
  }
 
  public static Finder<Long, Offer> find() {
    return new Finder<Long, Offer>(Long.class, Offer.class);
  }
  
  public String validate() {
    Student studentTemp = Student.find().where().eq("email", getStudentEmail()).findUnique();
    Book bookTemp = Book.find().where().eq("name", getBookName()).eq("edition",getBookEdition()).findUnique();
    if (studentTemp == null) {
      return "Invalid. Student must exist.";
    }
    else if (bookTemp == null){ 
      Book bookNew = new Book("Book-"+getPrimaryKey().toString(),getBookName(),getBookEdition(), null, null);
      setBook(bookNew);
      book.save();
    } else {
      setBook(bookTemp);
    }
    setStudent(studentTemp);
    save();
    return null;
  }
  
  public String toString() {
    return String.format("[Offer %s %s %s]", offerId, condition, price);
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public String getStudentEmail() {
    return studentEmail;
  }

  public void setStudentEmail(String studentEmail) {
    this.studentEmail = studentEmail;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public String getBookEdition() {
    return bookEdition;
  }

  public void setBookEdition(String bookEdition) {
    this.bookEdition = bookEdition;
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }
}
