import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchMcKinney")
public class SearchMcKinney extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchMcKinney() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionMcKinney.getDBConnection();
         connection = DBConnectionMcKinney.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM favoriteSportsTeams";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM favoriteSportsTeams WHERE EMAIL LIKE ?";
            String theEmail = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theEmail);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String football = rs.getString("football").trim();
            String baseball = rs.getString("baseball").trim();
            String basketball = rs.getString("basketball").trim();
            String email = rs.getString("email").trim();

            if (keyword.isEmpty() || email.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("Favorite Football Team: " + football + ", ");
               out.println("Favorite Baseball Team: " + baseball+ ", ");
               out.println("Favorite Basketball Team: " + basketball + ",");
               out.println("Email: " + email + "<br>");
            }
         }
         out.println("<a href=/webproject-techExercise-1025-McKinney/search_mckinney.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
