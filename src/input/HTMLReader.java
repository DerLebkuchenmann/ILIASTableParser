package input;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLReader {
	
	private LinkedList<LinkedList<String>> storage = new LinkedList<>();
	
	private void readHTML(File file) throws IOException {
		Document doc = Jsoup.parse(file);
		Elements rows = doc.select("tr[class^=tblrow]");
		Elements values;
		for(Element row : rows) {
			values = row.select("td");
			storage.add(new LinkedList<String>());
			for(Element value : values) {
				storage.getLast().add(value.text());
			}
		}
	}
	
	public LinkedList<LinkedList<String>> getValues(File file) throws IOException {
		readHTML(file);
		return storage;
	}

}
