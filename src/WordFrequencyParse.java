import java.io.*;
import java.net.URL;
import java.util.*;

import org.jsoup.Jsoup;

/**
 * This class reads the HTML text file from a URL link and organizes the word
 * frequencies of all words in the file, sorted by the most frequently used
 * word. It puts them all into a Map
 *
 */

public class WordFrequencyParse {

	public LinkedHashMap<String, Integer> finalList = new LinkedHashMap<>();

	/**
	 * This method extracts the text from the HTML file in the URL and returns the
	 * text from the file with no HTML tags.
	 *
	 * @param file URL address
	 * @return text extracted from the file
	 * @throws IOException if there is a problem with the URL
	 */

	public static String extractText(String file) throws IOException {

		// Using jsoup to parse html from the url and BufferedReader to read text from an input stream
		URL reader = new URL(file);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(reader.openStream()));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String text = Jsoup.parse(sb.toString()).text();
		return text;
	}

	/**
	 * This method receives a String and a Map collection and gets the words of the
	 * String and puts them in the collection word per word with the value of 1. If
	 * a word that is obtained from the document is already in the collection, then
	 * we sum 1 to the value of that word.
	 *
	 * @param str   String whose words are counted and put in the collection
	 * @param words collection to store the words and the occurrences of it
	 * @throws FileNotFoundException if the file is not found
	 */

	public static void wordFrequency(String str, Map<String, Integer> words) throws FileNotFoundException {

		// Method to get the words of the string and puts them in a collection and adds 1 for each word
		String arr[] = str.replaceAll("[^a-zA-Z '-]", "").toUpperCase().split("\\s+");
		for (String s : arr) {
			int count = 0;
			if (words.containsKey(s)) {
				count = words.get(s);
			}
			words.put(s, count + 1);
		}
	}

	/**
	 * This method sorts the values in the collection from lowest to highest entry
	 * by comparing each value and then putting them in a new collection.
	 *
	 * @param words collection to store the words and the occurrences of it
	 * @return newList collection that will be passed into a variable in the
	 *         constructor method
	 */

	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> words) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(words.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		HashMap<String, Integer> newList = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			newList.put(aa.getKey(), aa.getValue());
		}
		return newList;
	}

	/**
	 * Method in which we create the first collection and specify the URL from where
	 * we extract the text that will be analyzed. This method makes use of the
	 * methods in the class to create a reverseSortedMap which is a LinkedHashMap
	 * that is filled with the words of the document but now with the highest values
	 * displayed first.
	 *
	 * @param url of the HTML file that we are going to use
	 * @throws Exception
	 */

	public WordFrequencyParse(String url) throws Exception {
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		// URL given with the assignment
		//String document = "http://shakespeare.mit.edu/macbeth/full.html";
		String document = url;

		String play = WordFrequencyParse.extractText(document);

		wordFrequency(play, words);

		Map<String, Integer> sortedList = sortByValue(words);

		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		sortedList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

		finalList = reverseSortedMap;

		DatabaseConnection.createTable();
		DatabaseConnection.deleteAllRows();
		DatabaseConnection.insertAllRows(finalList);

		// Print to text document
		//int count = 1;
		//PrintStream o = new PrintStream(new File("MacbethWordCount.txt"));
		//PrintStream console = System.out;
		//System.setOut(o);
		//for (Map.Entry<String, Integer> en : reverseSortedMap.entrySet()) {
			//System.out.println(count + ". (" + en.getKey() + ", " + en.getValue() + ")");
			//count++;
		//}

		// Print to console
		//count = 1;
		//System.setOut(console);
		//for (Map.Entry<String, Integer> en : reverseSortedMap.entrySet()) {
			//System.out.println(count + ". (" + en.getKey() + ", " + en.getValue() + ")");
			//count++;
		//}
	}

}