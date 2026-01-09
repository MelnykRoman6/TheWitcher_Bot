package API.models;

public class Character implements Item {
    private int id;
    private String name;
    private String alias;
    private String race;
    private String gender;
    private String nationality;
    private String eyeColor;
    private String hairColor;
    private String imageUrl;
    private String description;

    public Character() {}

    @Override public int getId() { return id; }
    @Override public String getName() { return name; }
    @Override public String getImageUrl() { return imageUrl; }


    @Override
    public String toString() {
        return "👤Character: " + name + "\n" +
                (alias != null ? "Also known as " + alias + "\n" : "") +
                "------------------\n" +
                "Race: " + race + "\n" +
                "Sex: " + gender + "\n" +
                "Nationality: " + nationality + "\n" +
                "Eye color: " + eyeColor + "\n" +
                "Hair color: " + hairColor + "\n" +
                "Description: " + description;
    }

    public String getDesc() { return description; }
    public String getAlias() { return alias; }
    public String getRace() { return race; }
    public String getGender() { return gender; }
}