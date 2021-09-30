package ironhack.lab.book.movieservice;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {


    public static final String TARGET_SERVICE = "movie-catalogue";
    final MovieRepository movieRepository;
    private WebClient client;
    //    private final WebClient client = WebClient.create("http://localhost:8080");
    final DiscoveryClient discoveryClient;

    public MovieService(MovieRepository movieRepository, DiscoveryClient discoveryClient) {
        this.movieRepository = movieRepository;
        this.discoveryClient = discoveryClient;
        createClient();
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie save(Movie movie) {
        var catalogueNumber = getBookCatalogueNumber(movie);
        movie.setCatalogueNumber(catalogueNumber);
        return movieRepository.save(movie);
    }

    private Long getBookCatalogueNumber(Movie movie) {
        return client.get()
                .uri("/catnum")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public void delete(Long id) {
        var person = movieRepository.findById(id);
        if (person.isEmpty()) throw new RuntimeException("NOT FOUND " + id);
        movieRepository.delete(person.get());
    }

    private void createClient() {
        var serviceInstanceList = discoveryClient.getInstances(TARGET_SERVICE);
        String clientURI = serviceInstanceList.get(0).getUri().toString();
        client = WebClient.create(clientURI);
    }
}
