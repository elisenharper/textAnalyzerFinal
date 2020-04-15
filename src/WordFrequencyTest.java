import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

// This tests the class WordFrequencyParse. It verifies each of the methods of that class.

public class WordFrequencyTest {

	@Test
	/* This tests the extractText method. This test uses text from an
	 HTML file hosted on my github and verifies that the extracted text using the method is the correct one
	 and throws IOException if there's a problem in the file */

	public void test1() throws IOException {
		assertEquals("Hello world! Hello World!", WordFrequencyParse.extractText(
				"https://gist.github.com/elisenharper/6f500e99ba56e230c61693c178713c59"));
	}

	@Test
	/* This is testing the wordFrquency method. The test gives the method a String
	 and verifies that it's counting the frequency of the words in
	 by comparing it to a HashMap that has the correct words and
	 frequencies and throws an exception if the text file isn't found. */

	public void test2() throws FileNotFoundException {
		HashMap<String, Integer> wordsTestVariable = new HashMap<String, Integer>();
		wordsTestVariable.put("HELLO", 2);
		wordsTestVariable.put("WORLD", 2);
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		String play = "Hello World! Hello World!";
		WordFrequencyParse.wordFrequency(play, words);
		assertTrue(wordsTestVariable.equals(words));
	}

	@Test
	/* This tests the sortByValue method by verifying the HashMap that is sent to
	 the method comes back with the Map entries sorted from lowest to highest. */

	public void test3() {
		int count = 0;
		ArrayList<Integer> arrli = new ArrayList<Integer>();
		arrli.add(2);
		arrli.add(5);
		arrli.add(7);
		HashMap<String, Integer> wordsTestVariable = new HashMap<String, Integer>();
		wordsTestVariable.put("HELLO", 2);
		wordsTestVariable.put("MY", 7);
		wordsTestVariable.put("WORLD", 5);
		Map<String, Integer> sortedList = WordFrequencyParse.sortByValue(wordsTestVariable);
		for (Map.Entry<String, Integer> aa : sortedList.entrySet()) {
			assertEquals((int) arrli.get(count), (int) aa.getValue());
			count++;
		}

	}

	@Test
	/* Tests getWordList method and the WordFrequencyParse method.
	 It verifies the URL sent to the constructor creates the correct list
	 of entries and sorted them correctly */

	public void test4() throws Exception {
		int count = 0;
		ArrayList<Integer> arrli = new ArrayList<Integer>();
		arrli.add(2);
		arrli.add(1);
		arrli.add(1);
		WordFrequencyParse wordFinder = new WordFrequencyParse(
				"https://gist.github.com/elisenharper/6f500e99ba56e230c61693c178713c59");

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			con = DatabaseConnection.getConnection();
			statement = con.prepareStatement("Select * FROM word");
			result = statement.executeQuery();
			while (result.next()) {
				assertEquals((int) arrli.get(count), result.getInt("word_count"));
				count++;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

}
