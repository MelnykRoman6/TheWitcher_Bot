import API.WitcherApiClient;
import lombok.Value;
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
                System.out.println("");
                return;
            }

            prop.load(input);

            botToken = prop.getProperty("bot.token");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        botsApplication.registerBot(botToken, new Bot(botToken));

        System.out.println("Bot successfully started!");
    }
}