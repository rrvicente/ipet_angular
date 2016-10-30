package br.com.ipet.json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRead {

	@SuppressWarnings("unchecked")
	public static void gravaJSONObject(JSONObject jsonObject, String fileName) {

		JSONArray jr = getJSONArrayFromFile(fileName);

		FileWriter writeFile = null;

		jr.add(jsonObject);
		try {

			writeFile = new FileWriter(fileName);

			writeFile.write(jr.toJSONString());
			writeFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static JSONArray getJSONArrayFromFile(String fileName) {
		JSONParser parser = new JSONParser();

		File file = new File(fileName);

		try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				return (JSONArray) parser.parse(new FileReader(fileName));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return new JSONArray();
	}

	public static JSONObject getJSONObjectById(Integer id, String fileName) {
		JSONArray jr = getJSONArrayFromFile(fileName);

		for (int i = 0; i < jr.size(); i++) {
			JSONObject jsonObject = (JSONObject) jr.get(i);

			if (id.equals(((Long) jsonObject.get("id")).intValue()))
				return jsonObject;
		}
		return null;
	}

	public static String listarJSONObject(String fileName) {
		List<JSONObject> lista = new ArrayList<JSONObject>();

		JSONArray jr = getJSONArrayFromFile(fileName);

		for (int i = 0; i < jr.size(); i++) {
			lista.add((JSONObject) jr.get(i));
		}
		return lista.toString();
	}

	public static void deleteJSONObjectById(Integer id, String fileName) {
		JSONArray jr = getJSONArrayFromFile(fileName);

		for (int i = 0; i < jr.size(); i++) {
			JSONObject jsonObject = (JSONObject) jr.get(i);

			if (id.equals(((Long) jsonObject.get("id")).intValue())) {
				jr.remove(i);
				gravaJSONArray(jr, fileName);
				break;
			}
		}
	}

	public static void gravaJSONArray(JSONArray jr, String fileName) {
		FileWriter writeFile = null;

		try {

			File file = new File(fileName);

			if (!file.exists())
				file.createNewFile();

			writeFile = new FileWriter(fileName);

			writeFile.write(jr.toJSONString());
			writeFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}