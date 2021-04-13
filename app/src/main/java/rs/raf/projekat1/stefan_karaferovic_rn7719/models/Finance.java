package rs.raf.projekat1.stefan_karaferovic_rn7719.models;

import java.io.File;
import java.io.Serializable;

public class Finance implements Serializable {
    private int id;
    private String title;
    private int amount;
    //    private File audio;
    private Object description;

    public Finance(int id, String title, int amount, Object description) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }
}
