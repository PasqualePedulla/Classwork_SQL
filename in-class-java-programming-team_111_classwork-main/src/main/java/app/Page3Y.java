package app;

import java.sql.Connection; // Added missing import
import java.sql.Statement;  // Added missing import
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Page3Y implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "3Y";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>3Y Page</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Prog. 2 Sample</a>
                <a href='2X'>Prog. 2X</a>
                <a href='2Y'>Prog. 2Y</a>
                <a href='movies'>Prog. 3 Sample</a>
                <a href='3X'>Prog. 3X</a>
                <a href='3Y'>Prog. 3Y</a>
                <a href='moviestype'>Prog. 4 Sample</a>
                <a href='4X'>Prog. 4X</a>
                <a href='4Y'>Prog. 4Y</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>
                    <img src='logo.png' class='top-image' alt='RMIT logo' height='75'>
                    3Y Page
                </h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";
        html = html + "<p>Use your combined Java, HTML and SQL skills to show the list of all movie stars.</p>";
        
        // --- DISPLAY THE DATABASE RESULTS ON THE WEBPAGE ---
        html = html + "<ul>";
        ArrayList<String> moviesList = getMovieStars();
        for (String movie : moviesList) {
            html = html + "<li>" + movie + "</li>";
        }
        html = html + "</ul>";
        // ----------------------------------------------------

        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                <p>COSC2803 - Java Programming Studio</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    /**
     * Get all of the Movie Titles in the database
     * @return
     *    Returns an ArrayList of String with ONLY the movie titles
     */
    public ArrayList<String> getMovieStars() {
        // Create the ArrayList to return - of Strings for the movie titles
        ArrayList<String> movies = new ArrayList<String>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC database
            // Note: Ensure JDBCConnection.DATABASE points to your valid SQLite string
            connection = DriverManager.getConnection(JDBCConnection.DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT mvtitle, yrmde FROM movie";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String movieName = results.getString("mvtitle");
                int movieYear = results.getInt("yrmde");

                // Store the movieName and Year in the ArrayList to return
                movies.add(movieName + " - " + movieYear);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the movie stars
        return movies;
    }
} // Added missing closing bracket for the class
