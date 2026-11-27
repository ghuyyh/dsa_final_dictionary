package dsa.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import dsa.model.entry;

public class dict_loader {

    private static String url = "data/dictionary.json";
    private static Path path = Path.of(url);

    public static List<entry> load() {
        List<entry> entries = new ArrayList<>();
        try (JsonReader reader = new JsonReader(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                String word = null;
                double zipf = 0.0;
                while (reader.hasNext()) {
                    String key = reader.nextName();
                    if (key.equals("word")) {
                        if (reader.peek() != JsonToken.NULL) {
                            word = reader.nextString();
                            // System.out.println(word);
                        } else {
                            reader.nextNull();
                        }
                    } else if (key.equals("zipf")) {
                        if (reader.peek() != JsonToken.NULL) {
                            zipf = reader.nextDouble();
                        } else {
                            reader.nextNull();
                        }
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                if (word == null || word.isBlank()) {
                } else {
                    entries.add(new entry(word, zipf));
                }

            }
            reader.endArray();
        } catch (IOException e) {
            System.out.println(e);
        }
        return entries;
    }

    public static String load_def(String word) {
        // not yet
        return null;
    }

    public static void main(String[] args) {
        load();
    }

}
