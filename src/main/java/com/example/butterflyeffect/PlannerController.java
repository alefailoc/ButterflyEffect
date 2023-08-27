package com.example.butterflyeffect;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class PlannerController extends ConfigurationController{

        @FXML
        public HBox weekHbox;
        @FXML
        private ImageView photoOne;
        @FXML
        private ImageView photoTwo;

        @FXML
        protected void configurationTaskBtn() throws IOException {
            StartApplication.setRoot("configuration-view");
        }


        public void initialize() {
            LoadTasks();

            for (int i = 0; i < 7; i++) {
                TableView<Task> currentDayTable = new TableView<>();
                TableColumn<Task, String> column = new TableColumn<>(getDay(i));
                column.setCellValueFactory(new PropertyValueFactory<>("task"));
                column.setPrefWidth(91.5);

                currentDayTable.getColumns().add(column);
                weekHbox.getChildren().add(currentDayTable);
            }

            updatePlanner();
            //clearTasksForNewWeek();

        }

    private void clearTasksForNewWeek() {
        LocalDate today = LocalDate.now();
        LocalDate mondayOfThisWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Check if it's a new week (Monday)
        if (today.getDayOfWeek() == DayOfWeek.MONDAY && !today.isEqual(mondayOfThisWeek)) {
            tasks.clear();
            saveTasksToJson();
        }
    }


    private String getDay(int i) {
            switch (i) {
                case 0:
                    return "mon";
                case 1:
                    return "tues";
                case 2:
                    return "wed";
                case 3:
                    return "thurs";
                case 4:
                    return "fri";
                case 5:
                    return "sat";
                case 6:
                    return "sun";
                default:
                    return "";
            }
        }

        private void updatePlanner() {

                // Delete all tasks from weekHbox's TableViews
                for (int i = 0; i < 7; i++) {
                    TableView<Task> tableView = (TableView<Task>) weekHbox.getChildren().get(i);
                    tableView.getItems().clear();
                }

            for (Task task : tasks) {
                int dayIndex = getDayIndex(task.getDueDate());

                if (dayIndex != -1) {
                    TableView<Task> dayTable = (TableView<Task>) weekHbox.getChildren().get(dayIndex);

                    int priority = task.getPriority();

                    // Insert task based on priority
                    if (dayTable.getItems().isEmpty()) {
                        // If the list is empty, simply add the task
                        dayTable.getItems().add(task);
                    } else {
                        // Find the correct index based on priority
                        int insertIndex = 0;
                        for (int i = 0; i < dayTable.getItems().size(); i++) {
                            if (priority > dayTable.getItems().get(i).getPriority()) {
                                insertIndex = i + 1;
                            }
                        }
                        // Insert the task at the calculated index
                        dayTable.getItems().add(insertIndex, task);
                    }
                }
            }
        }


    private int getDayIndex(String dueDate) {
            switch (dueDate.toLowerCase()) {
                case "mon":
                    return 0;
                case "tues":
                    return 1;
                case "wed":
                    return 2;
                case "thurs":
                    return 3;
                case "fri":
                    return 4;
                case "sat":
                    return 5;
                case "sun":
                    return 6;
                default:
                    return -1; // Invalid day
            }
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
