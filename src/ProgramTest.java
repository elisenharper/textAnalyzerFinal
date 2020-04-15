import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 *
 * Testing Suite class that includes all the unit test classes of the
 * the project.
 *
 */

@RunWith(Suite.class)
@SuiteClasses({ WordFrequencyTest.class, WordListTest.class })

public class ProgramTest {

}
