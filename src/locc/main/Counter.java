package locc.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Counter {

	private static StringBuilder doc;
	
	public static void counter(JFrame frame, File[] files, boolean countWhitespace, ExtensionWrapper ext, JTextArea textArea) {
		doc = new StringBuilder();
		if(ext == null) {
			JOptionPane.showMessageDialog(frame, "Please select an extension filter", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String[] extensions = ext.getExtensions();
		int totalLines = 0;
		for(int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()) {
				doc.append(files[i].getName() + "\n");
				totalLines += directory(files[i], extensions, countWhitespace, 0);
			}
			else {
				totalLines += singleFile(files[i], extensions, countWhitespace, 0);
			}
		}
		doc.append("\nTotal Lines: \t" + totalLines);
		textArea.setText(doc.toString());
	}
	
	private static int singleFile(File file, String[] extensions, boolean countWhitespace, int tabs) {
		int lines = 0;
		for(int e = 0; e < extensions.length; e++) {
			if(file.getName().endsWith(extensions[e])) {
				lines = countLines(file.getAbsolutePath(), countWhitespace);
				for(int i = 0; i < tabs; i++)
					doc.append("     ->");
				doc.append(file.getName() + " - " + lines + "\n");
			}
		}
		return lines;
	}
	
	private static int directory(File file, String[] extensions, boolean countWhitespace, int tabs) {
		tabs++;
		File[] contents = file.listFiles();
		int lines = 0;
		for(int i = 0; i < contents.length; i++) {
			if(contents[i].isDirectory()) {
				for(int ts = 0; ts < tabs; ts++)
					doc.append("     ->");
				doc.append(contents[i].getName() + "\n");
				lines += directory(contents[i], extensions, countWhitespace, tabs);
			}
			else {
				lines += singleFile(contents[i], extensions, countWhitespace, tabs);
			}
		}
		return lines;
	}
	
	private static int countLines(String filePath, boolean countWhitespace) {
		BufferedReader reader = null;
		int lines = 0;
		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));
			String line = "";
			while((line = reader.readLine()) != null) {
				if(countWhitespace) {
					lines++;
				}
				else {
					if(line.matches(".*\\w.*"))
						lines++;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(reader != null)
					reader.close();
			} catch(Exception e) {}
		}
		return lines;
	}
	
}
