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
import java.nio.file.Paths;
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
    ImageView imageViewLeft, imageViewCenter, imageViewRight;
    @FXML
    Button saveLeftImage, saveCenterImage, saveRightImage;
    @FXML
    Button loadLeftImage, loadCenterImage, loadRightImage;
    @FXML
    Button loadText, saveText;
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
        loadText.setOnAction(this::handleTextLoadEvent);
        saveText.setOnAction(this::handleTextSaveEvent);
    }

    protected void handleImageSaveEvent(ActionEvent event) {
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

    protected void handleImageLoadEvent(ActionEvent event) {
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

    protected void handleTextSaveEvent(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();

        configureFileChooserText(fileChooser);
        File file = fileChooser.showSaveDialog(stage);
        if(!saveText(file,textBox.getText())) {
            System.err.println("Text failed to save.");
        }
    }

    protected void handleTextLoadEvent(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooserText(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        String text = loadText(file);
        if(text != null) {
            textBox.setText(text);
        }
    }

    protected static void configureFileChooserImage(
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

    protected static void configureFileChooserText(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Text Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("Text", "*.txt")
        );
    }

    protected void openImageFromImageView(ImageView view) {

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

    protected boolean saveImageFromImageView(ImageView imgView) {
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

    protected boolean saveText(File file, String text) {
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

    protected String loadText(File file) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
            return new String(bytes);
        } catch(IOException x) {
            System.err.format("IOException: %s%n", x);
            return null;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
