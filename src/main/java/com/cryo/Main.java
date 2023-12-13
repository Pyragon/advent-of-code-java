package com.cryo;

import com.cryo.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class Main {

	private static HashMap<String, Object> properties;
	private static Gson gson;

	public static int YEAR = 2023;
	public static int DAY = 1;

	public static void main(String[] args) {
		gson = buildGson();
		loadProperties();
		String input = Utils.getInput(YEAR, DAY);
		String[] split = input.split("\n");
		try {
			Class<?> c = Class.forName("com.cryo.y" + YEAR + ".Day" + DAY);
			Day instance = (Day) c.getConstructor().newInstance();
			instance.part1(input, split);
			instance.part2(input, split);
		} catch (ClassNotFoundException e) {
			System.err.println("Could not find a class for year "+YEAR+" day "+DAY);
			e.printStackTrace();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static Gson buildGson() {
		return new GsonBuilder().disableHtmlEscaping()
				.setPrettyPrinting()
				.create();
	}

	public static void loadProperties() {
		try {
			@Cleanup BufferedReader reader = new BufferedReader(new FileReader("data/props.json"));
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null)
				builder.append(line);
			properties = gson.fromJson(builder.toString(), HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, Object> getProps() {
		return properties;
	}
}
