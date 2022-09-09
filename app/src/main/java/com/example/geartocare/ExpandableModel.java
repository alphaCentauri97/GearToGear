package com.example.geartocare;

public class ExpandableModel {

    private String title;
    private String desc;
    private boolean isVisible;

    public ExpandableModel(String title, String desc, boolean isVisible) {
        this.title = title;
        this.desc = desc;
        this.isVisible = isVisible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
