package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Book extends Model {
  private static final long serialVersionUID = -894164648973164897L;
  @Id
  private Long primaryKey;
  @Column(unique=true)
  @Required
  private String bookId;
  @Required
  private String name;
  private String edition;
  private String isbn;
  private String priceNew;  
  @OneToMany(mappedBy="book", cascade=CascadeType.ALL)
  private List<Offer> offers = new ArrayList<>();
  @OneToMany(mappedBy="book", cascade=CascadeType.ALL)
  private List<Request> requests = new ArrayList<>();

  public Book(String bookId, String name, String edition, String isbn, String priceNew) {
    this.bookId = bookId;
    this.name = name;
    this.edition = edition;
    this.isbn = isbn;
    this.priceNew = priceNew;
  }
 
  public static Finder<Long, Book> find() {
    return new Finder<Long, Book>(Long.class, Book.class);
  }
  
  public String toString() {
    return String.format("[Book %s %s %s %s %s]", getBookId(), getName(), getEdition(), getIsbn(), getPriceNew());
  }
  
  public String getBookId() {
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEdition() {
    return edition;
  }

  public void setEdition(String edition) {
    this.edition = edition;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getPriceNew() {
    return priceNew;
  }

  public void setPriceNew(String priceNew) {
    this.priceNew = priceNew;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  public List<Request> getRequests() {
    return requests;
  }

  public void setRequests(List<Request> requests) {
    this.requests = requests;
  }
  
  
}
