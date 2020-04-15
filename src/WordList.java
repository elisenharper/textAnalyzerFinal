
public class WordList {

	/**
	 * Word class used for adding to the list of words to the ObservableList
	 */
	private int order;
	private String word;
	private int frequency;

	/**
	 * Word class constructor method to store the characteristics of each word.
	 *
	 * @param order     of frequency of the word
	 * @param word      that is being analyzed for frequency
	 * @param frequency of the word
	 */

	public WordList(int order, String word, int frequency) {

		this.order = order;
		this.word = word;
		this.frequency = frequency;

	}

	/*
	 * Getter method for the order variable
	 *
	 * @return order of frequency of the word
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Setter method for the order variable
	 *
	 * @param order of frequency of the word
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/*
	 * Getter method for the word variable
	 *
	 * @return that is being analyzed for frequency
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Setter method for the word variable
	 *
	 * @param word that is being analyzed for frequency
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * Getter method for the frequency variable
	 *
	 * @return frequency of the word
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Setter method for the frequency variable
	 *
	 * @param frequency of the word
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}