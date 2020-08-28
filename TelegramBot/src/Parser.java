import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Parser {
	
	public static void main(String[] args) {
		parserPage();
	}
	
	public static String parserPage() {
		try {
			
			URL url = new URL("https://astro-world.ru/astronomicheskij-kalendar-avgust-2019/");
			Document doc = Jsoup.parse(url, 10000);
			Elements P_elements = doc.select("p");            // All "p" tags
			List<String> P_text = P_elements.eachText();      // Taking text from them
			Iterator<String> iterator = P_text.iterator();
			while(iterator.hasNext()) {
				if(!Pattern.matches("^[1-9][0-9]*?\\..+", iterator.next())) {
					iterator.remove();
				}
			}
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
		SimpleDateFormat format = new SimpleDateFormat("d");
		return format.format(date);
	}
	
}
