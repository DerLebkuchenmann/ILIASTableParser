package main;

import gui.TPGui;

public class IliasTableParser {
	public static void main(String[] args) {
		if (System.getProperty("os.name") == "Mac OS X") {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ILIAS Table Parser");
		}
		TPGui gui = new TPGui();
		gui.setVisible(true);
	}
}
