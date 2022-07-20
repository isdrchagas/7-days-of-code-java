import java.io.PrintWriter;
import java.util.List;

public class HTMLGenerator {

    private PrintWriter out;

    public HTMLGenerator(PrintWriter out) {
        this.out = out;
    }

    public void generate(List<Movie> movies) {
        movies.forEach(movie -> {
            String htmlTemplate =
                    """
                           <html>
                             <head>
                               <meta charset="utf-8">
                               <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                               <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" + 
                               "integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">                                      
                             </head>
                             <body>
                               <div class="card text-white bg-dark mb-3" style="max-width: 18rem;">
                                 <h2 class="card-header">%s</h2>
                                 <div class="card-body">
                                    <img class="card-img" src="%s" alt="%s">
                                    <p class="card-text mt-2">Nota: %s - Ano: %s</p>
                                 </div>
                               </div>
                             </body>
                           </html>
                    """;
            out.println(String.format(htmlTemplate, movie.getTitle(), movie.getUrlImage(), movie.getTitle(), movie.getRating(), movie.getYear()));
        });
    }
}
