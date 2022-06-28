import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/JDBC_Assignment")
public class JDBC_Assignment extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String InsertUSN= request.getParameter("I_USN");
		String InsertName= request.getParameter("I_Name");
		String InsertCGPA = request.getParameter("I_CGPA");
		String InsertContact = request.getParameter("I_Contact");
                
		String UpdateName= request.getParameter("U_Name");
		String UpdateCGPA = request.getParameter("U_CGPA");
		String UpdateContact = request.getParameter("U_Contact");
		String UpdateWhere = request.getParameter("U_where");
                
                String Op = request.getParameter("r1");
                
                out.print("<html>");
                out.print("<Head>"
                        + "<Title>JDBC_Implementation_Servlet</Title>"
                        + "</Head>"
                        + "<style>"+
                          "h3, p{ text-align: center;}"+
                          "table, th, td, caption{\n" +
                          "border: 1px solid black;\n" +"table, th, td{width: 300px;}"+
                          "border-collapse: collapse;\n}"
                        + "table.Table {\n" +
                          "margin-left: auto; \n" +
                          "margin-right: auto;\n" +"}"
                        + "</style>");
                       
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaschema", "root", "root");
			Statement stmt = conn.createStatement();
			
                        if("Insert".equals(Op)){                           
                            int Insert = stmt.executeUpdate("insert into student_details values('"+InsertUSN+"', '"+InsertName+"', '"
                                                            +InsertCGPA+"',"+InsertContact+");");
                            if(Insert>0) {
				out.print("<p>---Inserted 1 Row to Database successfully---</p>");
                            }else{
                                out.print("<p>---Recheck the entered data---</p>");
                            } 
			}
                        else if("Update".equals(Op)){
                            int Update = stmt.executeUpdate("update student_details set Name='"+UpdateName+"', CGPA='"+UpdateCGPA+"', Contact='"
                                                            +UpdateContact+"' where "+UpdateWhere+";");
                            if(Update>0){
                                out.print("<p>---Database Updated Successfully---</p>");
                            }else{
                                out.print("<p>---Recheck the entered data---</p>");
                            }                            
                        }else{
                            out.print("<h3>---NO OPERATION SELECTED---</h3>");
                        }
                        
                        
                        ResultSet resultSet = stmt.executeQuery("select * from student_details;");
                        
                        out.println("<Body>"
                                + "<table class=\"Table\">\n" +
                                  "<caption>Student Details</caption>\n" +
                                  "<tr>\n" +
                                  "<td style=\"text-align:center\">USN</td>\n" +
                                  "<td style=\"text-align:center\">Name</td>\n" +
                                  "<td style=\"text-align:center\">CGPA</td>\n" +
                                  "<td style=\"text-align:center\">Contact</td>\n" +
                                  "</tr>");
                        while(resultSet.next()){
                            String USN = resultSet.getString("USN");
                            String Name = resultSet.getString("Name");
                            String CGPA = resultSet.getString("CGPA");
                            String Contact = resultSet.getString("Contact");
                                    
                            out.print("<tr><td>"+USN+"</td>"+
                                      "<td>"+Name+"</td>"+
                                      "<td>"+CGPA+"</td>"+
                                      "<td>"+Contact+"</td></tr>");
                        }
                        out.print("</Table><Center><body></html>");		
                    }catch(Exception e) {
                       out.print(e);
                    }
	}
}