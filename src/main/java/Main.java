import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String movies = extractItemsFromMovies(response.body());

        List<String> titles = parseAttributes(movies, "title");
        titles.forEach(System.out::println);

        List<String> urlImages = parseAttributes(movies, "image");
        urlImages.forEach(System.out::println);

        List<String> years = parseAttributes(movies, "year");
        years.forEach(System.out::println);

        List<String> ratings = parseAttributes(movies, "imDbRating");
        ratings.forEach(System.out::println);
    }

    static String extractItemsFromMovies(String json) {
        return extractJson(json, "(\\[\\{.*}\\])", 0);
    }

    static String extractJson(String json, String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);

        if (!matcher.find()) throw new IllegalArgumentException("no match found in " + json);
        return matcher.group(group);
    }

    static List<String> parseAttributes(String json, String key) {
        String regex = "("+key+").:\\\"(.*?)\\\"";
        Matcher matcher = Pattern.compile(regex).matcher(json);
        List<String> matches = new ArrayList<>();

        if (!matcher.find()) throw new IllegalArgumentException("no match found in " + json);

        while (matcher.find()) matches.add(matcher.group(2));
        return matches;
    }
}
