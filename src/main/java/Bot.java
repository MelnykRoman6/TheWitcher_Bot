import API.models.Armor;
import API.models.Item;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
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
        System.out.println("userid: " + userid);
        if (update.hasMessage() && update.getMessage().hasText()) {

            String text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String chatIdString = String.valueOf(chat_id);
            switch (text) {
                case "/start":
                    SendMessage welcome = new SendMessage(chatIdString, "---");
                    welcome.setReplyMarkup(getMainMenuKeyboard());
                    try { telegramClient.execute(welcome); } catch (Exception e) { e.printStackTrace(); }
                    break;

                case "⚔️Weapon":
                    btnText = "weapon";
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Insert weapon ID (ex., `1`):")
                            .build();
                    try {
                        telegramClient.execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "🛡️Armor":
                    btnText = "armor";
                    message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Insert armor ID (ex., `1`):")
                            .build();
                    try {
                        telegramClient.execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "👤Character":
                    btnText = "character";
                    message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Insert character ID (ex., `1`):")
                            .build();
                    try {
                        telegramClient.execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                case "🗺️Map":
                    btnText = "map";
                    message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Insert map name (ex., `Kaer Morhen`):")
                            .build();
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    if (text.matches("\\d+")) {
                        handleRequestCommand(chat_id, btnText + " " + text);
                    } else {
                        message = SendMessage
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
        else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            String message_text = "its a photo";
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasMessage() && update.getMessage().hasVoice()) {
            String message_text = "its a voice msg";
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasMessage() && update.getMessage().hasViaBot()) {
            String message_text = "its a msg forwarded from bot";
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasMessage() && update.getMessage().hasSticker()) {
            String message_text = "its a sticker";
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasMessage() && update.getMessage().hasAnimation()) {
            String message_text = "its a gif";
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            Animation animation = update.getMessage().getAnimation();
            String AniID = animation.getFileId();
            SendAnimation message2 = SendAnimation.builder()
                    .chatId(chat_id)
                    .animation(new InputFile(AniID))
                    .build();
            try {
                telegramClient.execute(message2); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



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
    private void handleRequestCommand(long chatId, String messageText) {
        String[] parts = messageText.split(" ");
        String chatIdString = String.valueOf(chatId);
        Item item = null;

        try {
            if (parts.length < 2) {
                SendMessage helpMessage = new SendMessage(chatIdString, "Пожалуйста, укажи ID. Пример: `" + parts[0] + " 1`");
                helpMessage.setParseMode("Markdown");
                telegramClient.execute(helpMessage);
                return;
            }

            String command = parts[0].toLowerCase().trim();
            int itemId = Integer.parseInt(parts[1].trim());

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
                    // item = apiClient.getCharacterById(itemId); // Когда добавишь в API
                    break;
            }
            System.out.println(item);
            if (item != null) {

                String baseApiUrl = "https://subaveragely-soupier-adella.ngrok-free.dev/";
                String fullImageUrl = baseApiUrl + item.getImageUrl().replace('\\', '/');

                byte[] imageBytes = apiClient.downloadImage(fullImageUrl);


                SendDocument sendDocument = SendDocument.builder()
                        .chatId(chatIdString)
                        .document(new InputFile(
                                new ByteArrayInputStream(imageBytes),
                                item.getName() + ".png" // Имя файла, которое увидит пользователь
                        ))
                        .caption(item.toString()) // Описание предмета (toString из Weapon или Armor)
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

}
