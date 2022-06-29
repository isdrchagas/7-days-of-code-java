import java.net.URI;
import java.net.http.*;

public class Main {

    private static String API_KEY = System.getenv("IMDb_KEY");
    private static String URL = "https://imdb-api.com/en/API/Top250Movies/";

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                new URI(URL + API_KEY))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println(response.body());
    }
}
