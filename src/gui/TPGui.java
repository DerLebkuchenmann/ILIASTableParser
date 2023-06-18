package gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import input.HTMLReader;
import output.CSVWriter;

public class TPGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container c;
	private JPanel base, inputPanel, outputPanel;
	private JFileChooser openFileChooser, saveFileChooser;
	private FileNameExtensionFilter inputFileFilter, outputFileFilter;
	private JButton openFileBtn, saveFileBtn, convert;
	private JTextField openPath, savePath;
	private HTMLReader reader;
	private CSVWriter writer;
	private JScrollPane openScroll, saveScroll;
	private JScrollBar horizontalScrollBar, verticalScrollBar;

	public TPGui() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		createWindow();
		reader = new HTMLReader();
		writer = new CSVWriter();
		this.setTitle("ILIAS Table Parser");

	}

	private void createWindow() {
		c = this.getContentPane();
		base = new JPanel();
		base.setLayout(new GridLayout(0, 1));
		openFileChooser = new JFileChooser();
		saveFileChooser = new JFileChooser();
		openPath = new JTextField();
		savePath = new JTextField();
		inputPanel = new JPanel();
		openFileBtn = new JButton("Select HTML file");
		saveFileBtn = new JButton("Select destination path");
		convert = new JButton("Convert");
		inputPanel.setLayout(new GridLayout());
		inputPanel.add(openFileBtn);
		openScroll = new JScrollPane(openPath);
		openScroll.setAutoscrolls(true);
		inputPanel.add(openScroll);
		outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout());
		outputPanel.add(saveFileBtn);
		saveScroll = new JScrollPane(savePath);
		saveScroll.setAutoscrolls(true);
		outputPanel.add(saveScroll);
		inputFileFilter = new FileNameExtensionFilter("HTML", "htm", "html");
		openFileChooser.setFileFilter(inputFileFilter);
		outputFileFilter = new FileNameExtensionFilter("CSV", "csv");
		saveFileChooser.setFileFilter(outputFileFilter);

		c.add(base);
		base.add(inputPanel);
		base.add(outputPanel);
		base.add(convert);

		setActions();
		this.pack();
	}

	private void setActions() {
		openFileBtn.addActionListener(e -> {
			openFileChooser.setCurrentDirectory(new File(openPath.getText()));
			int returnVal = openFileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				verticalScrollBar = openScroll.getHorizontalScrollBar();
				openPath.setText(openFileChooser.getSelectedFile().getAbsolutePath());
				verticalScrollBar.setValue(verticalScrollBar.getMaximum());
				//this.pack();
			}
		});
		saveFileBtn.addActionListener(e -> {
			saveFileChooser.setCurrentDirectory(new File(savePath.getText()));
			int returnVal = saveFileChooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String destination = saveFileChooser.getSelectedFile().getAbsolutePath();
				if(saveFileChooser.getFileFilter().equals(outputFileFilter) && !destination.endsWith("csv")) {
					destination += ".csv";
				}
				savePath.setText(destination);
				horizontalScrollBar = saveScroll.getHorizontalScrollBar();
				horizontalScrollBar.setValue(horizontalScrollBar.getMaximum());
				//this.pack();
			}
		});
		convert.addActionListener(e -> {
			File outputFile = new File(savePath.getText());
			File inputFile = new File(openPath.getText());
			if (outputFile.exists()) {
				int selection = JOptionPane.showConfirmDialog(this,
						"File " + outputFile.getName() + " already exists\nThe file will be overwritten!",
						"File already exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (selection != JOptionPane.OK_OPTION) {
					return;
				}
			}
			try {
				boolean success = writer.writeToCSV(reader.getValues(inputFile), outputFile);
				if (success) {
					JOptionPane.showMessageDialog(this, outputFile.getAbsolutePath() + " was successfully created",
							"Success", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, e1.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		});

		setWindowClosingOps();

	}

	private void setWindowClosingOps() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				writer.closeWriter();
				System.exit(0);
			}
		});

	}
}
