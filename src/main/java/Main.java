import java.net.URI;
import java.net.http.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

        List<Movie> parse = parseToMovie(movies);
        parse.forEach(System.out::println);
    }

    private static List<Movie> parseToMovie(String moviesArray) {
        List<Movie> movies = new ArrayList<>();

        List<String> ids = parseAttributes(moviesArray, "id");
        List<String> ranks = parseAttributes(moviesArray, "rank");
        List<String> titles = parseAttributes(moviesArray, "title");
        List<String> urls = parseAttributes(moviesArray, "image");
        List<String> ratings = parseAttributes(moviesArray, "imDbRating");
        List<String> years = parseAttributes(moviesArray, "year");

        IntSummaryStatistics stats = Stream.<List<?>>of(ids, ranks, titles, urls, ratings, years)
                .mapToInt(List::size)
                .summaryStatistics();

        if (stats.getMin() == stats.getMax()) {
            int size = ids.size();
            for (int i = 0; i < size; i++) {
                movies.add(new Movie(ids.get(i), ranks.get(i), titles.get(i), urls.get(i), ratings.get(i), years.get(i)));
            }
        } else {
            throw new IndexOutOfBoundsException("lists size doesn't match");
        }

        return movies;
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
