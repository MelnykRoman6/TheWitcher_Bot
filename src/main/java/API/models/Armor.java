package API.models;

public class Armor implements Item {
    private int id;
    private String name;
    private String type;
    private String category;
    private float weight;
    private int price;
    private String imageUrl;

    public Armor() {}

    // Реализация методов интерфейса Item
    @Override public int getId() { return id; }
    @Override public String getName() { return name; }
    @Override public String getImageUrl() { return imageUrl; }

    // Красивый вывод для Telegram
    @Override
    public String toString() {
        return "🛡️Armor: " + name + "\n" +
                "--- \n" +
                "Type: " + type + "\n" +
                "Category: " + category + "\n" +
                "Weight: " + weight + "\n" +
                "Price: " + price + " 💰";
    }

    public String getType() { return type; }
    public String getCategory() { return category; }
    public float getWeight() { return weight; }
    public int getPrice() { return price; }
}