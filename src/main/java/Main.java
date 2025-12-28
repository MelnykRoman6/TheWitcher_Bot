import API.WitcherApiClient;
import config.*;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

        WitcherApiClient apiClient = new WitcherApiClient();



        String botToken = apikey; //your token here
        botsApplication.registerBot(botToken, new Bot(botToken));

        System.out.println("Bot successfully started!");
    }
}