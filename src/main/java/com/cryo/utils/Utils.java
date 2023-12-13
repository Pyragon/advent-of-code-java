package com.cryo.utils;

import com.cryo.Main;
import lombok.Cleanup;

import java.io.*;
import java.net.*;

public class Utils {

	public static String getInput(int year, int day) {
		File dir = new File("./data/inputs/"+year);
		if(!dir.exists()) dir.mkdir();
		File file = new File(dir.getAbsolutePath()+"/"+day+".txt");
		if(file.exists()) {
			try {
				@Cleanup BufferedReader reader = new BufferedReader(new FileReader(file));
				StringBuilder builder = new StringBuilder();
				String line;
				while((line = reader.readLine()) != null)
					builder.append(line).append("\n");
				return builder.toString();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		String sessionId = (String) Main.getProps().get("session");
		try {
			URL url = new URL("https://adventofcode.com/"+year+"/day/"+day+"/input");
			URLConnection con = url.openConnection();
			con.setRequestProperty("Cookie", "session="+sessionId);
			con.connect();

			@Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null)
				builder.append(line).append("\n");

			@Cleanup BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(builder.toString());
			writer.flush();
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
