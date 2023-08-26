package com.example.butterflyeffect;

import java.time.LocalDate;

public class Task {

    String task;
    int priority;
    double timeTaken;
    double dueDate;
    String details;
    boolean isItDifficult;

    boolean isItEasy;

    public Task(String task, int priority, double timeTaken, double dueDate, String details, boolean isItDifficult, boolean isItEasy) {
        this.task = task;
        this.priority = priority;
        this.timeTaken = timeTaken;
        this.dueDate = dueDate;
        this.details = details;
        this.isItDifficult = isItDifficult;
        this.isItEasy = isItEasy;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

    public double getDueDate() {
        return dueDate;
    }

    public void setDueDate(double dueDate) {
        this.dueDate = dueDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isItDifficult() {
        return isItDifficult;
    }

    public void setItDifficult(boolean itDifficult) {
        isItDifficult = itDifficult;
    }

    public boolean isItEasy() {
        return isItEasy;
    }

    public void setItEasy(boolean itEasy) {
        isItEasy = itEasy;
    }
}

