import API.models.Armor;
import API.models.Item;
import API.models.Monster;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import API.WitcherApiClient;
import API.models.Weapon;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final WitcherApiClient apiClient = new WitcherApiClient();
    String btnText = "";

    public Bot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        long userid = update.getMessage().getFrom().getId();
        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String chatIdString = String.valueOf(chat_id);
            SendMessage welcome = new SendMessage(chatIdString, "---");
            welcome.setReplyMarkup(getMainMenuKeyboard()); //sets the buttons
            //switchcase for all buttons implemented so far
            switch (text) {
                case "/start":
                    welcome = new SendMessage(chatIdString, "---");
                    welcome.setReplyMarkup(getMainMenuKeyboard());
                    try {
                        telegramClient.execute(welcome);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "⚔️Weapon":
                    btnText = "weapon";
                    getList(chat_id, btnText);
                    break;

                case "🛡️Armor":
                    btnText = "armor";
                    getList(chat_id, btnText);
                    break;

                case "👤Character":
                    btnText = "character";
                    getList(chat_id, btnText);
                    break;

                case "🗺️Map":
                    btnText = "map";
                    sendMap(chat_id);
                    break;

                default: //if it is not a text from button bot assumes its id requested
                    if (text.matches("\\d+")) {
                        handleRequestCommand(chat_id, btnText + " " + text);
                    } else { //and otherwise sends this message
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text("Use buttons or insert an ID")
                                .build();
                        try {
                            telegramClient.execute(message); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }
    //buttons
    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("⚔️Weapon");
        row1.add("🛡️Armor");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("👤Character");
        row2.add("🗺️Map");

        keyboard.add(row1);
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
    //map
    public void sendMap(Long chatId) {
        SendMessage message = SendMessage
                .builder()
                .chatId(chatId.toString())
                .text("Web map")
                .build();
        String url = "https://subaveragely-soupier-adella.ngrok-free.dev/index.html ";

        WebAppInfo webAppInfo = WebAppInfo.builder()
                .url(url)
                .build();

        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Open map🗺️")
                .webApp(webAppInfo)
                .build();

        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(button);

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();

        message.setReplyMarkup(markup);

        try {
            telegramClient.execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //function for requests
    private void handleRequestCommand(long chatId, String messageText) {
        String[] parts = messageText.split(" ");
        String chatIdString = String.valueOf(chatId);
        Item item = null;

        try {
            if (parts.length < 2) {
                SendMessage helpMessage = new SendMessage(chatIdString, "Please insert an id. Example: `" + parts[0] + " 1`");
                helpMessage.setParseMode("Markdown");
                telegramClient.execute(helpMessage);
                return;
            }

            String command = parts[0].toLowerCase().trim();
            int itemId = Integer.parseInt(parts[1].trim());
            //switchcase that creates a request in base of the item requested
            switch (command) {
                case "оружие":
                case "weapon":
                    item = apiClient.getItemById(itemId, "Weapon", Weapon.class);
                    break;
                case "броня":
                case "armor":
                    item = apiClient.getItemById(itemId, "Armor", Armor.class);
                    break;
                case "персонаж":
                case "character":
                    item = apiClient.getItemById(itemId, "Character", API.models.Character.class);
                    break;
            }
            System.out.println(item);
            if (item != null) {

                String baseApiUrl = "https://subaveragely-soupier-adella.ngrok-free.dev/";
                String fullImageUrl = baseApiUrl + item.getImageUrl().replace('\\', '/');

                byte[] imageBytes = apiClient.downloadImage(fullImageUrl);

                //document because image looks better
                SendDocument sendDocument = SendDocument.builder()
                        .chatId(chatIdString)
                        .document(new InputFile(
                                new ByteArrayInputStream(imageBytes),
                                item.getName() + ".png"
                        ))
                        .caption(item.toString())
                        .parseMode("Markdown")
                        .build();

                telegramClient.execute(sendDocument);

            } else {
                SendMessage notFoundMessage = new SendMessage(chatIdString, "❌Class object " + command + " with ID " + itemId + " not found.");
                telegramClient.execute(notFoundMessage);
            }

        } catch (NumberFormatException e) {
            SendMessage errmsg = new SendMessage(chatIdString, "⚠️ ID must be a number.");
        } catch (IOException e) {
            SendMessage apiErrorMessage = new SendMessage(chatIdString, "🛑Error during connection to Witcher API or downloading image. Make sure that API and Ngrok are running.");
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    //function to request the list of items existent in db as soon as user clicks button
    private void getList(long chatId, String itemType) {
        try {
            List<? extends Item> items = apiClient.getItemsList(itemType, getModelClass(itemType));

            if (items == null || items.isEmpty()) {
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text("List is empty")
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return;
            }

            StringBuilder message = new StringBuilder("<b>📜Select " + itemType + ":</b>\n\n");
            for (Item item : items) {

                message.append("<code>").append(item.getId()).append("</code>. ")
                        .append(item.getName()).append("\n");
            }

            SendMessage sm = new SendMessage(String.valueOf(chatId), message.toString());
            sm.setParseMode("HTML");
            telegramClient.execute(sm);

        } catch (IOException | TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private Class<? extends Item> getModelClass(String type) {
        switch (type.toLowerCase()) {
            case "armor": return Armor.class;
            case "character": return API.models.Character.class;
            case "monster": return Monster.class;
            default: return Weapon.class;
        }
    }
}
