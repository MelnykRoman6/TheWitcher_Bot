package API.models;

import com.google.gson.annotations.SerializedName;

public class Weapon implements Item {

    private int id;

    private String name;

    private String category;

    private String type;

    private String damage;

    private String effects;

    private float weight;

    private String price;

    @SerializedName("imageUrl")
    private String imageUrl;

    public Weapon() {}

    @Override
    public String toString() {
        return "⚔️Weapon: " + name + "\n" +
                "--- \n" +
                "Category: " + category + " (" + type + ")\n" +
                "Damage: " + damage + "\n" +
                "Effects: " + (effects != null ? effects : "No effects") + "\n" +
                "Weight: " + weight + "\n" +
                price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDamage() { return damage; }
    public String getEffects() { return effects; }
    public String getPrice() { return price; }
    public String getImageUrl() {
        return imageUrl;
    }
}