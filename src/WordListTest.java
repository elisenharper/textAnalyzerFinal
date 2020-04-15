import static org.junit.Assert.*;

import org.junit.Test;

public class WordListTest {

	@Test
	/* This is testing the methods from the WordList class. It simply tests the inputs
	 * from the first call of the object and then makes sure the getters and setters
	 * all work as expected */
	public void test() {
		WordList word1 = new WordList(5, "Hello", 6);
		assertEquals(5, word1.getOrder());
		word1.setOrder(7);
		assertEquals(7, word1.getOrder());
		assertEquals("Hello", word1.getWord());
		word1.setWord("Bye");
		assertEquals("Bye", word1.getWord());
		assertEquals(6, word1.getFrequency());
		word1.setFrequency(9);
		assertEquals(9, word1.getFrequency());

	}
}
