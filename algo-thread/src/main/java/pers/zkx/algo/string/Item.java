package pers.zkx.algo.string;

import lombok.Data;

@Data
public class Item {
    private final String f1;
    private final String f2;
    private final String f3;
    private String id;

    public Item(String f1, String f2, String f3) {
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
    }
}
