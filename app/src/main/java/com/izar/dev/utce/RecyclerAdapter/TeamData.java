package com.izar.dev.utce.RecyclerAdapter;

public class TeamData {
    private String name;
    private String thumbnail;
    private String numOfScore;

    public TeamData(){

    }
    public TeamData(String name, String thumbnail,String numOfScore){
        this.name =name;
        this.thumbnail = thumbnail;
        this.numOfScore = numOfScore;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getthumbnail() {
        return thumbnail;
    }

    public void setthumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getnumOfScore() {
        return numOfScore;
    }

    public void setnumOfScore(String numOfScore) {
        this.numOfScore = numOfScore;
    }
}
