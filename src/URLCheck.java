import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * This class was made to create an alert box for when users, so they don't
 * enter invalid URL's.
 *
 */
public class URLCheck {

	/**
	 * Create an alert box for when users don't enter valid URL's
	 *
	 * @param title   of the Window pop up
	 * @param message of the pop up
	 */

	public static void display(String title, String message) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);

		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setPadding(new Insets(10, 50, 20, 50));
		layout.setSpacing(10);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

}