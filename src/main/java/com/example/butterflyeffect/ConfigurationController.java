package com.example.butterflyeffect;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
    public TextField tPriorityTxt;
    public TextField tTimeTakenTxt;
    public TextField tDetailsTxt;
    public TextField tDueDate;
    public TableView tasksTable;
    public boolean isItDifficult;
    public boolean isItEasy;
    public TableColumn<Task, String> tasksName=new TableColumn<>("Task");
    public TableColumn<Task, String> tasksPriority=new TableColumn<>("Priority");
    public TableColumn<Task, String> tasksDueDate=new TableColumn<>("Due Date");
    public TableColumn<Task, String> tasksDifficult=new TableColumn<>("Is it difficult?");
    public TableColumn<Task, String> tasksEasy=new TableColumn<>("Is it easy?");
    public ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() throws IOException {
        StartApplication.setRoot("planners-view");
    }


    public void initialize(){
        LoadTasks();

        tasksTable.getColumns().add(tasksName);
        tasksTable.getColumns().add(tasksPriority);
        tasksTable.getColumns().add(tasksDueDate);
        tasksTable.getColumns().add(tasksDifficult);
        tasksTable.getColumns().add(tasksEasy);
        tasksTable.setItems(tasks);
    }

    private void LoadTasks() {
        /**
         * load tasks from saved file
         * open and read Json for any previous data
         */
        Gson gson = new Gson();
        try(Reader reader = new FileReader("tasks.json")){
            //convert Json file to Java Object
            ArrayList<Task> imports = gson.fromJson(reader, new TypeToken<ArrayList<Task>>() {
            }.getType());
            tasks = FXCollections.observableArrayList(imports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void addBtn(ActionEvent event) {
        tasks.add(new Task(tNameTxt.getText(),Integer.parseInt(tPriorityTxt.getText()),Double.parseDouble(tTimeTakenTxt.getText()),Double.parseDouble(tasksDueDate.getText()), tDetailsTxt.getText(),isItDifficult, isItEasy));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter("tasks.json")){
            gson.toJson(tasks,writer);
            System.out.println("saved");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void difficultBtn(ActionEvent event) {
    }

    public void easyBtn(ActionEvent event) {
    }

    public void completedBtn(ActionEvent event) {
    }

    public void searchBtn(ActionEvent event) {
    }

    public void editTaskBtn(ActionEvent event) {
    }
}