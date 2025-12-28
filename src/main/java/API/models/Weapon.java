package API.models;

import com.google.gson.annotations.SerializedName;

public class Weapon {

    private int id;

    private String name;

    private String category;

    private String type;

    private String damage;

    private String effects;

    private int weight;

    private int price;

    @SerializedName("imageUrl")
    private String imageUrl;

    public Weapon() {}

    @Override
    public String toString() {
        return "⚔️ *Weapon: " + name + "*\n" +
                "--- \n" +
                "Category: " + category + " (" + type + ")\n" +
                "Damage: " + damage + "\n" +
                "Effects: " + (effects != null ? effects : "No effects") + "\n" +
                "Weight: " + weight + "\n" +
                "Price: " + price + "💰";
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDamage() { return damage; }
    public String getEffects() { return effects; }
    public int getPrice() { return price; }
    // ... (и так далее для всех новых и старых полей)
    public String getImageUrl() {
        return imageUrl;
    }
}