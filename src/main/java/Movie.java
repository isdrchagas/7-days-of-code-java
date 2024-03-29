public class Movie implements Content {

    private String id;
    private String rank;
    private String title;
    private String urlImage;
    private String rating;
    private String year;

    public Movie(String id, String rank, String title, String urlImage, String rating, String year) {
        this.id = id;
        this.rank = rank;
        this.title = title;
        this.urlImage = urlImage;
        this.rating = rating;
        this.year = year;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String urlImage() {
        return urlImage;
    }

    @Override
    public String rating() {
        return rating;
    }

    @Override
    public String year() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie {" +
                "id:" + id +
                ", rank:" + rank +
                ", title:" + title +
                ", urlImage:" + urlImage +
                ", rating:" + rating +
                ", year:" + year +
                "}";
    }
}
