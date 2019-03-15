import java.sql.*;
import java.util.List;

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
            Connection conn = DriverManager.getConnection(url, username, password);
            return true;
        }
        catch(SQLException e){
            return false;
        }

        return false;
    }

    @Override
    public boolean disconnectDB() {
        // Implement this method!
        if(conn != NULL){
            conn.close();
            return true;
        }
        else 
            return false;
    }

    @Override
    public ElectionCabinetResult electionSequence(String countryName) {
        // Implement this method!
        try{
            String sql;
            sql = "SELECT e.id AS electionId, cabinet.id AS cabinetId " +
                  "FROM country, election e, cabinet " + "WHERE country.name = '?' AND " + 
                  "e.country_id = country.id AND cabinet.country_id = country.id AND " +
                  "cabinet.election_id = e.id " + "ORDER BY e.e_date DESC;";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, countryName);
            Resultset rs = stmt.executeQuery();

            List<Int> electionId = new ArrayList<Int>();
            List<Int> cabinetId = new ArrayList<Int>();

            while(rs.next()){
                electionId.add(rs.getInt("electionId"));
                cabinetId.add(rs.getInt("cabinetId"));
            }
            rs.close();
            return new FinalResult(electionId, cabinetId);
        }
        catch(SQLException se){
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
    }

}
