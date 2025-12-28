import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ОШИБКА: Не удалось найти config.properties!");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}