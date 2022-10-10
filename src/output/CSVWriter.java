package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class CSVWriter {
	private BufferedWriter bufWr;
	private String separator = ";";

	public boolean writeToCSV(LinkedList<LinkedList<String>> values, File file) throws IOException {
		bufWr = new BufferedWriter(new FileWriter(file));
		int count;
		for (LinkedList<String> user : values) {
			count = 0;
			for (String value : user) {
				if (value.length() == 0) {
					continue;
				}
				bufWr.write(value);
				if (count < user.size()) {
					bufWr.write(separator);
				}
				count++;
			}
			bufWr.newLine();
		}
		closeWriter();
		return true;
	}

	public void closeWriter() {
		if (bufWr != null) {
			try {
				bufWr.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
