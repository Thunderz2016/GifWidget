import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Combo;

public class FileManager {

    public void loadHistory(Combo combo) {
		try {
			Scanner readHistory = new Scanner(new File("history.dat"));
			while (readHistory.hasNextLine()) {
				combo.add(readHistory.nextLine());
			}
			readHistory.close();
		} catch (FileNotFoundException e) {
			System.out.println("\"history.dat\" not found. Creating new file.");
		}
	}

	public void saveHistory(Combo combo) {
		try {
			FileWriter writeHistory = new FileWriter(new File("history.dat"), false);
			for (int i = 0; i < combo.getItemCount(); i++) {
				writeHistory.write(combo.getItem(i) + "\n");
			}
			writeHistory.close();
		} catch (IOException e) {
			Widget.dialogBox(
					"An IOException has occurred while saving path history to \"history.dat\", please check file permissions.",
					"Error", 200, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public Config loadConfig() {
		try {
			Scanner readConfig = new Scanner(new File("config.dat"));
			String[] tokens = readConfig.nextLine().split("\\|");

			String imagePath = tokens[0];
			int imageSize = Integer.parseInt(tokens[1]);
			boolean borderless = Boolean.parseBoolean(tokens[2]);
			boolean alwaysOnTop = Boolean.parseBoolean(tokens[3]);
			boolean clickThrough = Boolean.parseBoolean(tokens[4]);

			readConfig.close();

			return new Config(imagePath, imageSize, borderless, alwaysOnTop, clickThrough);

		} catch (FileNotFoundException | NoSuchElementException e) {
			return new Config();
		}
	}

	public void saveConfig(Config config) {
		try {
			FileWriter writeConfig = new FileWriter(new File("config.dat"), false);
			writeConfig.write(config.toString());
			writeConfig.close();
		} catch (IOException e) {
			Widget.dialogBox(
					"An IOException has occurred while saving path history to \"config.dat\", please check file permissions.",
					"Error", 200, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
