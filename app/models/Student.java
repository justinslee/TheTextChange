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
public class Student extends Model {
  private static final long serialVersionUID = -654164648973164897L;
  @Id
  private Long primaryKey;
  @Column(unique=true)
  @Required
  private String studentId;
  @Required
  private String name;
  @Required
  private String email;
  @OneToMany(mappedBy="student", cascade=CascadeType.ALL)
  private List<Offer> offers = new ArrayList<>();
  @OneToMany(mappedBy="student", cascade=CascadeType.ALL)
  private List<Request> requests = new ArrayList<>();

  public Student(String studentId, String name, String email) {
    this.studentId = studentId;
    this.name = name;
    this.email = email;
  }
  
  public static Finder<Long, Student> find() {
    return new Finder<Long, Student>(Long.class, Student.class);
  }
  
  public String toString() {
    return String.format("[Student %s %s %s]", getStudentId(), getName(), getEmail());
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }
  
  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
  
  public static List<String> getEmails() {
    List<String> studentEmails = new ArrayList<>();
    for (Student student : find().all()) {
      studentEmails.add(student.email);
    }
    return studentEmails;
  }
  
  
}
