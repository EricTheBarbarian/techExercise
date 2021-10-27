/**
 * @file InsertMcKinney.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertMcKinney")
public class InsertMcKinney extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertMcKinney() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String football = request.getParameter("football");
      String baseball = request.getParameter("baseball");
      String basketball = request.getParameter("basketball");
      String email = request.getParameter("email");

      Connection connection = null;
      String insertSql = " INSERT INTO favoriteSportsTeams (id, FOOTBALL, BASEBALL, BASKETBALL, email) values (default, ?, ?, ?, ? )";

      try {
         DBConnectionMcKinney.getDBConnection();
         connection = DBConnectionMcKinney.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, football);
         preparedStmt.setString(2, baseball);
         preparedStmt.setString(3, basketball);
         preparedStmt.setString(4, email);
         
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Favorite Football Team</b>: " + football + "\n" + //
            "  <li><b>Favorite Baseball Team</b>: " + baseball + "\n" + //
            "  <li><b>Favorite Basketball Team</b>: " + basketball + "\n" + //
            "  <li><b>Email</b>: " + email+ "\n" + //
            "</ul>\n");

      out.println("<a href=/webproject-techExercise-1025-McKinney/search_mckinney.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
