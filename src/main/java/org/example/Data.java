package org.example;

public class Data {
    private Long id;
    private String uuid;
    private String letter;

    public Data(Long id, String uuid, String letter) {
        this.id = id;
        this.uuid = uuid;
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", letter='" + letter + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }
}
