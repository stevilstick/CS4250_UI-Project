package fxml;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
//import javafx.fxml

public class MainScreenController implements Initializable {
    private Stage stage;
    @FXML
    MenuBar menuBar;
    @FXML
    MenuItem menuOpenText;
    @FXML
    MenuItem menuOpenImage;
    @FXML
    MenuItem menuSaveImage;
    @FXML
    MenuItem menuSaveText;
    @FXML
    ImageView imageViewLeft, imageViewCenter, imageViewRight;
    @FXML
    Button saveLeftImage, saveCenterImage, saveRightImage;
    @FXML
    Button loadLeftImage, loadCenterImage, loadRightImage;
    @FXML
    TextArea textBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadLeftImage.setOnAction(this::handleImageLoadEvent);
        loadCenterImage.setOnAction(this::handleImageLoadEvent);
        loadRightImage.setOnAction(this::handleImageLoadEvent);
        saveLeftImage.setOnAction(this::handleImageSaveEvent);
        saveCenterImage.setOnAction(this::handleImageSaveEvent);
        saveRightImage.setOnAction(this::handleImageSaveEvent);
    }

    private void handleImageSaveEvent(ActionEvent event) {
        Button sourceButton = (Button)event.getSource();
        if(sourceButton.equals(saveLeftImage)) {
            saveImageFromImageView(imageViewLeft);
        }
        else if(sourceButton.equals(saveCenterImage)) {
            saveImageFromImageView(imageViewCenter);
        }
        else if(sourceButton.equals(saveRightImage)) {
            saveImageFromImageView(imageViewRight);
        }
        else {
            System.err.println("Save button not recognized.");
        }
    }

    private void handleImageLoadEvent(ActionEvent event) {
        Button sourceButton = (Button)event.getSource();
        if(sourceButton.equals(loadLeftImage)) {
            openImageFromImageView(imageViewLeft);
        }
        else if(sourceButton.equals(loadCenterImage)) {
            openImageFromImageView(imageViewCenter);
        }
        else if(sourceButton.equals(loadRightImage)) {
            openImageFromImageView(imageViewRight);
        }
        else {
            System.err.println("Load button not recognized.");
        }
    }


    private static void configureFileChooserImage(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private static void configureFileChooserText(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Text Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
    }

    private void openImageFromImageView(ImageView view) {
        /*
            Charles and Jamie, This is our test to get image and text loading working.
            This is called by the handlers of the FileChooser.
        */

        try {
            final FileChooser fileChooser = new FileChooser();
            configureFileChooserImage(fileChooser);
            File file = fileChooser.showOpenDialog(stage);
            Image image = new Image(file.toURI().toString());
            view.setImage(image);

        } catch (Exception e) {
            System.out.println("Oh no");
        }
    }

    /*
            Charles & Jamie, sample usage with preferred FileChooser for
            save location.  Extracts an image from an ImageView.
            Not 100% positive saveImageFromImageView or saveText work without integration
            with UI.  Will work with you if there are any hangups with these
            methods.

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            File file = fileChooser.showSaveDialog(stage);
            saveImageFromImageView(file, imgView);
    */
    private boolean saveImageFromImageView(ImageView imgView) {
        try {
            final FileChooser fileChooser = new FileChooser();

            configureFileChooserImage(fileChooser);
            File file = fileChooser.showSaveDialog(stage);
            ImageIO.write(SwingFXUtils.fromFXImage(imgView.getImage(),
                    null), "png", file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    private boolean saveText(File file, String text) {
        if (file != null) {
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
                writer.write(text, 0, text.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
                return false;
            }
        }

        return true;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
