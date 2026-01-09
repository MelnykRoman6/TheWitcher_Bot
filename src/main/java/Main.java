import API.WitcherApiClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        WitcherApiClient apiClient = new WitcherApiClient();

        Properties prop = new Properties();
        String botToken = "";
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {

                System.out.println("⚠️ Config not found in JAR, searching in local folder...");
                try (InputStream fileInput = new java.io.FileInputStream("config.properties")) {
                    prop.load(fileInput);
                } catch (IOException e) {
                    System.out.println("❌ ERROR: config.properties not found anywhere!");
                    return;
                }
            } else {
                prop.load(input);
            }
            System.out.println("prop loaded");
            prop.load(input);

            botToken = prop.getProperty("bot.token");
            System.out.println("bot token: " + botToken);
        } catch (IOException ex) {
            System.out.println("Error loading config file");
            ex.printStackTrace();
        }
        botsApplication.registerBot(botToken, new Bot(botToken));

        System.out.println("Bot successfully started!");
    }
}