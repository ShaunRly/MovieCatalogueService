package ironhack.lab.book.movieservice;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {this.movieService = movieService;}

    @GetMapping
    public List<Movie> getAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Movie> findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public Movie createPerson(@RequestBody Movie person) {
        return movieService.save(person);
    }

    @PutMapping
    public Movie putPerson(@RequestBody Movie person) {
        return movieService.save(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        movieService.delete(id);
    }
}
