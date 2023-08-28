package com.example.butterflyeffect;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//https://www.youtube.com/watch?v=KzfhgGGzWMQ UI- TabPane

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

                    // Load images path from settings and set the images
                    String imagePath = AppSettings.getPhotoTwoImagePath();
                    if (!imagePath.isEmpty()) {
                        try {
                            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
                            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                            photoTwo.setImage(image);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }

                    String imagesPath = AppSettings.getPhotoOneImagePath();
                    if (!imagesPath.isEmpty()) {
                        try {
                            BufferedImage bufferedImage = ImageIO.read(new File(imagesPath));
                            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                            photoOne.setImage(image);
                            AppSettings.setPhotoOneImagePath(imagesPath);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
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
                }

                private void updatePlanner() {
                    // Delete all tasks from weekHBox TableViews
                    for (int i = 0; i < 7; i++) {
                        TableView<Task> tableView = (TableView<Task>) weekHbox.getChildren().get(i);
                        tableView.getItems().clear();
                    }

                    for (Task task : tasks) {
                        int dayIndex = getDayIndex(task.getDueDate());

                        if (dayIndex != -1) {
                            TableView<Task> dayTable = (TableView<Task>) weekHbox.getChildren().get(dayIndex);

                            int priority = task.getPriority();
                            double timeTaken = Math.round(task.getTimeTaken()); // Round timeTaken to the closest whole number

                            // Insert task based on priority
                            if (timeTaken <= 2) {
                                insertTask(dayTable, task, priority, (int) timeTaken); // Convert timeTaken to int
                            } else {
                                int daysToAdd = (int) Math.ceil(timeTaken / 2.0); // Calculate how many days to add
                                int remainingTime = (int) timeTaken;

                                for (int i = 0; i < daysToAdd; i++) {
                                    int currentIndex = dayIndex - i;

                                    if (currentIndex >= 0) {
                                        TableView<Task> prevDayTable = (TableView<Task>) weekHbox.getChildren().get(currentIndex);
                                        int timeForDay = Math.min(remainingTime, 2); // Take at most 2 hours per day
                                        insertTask(prevDayTable, task, priority, timeForDay);
                                        remainingTime -= timeForDay;
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < 7; i++) {
                            TableView<Task> dayTable = (TableView<Task>) weekHbox.getChildren().get(i);

                            // Add event handler to open pop-up on double-click
                            //https://stackoverflow.com/questions/10949461/javafx-2-click-and-double-click
                            dayTable.setRowFactory(tv -> {
                                TableRow<Task> row = new TableRow<>();
                                row.setOnMouseClicked(event -> {
                                    if (!row.isEmpty() && event.getClickCount() == 2) {
                                        Task selectedTask = row.getItem();
                                        showTaskDetailsPopup(selectedTask);
                                    }
                                });
                                return row;
                            });
                        }
                    }

                private void showTaskDetailsPopup(Task selectedTask) {
                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.setTitle(selectedTask.getTask());
                    // Content for the pop-up window
                    VBox popupContent = new VBox();
                    popupContent.setPadding(new Insets(10));
                    Label detailsLabel = new Label("Details:\n" + selectedTask.getDetails());
                    Label timeTakenLabel = new Label("\n" + "\n" + "\n" + "\n" + "This task is expected to be performed for: " + selectedTask.getTimeTaken() + " hours");
                    popupContent.getChildren().addAll(detailsLabel, timeTakenLabel);
                    // The scene is created and set it to the stage
                    Scene popupScene = new Scene(popupContent, 300, 150);
                    popupStage.setScene(popupScene);
                    // The pop-up window is showed
                    popupStage.showAndWait();
                }


                private void insertTask(TableView<Task> dayTable, Task task, int priority, int timeTaken) {
                    int insertIndex = 0;

                    // The correct index is found based on priority
                    for (int i = 0; i < dayTable.getItems().size(); i++) {
                        if (priority > dayTable.getItems().get(i).getPriority()) {
                            insertIndex = i + 1;
                        }
                    }
                    // The task is inserted at the calculated index
                    dayTable.getItems().add(insertIndex, new Task(task.getTask(), priority, timeTaken, task.getDueDate(), task.getDetails(), task.isItDifficult(), task.isItEasy()));
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


            //This section allows the user to change the images on the screen
            //https://www.youtube.com/watch?v=UotiVqAjhDY&t=94s
                private FileChooser fileChooser;
                private File filePath;

                public void change1ImageBtn(ActionEvent event) {
                    fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Image");
                    Window stage = null;
                    this.filePath = fileChooser.showOpenDialog(stage);

                    try {
                        BufferedImage bufferedImage = ImageIO.read(filePath);
                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                        photoOne.setImage(image);

                        // Save the image path to settings
                        AppSettings.setPhotoOneImagePath(filePath.getAbsolutePath());
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }

                }

                public void change2ImageBtn(ActionEvent event) {
                    fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Image");
                    Window stage = null;
                    this.filePath = fileChooser.showOpenDialog(stage);

                    try {
                        BufferedImage bufferedImage = ImageIO.read(filePath);
                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                        photoTwo.setImage(image);

                        AppSettings.setPhotoTwoImagePath(filePath.getAbsolutePath());
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
