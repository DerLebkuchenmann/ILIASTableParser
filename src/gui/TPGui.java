package gui;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import input.HTMLReader;
import output.CSVWriter;

public class TPGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container c;
	private JPanel base;
	private JFileChooser fileChooser;
	private FileFilter fileFilter;
	private JButton openFile;
	private HTMLReader reader;
	private CSVWriter writer;
	private File output;

	public TPGui() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		createWindow();
		reader = new HTMLReader();
		writer = new CSVWriter();

	}

	private void createWindow() {
		c = this.getContentPane();
		base = new JPanel();
		fileChooser = new JFileChooser();
		fileFilter = new FileFilter() {
			
			@Override
			public String getDescription() {
				return "HTML";
			}
			
			@Override
			public boolean accept(File f) {
				return (f.getName().matches(".+\\.htm.*"));
			}
		};
		fileChooser.setFileFilter(fileFilter);
		openFile = new JButton("Open HTML file");
		c.add(base);
		base.add(openFile);
		setActions();
	}

	private void setActions() {
		openFile.addActionListener(e -> {
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					output = new File(file.getParentFile(), "results.csv");
					boolean success = writer.writeToCSV(reader.getValues(file), output);
					if (success) {
						JOptionPane.showMessageDialog(this, output.getAbsolutePath() + " was successfully created",
								"Success", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		setWindowClosingOps();
		this.pack();

	}

	private void setWindowClosingOps() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				writer.closeWriter();
			}
		});

	}
}
