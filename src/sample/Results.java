package sample;

import java.io.Serializable;

public class Results implements Serializable {
    private int id;
    private  String name;
    private int Math;
    private int Chinese;
    private int English;
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public int getChinese() {
        return Chinese;
    }

    public int getEnglish() {
        return English;
    }

    public int getMath() {
        return Math;
    }

    public void setChinese(int chinese) {
        Chinese = chinese;
    }

    public void setEnglish(int english) {
        English = english;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMath(int math) {
        Math = math;
    }

    public void setName(String name) {
        this.name = name;
    }
}
