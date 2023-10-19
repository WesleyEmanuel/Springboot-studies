package academy.devdojo.springboot.controllers;

import academy.devdojo.springboot.domain.Movie;
import academy.devdojo.springboot.requests.MoviePutRequestBody;
import academy.devdojo.springboot.requests.MoviePostRequestBody;
import academy.devdojo.springboot.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @GetMapping
    public ResponseEntity<List<Movie>> listMovies(){
        return ResponseEntity.ok(movieService.listMovies());
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
    public ResponseEntity<Movie> saveMovie(@RequestBody MoviePostRequestBody movie){
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