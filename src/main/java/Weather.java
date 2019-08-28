import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

//    a0511cd74c39e088929c1891efa76886

    public static String getWeather(String message, Model model) throws IOException {

        URL url = new URL("https://samples.openweathermap.org/data/2.5/find?q=" + message
                + "&units=metric&appid=a0511cd74c39e088929c1891efa76886");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";

        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONArray arrayList = object.getJSONArray("list");

        for (int i = 0; i < arrayList.length(); i++) {
            JSONObject objectName = arrayList.getJSONObject(i);
            model.setName(objectName.getString("name"));

            JSONObject objectMain = objectName.getJSONObject("main");
            model.setTemp(objectMain.getDouble("temp"));
            model.setHumidity(objectMain.getDouble("humidity"));


            JSONObject jsonObjectWeather = arrayList.getJSONObject(i);
            JSONArray jsonArrayWeather = jsonObjectWeather.getJSONArray("weather");
            for (int j = 0; j < jsonArrayWeather.length(); j++) {
                JSONObject jsonObjectInWeather = jsonArrayWeather.getJSONObject(j);
                model.setMain(jsonObjectInWeather.getString("main"));
                model.setIcon(jsonObjectInWeather.getString("icon"));
            }
        }


        return "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "C" + "\n" +
                "humidity: " + model.getHumidity() + "%" + "\n" +
                "main: " + model.getMain() + "%" + "\n" +
                "http://openweathermap.org/img/wn/" + model.getIcon() + "@2x.png"
                ;
    }
}