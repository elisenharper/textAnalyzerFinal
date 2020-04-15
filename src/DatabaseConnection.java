import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * This is a database connection class that we use to connect to the database
 * and be able to perform database-related tasks.
 *
 */

public class DatabaseConnection {

	/**
	 * This method deletes all rows of the database to accommodate the new entries
	 * of words that are going to be placed in the database.
	 *
	 * @throws Exception if there is a problem connecting to the database or running
	 *                   the SQL
	 */

	public static void deleteAllRows() throws Exception {

		Connection con = null;
		PreparedStatement statement = null;

		try {
			con = getConnection();
			statement = con.prepareStatement("TRUNCATE TABLE word");
			statement.executeUpdate();
			System.out.println("Table has been cleared");
		} catch (Exception e) {
			System.out.println(e);
		} finally {

			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}

		}

	}

	/**
	 * This method inserts all the words and their respective word count into the
	 * database.
	 *
	 * @param finalList of words that will be inserted into the database with their
	 *                  respective word count.
	 * @throws Exception if there is a problem connecting to the database or running
	 *                   the SQL
	 */

	public static void insertAllRows(LinkedHashMap<String, Integer> finalList) throws Exception {

		Connection con = null;
		PreparedStatement statement = null;

		try {
			con = getConnection();
			String sql = "INSERT INTO word(word,word_count) " + "VALUES(?,?)";
			statement = con.prepareStatement(sql);
			for (Map.Entry<String, Integer> en : finalList.entrySet()) {
				statement.setString(1, en.getKey());
				statement.setInt(2, en.getValue());
				statement.executeUpdate();
			}
			System.out.println("Table entries have been inserted");

		} catch (Exception e) {
			System.out.println(e);
		} finally {

			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}

		}

	}

	/**
	 * This method created the word table if it has not been created already for the
	 * wordocurrences schema.
	 *
	 * @throws Exception if there is a problem connecting to the database or running
	 *                   the SQL
	 */

	public static void createTable() throws Exception {

		Connection con = null;
		PreparedStatement statement = null;

		try {
			con = getConnection();
			statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS `wordoccurrences`.`word` (\r\n"
					+ "  `word_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + "  `word` VARCHAR(45) NOT NULL,\r\n"
					+ "  `word_count` INT(10) UNSIGNED NOT NULL,\r\n" + "  PRIMARY KEY (`word_id`))");
			statement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		} finally {

			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * This is the method that gets called when another method needs to use the
	 * database.
	 *
	 * @return con if the connection to the database was established and null if the
	 *         connection to the database we not established
	 * @throws Exception if there is a problem connecting to the database
	 */

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/wordoccurrences";
			String user = "root";
			String password = "Tester123";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;

	}

}