import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * This program is a text analyzer that reads a html URL that the user inputs
 * and outputs the word frequencies of all words in the file, sorted by the most
 * frequently used word. The output is displayed in a JavaFX UI table with each
 * pair containing a word and how many times it occurred in the file.
 *
 *
 */

public class TextAnalyzerGUI extends Application {

	Stage window;
	Button button;
	TableView<WordList> table;
	String url = "";
	public LinkedHashMap<String, Integer> importedList = new LinkedHashMap<>();

	/**
	 * Main method in which we launch the UI and thus the rest of the processes go
	 * into action.
	 *
	 * @param args an array of command-line arguments for the application.
	 * @throws IOException if there is a problem in the input of the file.
	 */

	public static void main(String[] args) throws IOException {
		launch(args);

	}

	/**
	 * Method that is called by the main method in order to create the UI for the
	 * program. Here we set the elements of the UI including labels, texts, text
	 * boxes and tables.
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Text Analyzer GUI");

		Label instructionsLabel = new Label("Type the URL for the text you want the word count for");

		Label urlLabel = new Label("URL:");

		TextField urlInput = new TextField();
		urlInput.setPrefWidth(600);

		button = new Button();
		button.setText("Get word frequencies");
		button.setOnAction(e -> {
			try {
				url = urlInput.getText();
				if (url.isEmpty()) {
					URLCheck.display("Invalid URL", "Please type a valid HTML URL");
				} else {
					table.setItems(getWord(url));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				URLCheck.display("Invalid URL", "Please type a valid HTML URL");
			}
		});

		// Order column
		TableColumn<WordList, Integer> orderColumn = new TableColumn<>("Order");
		orderColumn.setMinWidth(200);
		orderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));

		// Word column
		TableColumn<WordList, String> wordColumn = new TableColumn<>("Word");
		wordColumn.setMinWidth(300);
		wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));

		// Frequency column
		TableColumn<WordList, Integer> frequencyColumn = new TableColumn<>("Frequency");
		frequencyColumn.setMinWidth(200);
		frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));

		table = new TableView<>();
		table.getColumns().addAll(orderColumn, wordColumn, frequencyColumn);
		table.prefHeightProperty().bind(window.heightProperty());
		table.prefWidthProperty().bind(window.widthProperty());

		HBox hBox = new HBox();
		hBox.getChildren().addAll(urlLabel, urlInput);
		hBox.setSpacing(20);
		hBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(instructionsLabel, hBox, button, table);
		vBox.setPadding(new Insets(10, 50, 50, 50));
		vBox.setSpacing(10);
		vBox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vBox);
		window.setScene(scene);
		window.setHeight(650);
		window.setWidth(815);
		window.show();
	}

	/**
	 * This method gets the list of words from the WordFrequencyFinder class and
	 * adds it to the observable list that will be displayed in the UI.
	 *
	 * @param urlLink HTML URL link inputed by the user which the program will
	 *                analyze.
	 * @return words Observable list that will be displayed in the UI
	 * @throws Exception
	 */
	public ObservableList<WordList> getWord(String urlLink) throws Exception {
		ObservableList<WordList> words = FXCollections.observableArrayList();
		new WordFrequencyParse(urlLink);

		int count = 1;

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			con = DatabaseConnection.getConnection();
			statement = con.prepareStatement("Select * FROM word");
			result = statement.executeQuery();
			while (result.next()) {
				words.add(new WordList(count, result.getString("word"), result.getInt("word_count")));
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return words;

	}

}
