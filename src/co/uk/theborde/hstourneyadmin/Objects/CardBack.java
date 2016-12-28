package co.uk.theborde.hstourneyadmin.Objects;

import co.uk.theborde.hstourneyadmin.Handlers.HSAPI;

import java.util.ArrayList;

/**
 * Created by easyr on 27/12/2016.
 */
public class CardBack {
    private int ID;
    private String name;
    private String description;
    private String source;
    private String sourceDescription;
    private boolean enabled;
    private String image;
    private String imageAnimated;
    private String sortCatagory;
    private String sortOrder;
    private String locale;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getSourceDescription() {
        return sourceDescription;
    }
    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImageAnimated() {
        return imageAnimated;
    }
    public void setImageAnimated(String imageAnimated) {
        this.imageAnimated = imageAnimated;
    }
    public String getSortCatagory() {
        return sortCatagory;
    }
    public void setSortCatagory(String sortCatagory) {
        this.sortCatagory = sortCatagory;
    }
    public String getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
