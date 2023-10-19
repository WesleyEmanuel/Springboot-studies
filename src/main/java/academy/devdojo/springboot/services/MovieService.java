package academy.devdojo.springboot.services;

import academy.devdojo.springboot.domain.Movie;
import academy.devdojo.springboot.exception.BadRequestException;
import academy.devdojo.springboot.mapper.MovieMapper;
import academy.devdojo.springboot.repositories.MovieRepository;
import academy.devdojo.springboot.requests.MoviePostRequestBody;
import academy.devdojo.springboot.requests.MoviePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> listMovies(){
        return movieRepository.findAll();
    }

    public Movie findMovieByIdOrThrowBadRequestException(long id){
        return movieRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("Movie not found"));
    }

    public List<Movie> findMovieByName(String name){
        return movieRepository.findByName(name);
    }

    @Transactional
    public Movie saveMovie(MoviePostRequestBody moviePostRequestBody) {
        return movieRepository.save(MovieMapper.INSTANCE.toMovie(moviePostRequestBody));
    }

    public void deleteMovie(long id){
        movieRepository.delete(findMovieByIdOrThrowBadRequestException(id));
    }

    public void replace(MoviePutRequestBody moviePutRequestBody) {
        Movie savedMovie = findMovieByIdOrThrowBadRequestException(moviePutRequestBody.getId());
        Movie movie = MovieMapper.INSTANCE.toMovie(moviePutRequestBody);

        movie.setId(savedMovie.getId());

        movieRepository.save(movie);
    }
}
