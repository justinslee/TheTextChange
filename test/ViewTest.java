import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import org.junit.Test;
import pages.StudentCreatePage;
import pages.StudentEditPage;
import pages.BookCreatePage;
import pages.BookEditPage;
import pages.AdminPage;
import pages.IndexPage;
import play.libs.F.Callback;
import play.test.TestBrowser;
import static org.fest.assertions.Assertions.assertThat;


public class ViewTest {
  
  @Test
  public void testIndexPage () {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        IndexPage homePage = new IndexPage(browser.getDriver(), 3333);
        browser.goTo(homePage);
        homePage.isAt();
      }
    });
  }
  @Test
  public void testAdminPage () {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        AdminPage homePage = new AdminPage(browser.getDriver(), 3333);
        browser.goTo(homePage);
        homePage.isAt();
      }
    });
  }
  
  @Test
  public void testStudentCreatePage () {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        // Create the pages. 
        StudentCreatePage studentPage = new StudentCreatePage(browser.getDriver(), 3333); 
        AdminPage homePage = new AdminPage(browser.getDriver(), 3333);
        // Now test the page.
        browser.goTo(studentPage);
        studentPage.isAt();
        String studentId = "NewTestStudent";
        studentPage.makeNewStudent(studentId);
        homePage.isAt();
        homePage.pageSource().contains(studentId);
      }
    });
  }
  
  @Test
  public void testStudentEditPage () {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        // Define the pages. 
        StudentCreatePage studentPage = new StudentCreatePage(browser.getDriver(), 3333); 
        AdminPage homePage = new AdminPage(browser.getDriver(), 3333);
        // Test that we can create a page.
        browser.goTo(studentPage);
        studentPage.isAt();
        String studentId = "NewTestStudentId";
        studentPage.makeNewStudent(studentId);
        homePage.isAt();
        homePage.pageSource().contains(studentId);
        // Test that we can edit a page. 
        //   We should realestly get the PK from the home page, not just magically know it. 
        StudentEditPage editPage = new StudentEditPage(browser.getDriver(), 3333, 1);
        browser.goTo(editPage);
        String editStudentId = "EditedStudentId";
        editPage.editStudent(editStudentId);
        homePage.pageSource().contains(editStudentId);
        // Test that we can delete the page and it will no longer be found on the home page. 
        browser.goTo(editPage);
        editPage.deleteStudent();
        homePage.isAt();
        assertThat(homePage.pageSource()).doesNotContain(editStudentId);
      }
    });
  }
  @Test
  public void testBookCreatePage () {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        // Create the pages. 
        BookCreatePage bookPage = new BookCreatePage(browser.getDriver(), 3333); 
        AdminPage homePage = new AdminPage(browser.getDriver(), 3333);
        // Now test the page.
        browser.goTo(bookPage);
        bookPage.isAt();
        String bookId = "NewTestBook";
        bookPage.makeNewBook(bookId);
        homePage.isAt();
        homePage.pageSource().contains(bookId);
      }
    });
  }
  
  @Test
  public void testBookEditPage () {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
      public void invoke(TestBrowser browser) {
        // Define the pages. 
        BookCreatePage bookPage = new BookCreatePage(browser.getDriver(), 3333); 
        AdminPage homePage = new AdminPage(browser.getDriver(), 3333);
        // Test that we can create a page.
        browser.goTo(bookPage);
        bookPage.isAt();
        String bookId = "NewTestBookId";
        bookPage.makeNewBook(bookId);
        homePage.isAt();
        homePage.pageSource().contains(bookId);
        // Test that we can edit a page. 
        //   We should realestly get the PK from the home page, not just magically know it. 
        BookEditPage editPage = new BookEditPage(browser.getDriver(), 3333, 1);
        browser.goTo(editPage);
        String editBookId = "EditedBookId";
        editPage.editBook(editBookId);
        homePage.pageSource().contains(editBookId);
        // Test that we can delete the page and it will no longer be found on the home page. 
        browser.goTo(editPage);
        editPage.deleteBook();
        homePage.isAt();
        assertThat(homePage.pageSource()).doesNotContain(editBookId);
      }
    });
  }

}
