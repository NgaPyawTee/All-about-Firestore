package com.homework.firsestoreallinone;

public class Note {
    private String title;
    private String description;
    private String ID;
    private int priority;

    public Note(){

    }

    public Note(String title, String description,int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}


