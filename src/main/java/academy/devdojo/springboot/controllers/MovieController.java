package academy.devdojo.springboot.controllers;

import academy.devdojo.springboot.domain.Movie;
import academy.devdojo.springboot.requests.MoviePutRequestBody;
import academy.devdojo.springboot.requests.MoviePostRequestBody;
import academy.devdojo.springboot.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @GetMapping
    public ResponseEntity<Page<Movie>> listPaginatedMovies(Pageable pageable){
        return ResponseEntity.ok(movieService.listPaginatedMovies(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> listAllMovies(){
        return ResponseEntity.ok(movieService.listAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable long id){
        return ResponseEntity.ok(movieService.findMovieByIdOrThrowBadRequestException(id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Movie>> findMovieByName(@RequestParam String name){
        return ResponseEntity.ok(movieService.findMovieByName(name));
    }

    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody @Valid MoviePostRequestBody movie){
        return new ResponseEntity<>(movieService.saveMovie(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable long id){
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replaceMovie(@RequestBody MoviePutRequestBody movie){
        movieService.replace(movie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}