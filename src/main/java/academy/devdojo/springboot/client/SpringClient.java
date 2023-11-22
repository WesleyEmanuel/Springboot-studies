package academy.devdojo.springboot.client;

import academy.devdojo.springboot.domain.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Movie> entity = new RestTemplate().getForEntity(
                "http://localhost:8080/movies/{id}", Movie.class,2
        );
        log.info(entity);

        Movie[] movies = new RestTemplate().getForObject("http://localhost:8080/movies/all", Movie[].class);
        log.info(Arrays.toString(movies));

        ResponseEntity<List<Movie>> exchangeMovies = new RestTemplate().exchange(
                "http://localhost:8080/movies/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Movie>>() {
                }
        );
        log.info(exchangeMovies.getBody());

//        Movie beingJohnMalkovich = Movie.builder().name("Being John Malkovich").build();
//        Movie movieResponseObject = new RestTemplate().postForObject(
//                "http://localhost:8080/movies", beingJohnMalkovich, Movie.class
//        );
//
//        log.info(movieResponseObject);

        Movie queroSerJohnMalkovich = Movie.builder().id(40L).name("Quero ser John Malkovich").build();
        ResponseEntity<Void> updatedMovie = new RestTemplate().exchange(
                "http://localhost:8080/movies",
                HttpMethod.PUT,
                new HttpEntity<>(queroSerJohnMalkovich),
                Void.class
        );

        log.info(updatedMovie);

        ResponseEntity<Void> deleteMovie = new RestTemplate().exchange(
                "http://localhost:8080/movies/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(queroSerJohnMalkovich, createHttpHeaders()),
                Void.class,
                40
        );

        log.info(deleteMovie);
    }

    private static HttpHeaders createHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}