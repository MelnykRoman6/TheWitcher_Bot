//not finished



package API.models;

public class Monster implements Item {
    private int id;
    private String name;
    private String monsterClass;
    private String occurrence;
    private String vulnerableTo;
    private String imageUrl;

    public Monster() {}

    @Override public int getId() { return id; }
    @Override public String getName() { return name; }
    @Override public String getImageUrl() { return imageUrl; }

    @Override
    public String toString() {
        return "👹 <b>Monster: " + name + "</b>\n" +
                "--- \n" +
                "<b>Class:</b> " + monsterClass + "\n" +
                "<b>Occurrence:</b> " + (occurrence != null ? occurrence : "Unknown") + "\n" +
                "<b>Vulnerable to:</b> <i>" + (vulnerableTo != null ? vulnerableTo : "Nothing") + "</i>\n" +
                "<b>ID:</b> " + id;
    }
}