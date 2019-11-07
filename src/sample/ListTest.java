package sample;

import java.io.Serializable;
import java.util.List;

public class ListTest implements Serializable {
    private List<Results> list;

    public List<Results> getList() {
        return list;
    }

    public void setList(List<Results> list) {
        this.list = list;
    }
}
