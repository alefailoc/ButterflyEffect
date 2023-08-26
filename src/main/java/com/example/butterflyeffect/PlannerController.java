package com.example.butterflyeffect;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

    public class PlannerController {

        @FXML
        private ImageView photoOne;
        @FXML
        private ImageView photoTwo;

        @FXML
        protected void onHelloButtonClick() throws IOException {
            StartApplication.setRoot("configuration-view");
        }

        private FileChooser fileChooser;
        private File filePath;

        //This method will allow the user to change the image on the screen
        public void change1ImageBtn (ActionEvent event) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            Window stage = null;
            this.filePath = fileChooser.showOpenDialog(stage);

            //Try to update the image by loading the new image

            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                photoOne.setImage(image);
            } catch (IOException e)
            {
                System.err.println(e.getMessage());
            }

        }
        public void change2ImageBtn (ActionEvent event) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            Window stage = null;
            this.filePath = fileChooser.showOpenDialog(stage);

            //Try to update the image by loading the new image

            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                photoTwo.setImage(image);
            } catch (IOException e)
            {
                System.err.println(e.getMessage());
            }

        }


    }
