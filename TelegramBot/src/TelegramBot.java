
//===============================================
//
// https://api.telegram.org/file/bot681723370:AAHP9Ewm_JwiePcGM9kyZGdGJ6hadXGpgV0/photos/file_0.jpg
//
//===============================================

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.Character.UnicodeScript;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {

	public enum Weather {
		SUN("?"), CLOUDS("?"), RAIN("?"), STORM("??"), LIGHT("??"), SNOW("??");
		String picture;
		private Weather(String picture) {
			this.picture = picture;
		}
	}

	@Override
	public void onUpdateReceived(Update message) {
		if (message.getMessage().hasText()) {
			switch(message.getMessage().getText()) {
			case "hey!": send(message, Parser.parserPage() + "\n");
				break;
			case "time": send(message, "You printed hey!");
				break;
			}
		}
		
	}
	
//===========================================================================
//                            Sending a text
//===========================================================================		
	
	public void send(Update message, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getMessage().getChatId());
		sendMessage.setText(text);
		sendMessage.setReplyMarkup(getKeyBoardMarkup());
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
//===========================================================================
//                          Downloading an image to the
//                          ???????????????????????????
//===========================================================================	

	public PhotoSize getPhoto(Update update) {
		if (update.hasMessage() && update.getMessage().hasPhoto()) {
			List<PhotoSize> photos = update.getMessage().getPhoto();
			return photos.stream()
					.sorted(Comparator.comparing(PhotoSize::getFileSize)
					.reversed())
					.findFirst().orElse(null);
		}
		return null;
	}
	
	public String getFilePath(PhotoSize photo) {
		java.util.Objects.requireNonNull(photo);
		
		if (photo.hasFilePath()) {
			return photo.getFilePath();
		} else {
			GetFile getFileMethod = new GetFile();
			getFileMethod.setFileId(photo.getFileId());
			try {
				org.telegram.telegrambots.meta.api.objects.File file = execute(getFileMethod);
				return file.getFilePath();
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public java.io.File downloadPhotoByFilePath(String filePath){
		try {
			System.out.println(filePath);
			return downloadFile(filePath);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
//===========================================================================
//                       Constructing a keyboard
//===========================================================================	
	
	public ReplyKeyboardMarkup getKeyBoardMarkup() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setOneTimeKeyboard(true);
		keyboardMarkup.setResizeKeyboard(true);
		List<KeyboardRow> keyboardRows = new ArrayList<>();
		KeyboardRow keyboardRow = new KeyboardRow();
		keyboardRow.add("hey!");
		keyboardRow.add("time");
		keyboardRows.add(keyboardRow);
		keyboardMarkup.setKeyboard(keyboardRows);
		return keyboardMarkup;
		
	}
	
//===========================================================================
//                       Three ways to send images
//===========================================================================	
	
	public void sendImageFromUrl(String url, Update chatId) {
		SendPhoto sendPhotoRequest = new SendPhoto();
		sendPhotoRequest.setChatId(chatId.getMessage().getChatId());
		sendPhotoRequest.setPhoto(url);
		sendPhotoRequest.setReplyMarkup(getKeyBoardMarkup());
		try {
			execute(sendPhotoRequest);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public void sendImageFromFileId(String fileId, String chatId) {
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(fileId);
		try {
			execute(sendPhoto);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	public void sendImageUploadingAFile(String filePath, String chatId) {
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(new java.io.File(filePath));
		try {
			execute(sendPhoto);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
//===========================================================================
//===========================================================================
	
	@Override
	public String getBotUsername() {
		return "@BegemotBotWithLongNameBot";
	}
	
	@Override
	public String getBotToken() {
		return "681723370:AAHP9Ewm_JwiePcGM9kyZGdGJ6hadXGpgV0";
	}
	
	public static void main(String[] args) {
		try {
			
            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

            TelegramBot bot = new TelegramBot();

            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

}





















