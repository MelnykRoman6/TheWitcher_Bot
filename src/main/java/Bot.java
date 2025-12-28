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

    public Bot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        long userid = update.getMessage().getFrom().getId();
        System.out.println("userid: " + userid);
        String btnText = "";
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String chatIdString = String.valueOf(chat_id);
            switch (text) {
                case "/start":
                    SendMessage welcome = new SendMessage(chatIdString, "Привет, ведьмак! Выбери раздел:");
                    welcome.setReplyMarkup(getMainMenuKeyboard());
                    try { telegramClient.execute(welcome); } catch (Exception e) { e.printStackTrace(); }
                    break;

                case "⚔️Weapon":
                    btnText = "weapon";
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Введи ID оружия (например, `1`):")
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
                            .text("Введи ID брони (например, `1`):")
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
                            .text("Введи ID персонажа (например, `1`):")
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
                            .text("Введи название карты (например, `Каэр Морхен`):")
                            .build();
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    if (text.matches("\\d+")) {
                        handleRequestCommand(chat_id, btnText + text);
                    } else {
                        message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text("Используй кнопки меню или введи ID")
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

        try {
            if (parts.length < 2) {
                SendMessage helpMessage = new SendMessage(chatIdString, "Please specify" + parts[0] + " id. Example: `/weapon 1`");
                helpMessage.setParseMode("Markdown");
                telegramClient.execute(helpMessage);
                return;
            }

            int itemId = Integer.parseInt(parts[1].trim());
            String itemType = parts[0].trim();



            if (item != null) {
                String relativePath = item.getImageUrl().replace('\\', '/');
                String baseApiUrl = "https://subaveragely-soupier-adella.ngrok-free.dev/";
                String fullImageUrl = baseApiUrl + relativePath;

                byte[] imageBytes = apiClient.downloadImage(fullImageUrl);

                SendDocument sendDocument = SendDocument.builder()
                        .chatId(chatIdString)
                        .document(new InputFile(
                                new ByteArrayInputStream(imageBytes),
                                item.getName() + ".png" // Обязательно .png или .jpg для сохранения типа
                        ))

                        // Документы поддерживают подпись (caption)
                        .parseMode("Markdown")
                        .caption(item.toString())
                        .build();

                // Отправляем документ
                telegramClient.execute(sendDocument);

            } else {
                SendMessage notFoundMessage = new SendMessage(chatIdString, "❌"+ itemType +" with ID " + itemId + " not found.");
                telegramClient.execute(notFoundMessage);
            }

        } catch (NumberFormatException e) {

        } catch (IOException e) {
            SendMessage apiErrorMessage = new SendMessage(chatIdString, "🛑Error during connection to Witcher API or downloading image. Make sure that API and Ngrok are running.");
            try { telegramClient.execute(apiErrorMessage); } catch (TelegramApiException ignored) {}
            e.printStackTrace();
        } catch (TelegramApiException ignored) {
        }
    }

}
