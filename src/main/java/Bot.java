import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import API.WitcherApiClient;
import API.models.Weapon;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (text.contains("weapon")){
                handleWeaponCommand(chat_id, update.getMessage().getText());
            }
            else {
                String message_text = "unknown pattern";
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
    private void handleWeaponCommand(long chatId, String messageText) {

        String[] parts = messageText.split(" ");
        String chatIdString = String.valueOf(chatId);

        try {
            if (parts.length < 2) {
                SendMessage helpMessage = new SendMessage(chatIdString, "Please specify weapon id. Example: `/weapon 1`");
                helpMessage.setParseMode("Markdown");
                telegramClient.execute(helpMessage);
                return;

            }

            int weaponId = Integer.parseInt(parts[1].trim());

            Weapon weapon = apiClient.getWeaponById(weaponId);

            if (weapon != null) {
                String relativePath = weapon.getImageUrl().replace('\\', '/');
                String baseApiUrl = "https://subaveragely-soupier-adella.ngrok-free.dev/";
                String fullImageUrl = baseApiUrl + relativePath;

                byte[] imageBytes = apiClient.downloadImage(fullImageUrl);

                SendDocument sendDocument = SendDocument.builder()
                        .chatId(chatIdString)
                        .document(new InputFile(
                                new ByteArrayInputStream(imageBytes),
                                weapon.getName() + ".png" // Обязательно .png или .jpg для сохранения типа
                        ))

                        // Документы поддерживают подпись (caption)
                        .parseMode("Markdown")
                        .caption(weapon.toString())
                        .build();

                // Отправляем документ
                telegramClient.execute(sendDocument);

            } else {
                SendMessage notFoundMessage = new SendMessage(chatIdString, "❌ Weapon with ID " + weaponId + " not found.");
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
