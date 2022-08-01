import java.io.PrintWriter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("IMDb_KEY");

        String json = new ImdbApiClient(apiKey).getBody();
        List<Movie> movies = new ImdbMovieJsonParser(json).parse();
        movies.forEach(System.out::println);

        PrintWriter writer = new PrintWriter("movies.html");
        new HTMLGenerator(writer).generate(movies);
        writer.close();
    }
}
