import fxml.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application
{
	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override 
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        MainScreenController controller = (MainScreenController)loader.getController();
        controller.setStage(stage);
		stage.setTitle("PicView");
		stage.setScene(scene);
        stage.sizeToScene();
		stage.show();
	}
}

