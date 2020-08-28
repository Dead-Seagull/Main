import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	
	public static void main(String[] args) {
		parserPage();
	}
	
	public static String parserPage() {
		try {
			
			URL url = new URL("https://astro-world.ru/astronomicheskie-sobytiya-v-avguste-2020-goda/");
			Document doc = Jsoup.parse(url, 10000);
			Elements P_elements = doc.select("p");            // All "p" tags
			List<String> P_text = P_elements.eachText();      // Taking text from them
			Iterator<String> iterator = P_text.iterator();
			while(iterator.hasNext()) {
				if(!Pattern.matches("^[1-9][0-9]*?\\..+", iterator.next())) {
					iterator.remove();
				}
			}
			//for (String s : P_text) {
				//System.out.println(s);
			//}
			for (String ss : P_text) {	
				String[] words = ss.split(" ");
				if (words[0].equals(generateDate())) {
					System.out.println(ss);
					return ss;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String generateDate() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("d.MM");
		String dateFinal = format.format(date);
		//System.out.println(dateFinal);
		return dateFinal;
	}
	
}
