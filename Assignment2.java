import java.sql.*;
import java.util.List;
import java.util.ArrayList;

// If you are looking for Java data structures, these are highly useful.
// Remember that an important part of your mark is for doing as much in SQL (not Java) as you can.
// Solutions that use only or mostly Java will not receive a high mark.
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.Set;
//import java.util.HashSet;
public class Assignment2 extends JDBCSubmission {

    public Assignment2() throws ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
    }

    @Override
    public boolean connectDB(String url, String username, String password) {
        // Implement this method!
        try{
//             url = "jdbc:postgresql://localhost:5432/cac343h-wangy542";
//             username = "wangy542";
//             password = "";
            connection = DriverManager.getConnection(url, username, password);
            return true;
        }
        catch(SQLException e){
            System.err.println("SQL Exception." + "<Message>: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean disconnectDB() {
        // Implement this method!
        try{
            connection.close();
            return true;
        }
        catch(SQLException e){
            System.err.println("SQL Exception." + "<Message>: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ElectionCabinetResult electionSequence(String countryName) {
        // Implement this method!
        try{
            String sql;
            sql = "SELECT e.id AS electionId, cabinet.id AS cabinetId " +
                  "FROM country, election e, cabinet " + "WHERE country.name = ? AND " + 
                  "e.country_id = country.id AND cabinet.country_id = country.id AND " +
                  "cabinet.election_id = e.id " + "ORDER BY e.e_date DESC;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, countryName);
            ResultSet rs = stmt.executeQuery();

            List<Integer> electionId = new ArrayList<Integer>();
            List<Integer> cabinetId = new ArrayList<Integer>();

            while(rs.next()){
                electionId.add(rs.getInt("electionId"));
                cabinetId.add(rs.getInt("cabinetId"));
            }
            rs.close();
            //System.out.println(electionId.size());
            //System.out.println(cabinetId.size());
            return new ElectionCabinetResult(electionId, cabinetId);
        }
        catch(SQLException se){
            System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
            return null;
        }
    }

    @Override
    public List<Integer> findSimilarPoliticians(Integer politicianName, Float threshold) {
        // Implement this method!
        return null;
    }

    public static void main(String[] args) {
        // You can put testing code in here. It will not affect our autotester.
        System.out.println("Hello");
        
        //test case
         try {
            Assignment2 testcase = new Assignment2();
            testcase.connectDB("jdbc:postgresql://localhost:5432/csc343h-wangy542?currentSchema=parlgov", "wangy542", "");
            ElectionCabinetResult a = testcase.electionSequence("Japan");
           System.out.println("election id | cabinet id");
             for(int i = 0; i < a.elections.size(); ++i) {
             System.out.println(a.elections.get(i) + " | " + a.cabinets.get(i));
            }
            testcase.disconnectDB();
        }

        catch (ClassNotFoundException e) {
            System.err.println("SQL Exception." +
                       "<Message>: " + e.getMessage());
        }
   }

}
