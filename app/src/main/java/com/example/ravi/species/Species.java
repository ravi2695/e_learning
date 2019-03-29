package com.example.ravi.species;

public class Species {
    private String Name,Image,Description,MenuId,Preage,middleage,recentage,VideoPlay;

    public Species() {
    }

    public Species(String name, String image, String description, String menuId, String preage, String middleage, String recentage, String videoPlay) {
        Name = name;
        Image = image;
        Description = description;
        MenuId = menuId;
        Preage = preage;
        this.middleage = middleage;
        this.recentage = recentage;
        VideoPlay = videoPlay;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getPreage() {
        return Preage;
    }

    public void setPreage(String preage) {
        Preage = preage;
    }

    public String getMiddleage() {
        return middleage;
    }

    public void setMiddleage(String middleage) {
        this.middleage = middleage;
    }

    public String getRecentage() {
        return recentage;
    }

    public void setRecentage(String recentage) {
        this.recentage = recentage;
    }

    public String getVideoPlay() {
        return VideoPlay;
    }

    public void setVideoPlay(String videoPlay) {
        VideoPlay = videoPlay;
    }
}
