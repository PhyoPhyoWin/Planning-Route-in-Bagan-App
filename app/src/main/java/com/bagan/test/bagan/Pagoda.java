package com.bagan.test.bagan;

public class Pagoda {

    String pagodaName = null;
    String name = null;
    boolean selected = false;

    public Pagoda(String name, String pagodaName, boolean selected) {
        super();
        this.name = name;
        this.pagodaName = pagodaName;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPagodaName() {
        return pagodaName;
    }
    public void setCode(String pagodaName) {
        this.pagodaName = pagodaName;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}



