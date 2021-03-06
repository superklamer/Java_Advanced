package com.knockknock.message.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JOptionPane;



public class MessageReader {
	
	private File file;

	public MessageReader(ResponseFiles responseFile) {
		getFilePath(responseFile);

	}

	private final void getFilePath(ResponseFiles fileToGet) {
		String packagePath = "/com/knockknock/message";
		
		try {
		if (fileToGet == ResponseFiles.CLUES)
			file = new File(getClass().getResource(String.format("%s/clues.txt", packagePath)).getPath());
		else if (fileToGet == ResponseFiles.ANSWERS)
			file = new File(getClass().getResource(String.format("%s/answers.txt", packagePath)).getPath());
		} catch (NullPointerException e) {
			javax.swing.JOptionPane.showMessageDialog(null, "Jokes Files Missing", "File Missing", JOptionPane.ERROR_MESSAGE); 
		}
	}


	public List<String> readFile() {
		List<String> output = new ArrayList<String>();
		
		try (FileInputStream fis = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
			
			String line = reader.readLine();
			output.add(line);
			
			while (line != null) {
				output.add(line);
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(null, "Cannot jokes file(s)", "Read Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return output;
	}
}
		
		


