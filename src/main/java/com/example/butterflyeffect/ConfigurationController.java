package com.example.butterflyeffect;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import com.google.gson.Gson;


public class ConfigurationController {

    public TextField tNameTxt;
    public TextField sNameTxt;
    @FXML
    public RadioButton rDifficult;
    public RadioButton rEasy;
    public Spinner <Integer> tPrioritySpinner;

    public Spinner <Double> tTimeTakenSpinner;

    public TextField tDetailsTxt;
    public TextField tDueDate;
    public TableView tasksTable;
    public boolean isItDifficult;
    public boolean isItEasy;
    public RadioButton sIsDifficult;
    public RadioButton sIsEasy;
    int priorityValue;
    double timeTaken;
    public Spinner <Integer> sPrioritySpinner;
    public Spinner <Double> sTimeTakenSpinner;
    public TextField sDetailsTxt;
    public TextField sDueDateTxt;
    public TableColumn<Task, String> tasksName = new TableColumn<>("Task");
    public TableColumn<Task, String> tasksPriority = new TableColumn<>("Priority");
    public TableColumn<Task, String> tasksDueDate = new TableColumn<>("Due Date");
    public TableColumn<Task, String> tasksDifficult = new TableColumn<>("Is it difficult?");
    public TableColumn<Task, String> tasksEasy = new TableColumn<>("Is it easy?");
    public ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    protected void viewPlannerBtn() throws IOException {
        StartApplication.setRoot("planner-view");
    }


    public void initialize() {
        LoadTasks(); // Load tasks from JSON
        setupTaskTable(); // Set up the taskTable with the loaded tasks

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,3);
        valueFactory.setValue(1);
        tPrioritySpinner.setValueFactory(valueFactory);

        SpinnerValueFactory<Double> valuesFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.3,10);
        valuesFactory.setValue(0.3);
        tTimeTakenSpinner.setValueFactory(valuesFactory);

        SpinnerValueFactory<Integer> sValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3);
        sValueFactory.setValue(1);
        sPrioritySpinner.setValueFactory(sValueFactory);

        SpinnerValueFactory<Double> sValuesFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.3, 10);
        sValuesFactory.setValue(0.3);
        sTimeTakenSpinner.setValueFactory(sValuesFactory);


        tasksName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTask()));
        tasksName.setPrefWidth(142);
        tasksPriority.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPriority())));
        tasksPriority.setPrefWidth(142);
        tasksDueDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));
        tasksDueDate.setPrefWidth(142);
        tasksDifficult.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isItDifficult())));
        tasksDifficult.setPrefWidth(142);
        tasksEasy.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().isItEasy())));
        tasksEasy.setPrefWidth(142);


        tasksTable.getColumns().add(tasksName);
        tasksTable.getColumns().add(tasksPriority);
        tasksTable.getColumns().add(tasksDueDate);
        tasksTable.getColumns().add(tasksDifficult);
        tasksTable.getColumns().add(tasksEasy);
        tasksTable.setItems(tasks);


    }

    private void setupTaskTable() {
        tasksTable.setItems(tasks);
    }

    public void LoadTasks() {
        /**
         * load tasks from saved file
         * open and read Json for any previous data
         */
        Gson gson = new Gson();
        try (Reader reader = new FileReader("tasks.json")) {
            //convert Json file to Java Object
            ArrayList<Task> imports = gson.fromJson(reader, new TypeToken<ArrayList<Task>>() {
            }.getType());
            tasks = FXCollections.observableArrayList(imports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addBtn(ActionEvent event) {

        String taskName = tNameTxt.getText().trim();
        String dueDate = tDueDate.getText().trim();
        String details = tDetailsTxt.getText().trim();

        if (taskName.isEmpty() || dueDate.isEmpty() || details.isEmpty()) {
            showAlert("Error", "Empty Fields", "Make sure everything is filled in correctly.");
            return;
        }
        
        int priorityValue = tPrioritySpinner.getValue();
        double timeTaken = tTimeTakenSpinner.getValue();

        Task newTask = new Task(tNameTxt.getText(), priorityValue, timeTaken, tDueDate.getText(), tDetailsTxt.getText(), isItDifficult, isItEasy);
        tasks.add(newTask);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("tasks.json")) {
            gson.toJson(tasks, writer);
            System.out.println("saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear input fields
        tNameTxt.clear();
        tPrioritySpinner.getValueFactory().setValue(1); // Reset spinner value
        tTimeTakenSpinner.getValueFactory().setValue(0.3); // Reset spinner value
        tDueDate.clear();
        tDetailsTxt.clear();
        rDifficult.setSelected(false);
        rEasy.setSelected(false);


    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    public void deleteTaskBtn(ActionEvent event) {
        Task selectedTask = (Task) tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);

            // Save updated tasks to JSON
            saveTasksToJson();

            // Clear input fields
            clearInputFields();

            System.out.println("Task deleted.");

            // Refresh the table
            tasksTable.refresh();
        } else {
            System.out.println("No task selected to delete.");
        }
    }


    public void searchBtn(ActionEvent event) {
        String searchName = sNameTxt.getText();

        for (Task task : tasks) {
            if (task.getTask().equals(searchName)) {
                sPrioritySpinner.getValueFactory().setValue(task.getPriority());
                sTimeTakenSpinner.getValueFactory().setValue(task.getTimeTaken());
                sDueDateTxt.setText(task.getDueDate());
                sDetailsTxt.setText(task.getDetails());

                if (task.isItDifficult()) {
                    sIsDifficult.setSelected(true);
                    sIsEasy.setSelected(false);
                } else if (task.isItEasy()) {
                    sIsDifficult.setSelected(false);
                    sIsEasy.setSelected(true);
                }

                // Exit the loop since the task is found
                return;
            }
        }

        // Clear fields if the task is not found
        clearInputFields();
    }

    private void clearInputFields() {
        sNameTxt.clear();
        sPrioritySpinner.getValueFactory().setValue(1); // Reset spinner value
        sTimeTakenSpinner.getValueFactory().setValue(0.3); // Reset spinner value
        sDueDateTxt.clear();
        sDetailsTxt.clear();
        sIsDifficult.setSelected(false);
        sIsEasy.setSelected(false);
    }



    public void editTaskBtn(ActionEvent event) {
        String searchName = sNameTxt.getText();

        for (Task task : tasks) {
            if (task.getTask().equals(searchName)) {
                task.setPriority(sPrioritySpinner.getValue()); // Use sPrioritySpinner
                task.setTimeTaken(sTimeTakenSpinner.getValue());                task.setDueDate(sDueDateTxt.getText());
                task.setDetails(sDetailsTxt.getText());

                if (task.isItDifficult()) {
                    sIsDifficult.setSelected(true);
                    sIsEasy.setSelected(false);
                } else if (task.isItEasy()) {
                    sIsDifficult.setSelected(false);
                    sIsEasy.setSelected(true);
                }

                // Save changes to JSON
                saveTasksToJson();

                // Clear input fields
                clearInputFields();

                // Reload tasks from JSON to update UI
                LoadTasks();
                tasksTable.refresh();

                System.out.println("Task edited and saved.");
                return; // Exit the loop since the task is found and edited
            }
        }

        System.out.println("Task not found for editing.");
    }

    public void saveTasksToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("tasks.json")) {
            gson.toJson(tasks, writer);
            System.out.println("Saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getDifficulty(ActionEvent event) {
        if(rDifficult.isSelected()){
            isItDifficult = true;
            isItEasy = false;
        } else {
            isItDifficult = false;
            isItEasy = true;
        }
    }
}