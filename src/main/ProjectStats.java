package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ProjectStats {

	int lines = 0;
	int characters = 0;
	int files = 0;
	int directories = -1;
	int words = 0;

	public static void main(String[] args) {
		new ProjectStats();
	}

	public ProjectStats() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		String path = null;

		JFileChooser chooser = new JFileChooser();
		JButton open = new JButton();
		chooser.setFileSelectionMode(JFileChooser.APPROVE_OPTION);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
		}
		if (path == null)
			System.exit(0);

		System.out.println("Scanning files...");
		directory(new File(path));

		System.out.println("-----------------------------");
		System.out.println(directories + " directories");
		System.out.println(files + " files");
		System.out.println(lines + " lines");
		System.out.println(words + " words");
		System.out.println(characters + " characters");
		System.out.println("-----------------------------");
	}

	private void directory(File file) {
		if (file.isDirectory()) {
			directories++;
			for (File f : file.listFiles()) {
				directory(f);
			}
		} else {
			if (file.getName().endsWith(".java")) {
				files++;
				try {

					BufferedReader reader = new BufferedReader(new FileReader(file));

					String line;
					while ((line = reader.readLine()) != null) {
						lines++;
						characters += line.length();
						for (String s : line.split("[ ().:;{}\"]")) {
							if (s.length() > 1)
								words++;
						}
					}

					reader.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
