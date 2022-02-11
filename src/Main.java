import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

// Warning: This is by no means a complete application. Only a Proof of Concept!

public class Main {

//credits: https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("https://newsapi.org/v2/top-headlines?country=pl&category=business&apiKey=192f7c9c69e84c388a5db5c70ca189c5");
        //System.out.println(json.toString());
        //System.out.println(json.get("status"));

        JSONArray articles = json.getJSONArray("articles");
        JSONObject article;

        System.out.println(articles.toString());

        FileWriter fileWriter = new FileWriter("output.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for(int i = 0; i < articles.length(); i++) { //What? JSONArray doesn't support foreach iteration?

            article = articles.getJSONObject(i);
            printWriter.printf("%s:%s:%s\n",
                    article.get("title"),
                    article.get("description"),
                    article.get("author"));


        }

        printWriter.close();
    }


}
