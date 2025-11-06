package pers.zkx.algo.string;

import lombok.Data;

@Data
public class Item {
    private final String f1;
    private final Integer f2;
    private String id;

    public Item(String f1, Integer f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    // 新增：使用 String.format 构造 id
    public void buildIdByFormat() {
        this.id = String.format("%s-%d", this.f1, this.f2);
    }

    // 新增：使用 + 拼接构造 id
    public void buildIdByPlus() {
        this.id = this.f1 + "-" + this.f2;
    }

    // 新增：使用 StringBuilder 构造 id（与 + 操作效果接近，但更显式）
    public void buildIdByBuilder() {
        this.id = new StringBuilder(16)
                .append(this.f1)
                .append('-')
                .append(this.f2)
                .toString();
    }
}
