import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ImdbMovieJsonParser {

    private String json;

    public ImdbMovieJsonParser(String json) {
        this.json = json;
    }

    public List<Movie> parseToMovie() {
        String moviesArray = extractItemsFromMovies(json);
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

    private static String extractItemsFromMovies(String json) {
        return extractJson(json, "(\\[\\{.*}\\])", 0);
    }

    private static String extractJson(String json, String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);

        if (!matcher.find()) throw new IllegalArgumentException("no match found in " + json);
        return matcher.group(group);
    }

    private static List<String> parseAttributes(String json, String key) {
        String regex = "("+key+").:\\\"(.*?)\\\"";
        Matcher matcher = Pattern.compile(regex).matcher(json);
        List<String> matches = new ArrayList<>();

        if (!matcher.find()) throw new IllegalArgumentException("no match found in " + json);

        while (matcher.find()) matches.add(matcher.group(2));
        return matches;
    }
}
