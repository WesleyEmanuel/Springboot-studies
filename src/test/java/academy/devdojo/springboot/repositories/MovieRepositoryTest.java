package academy.devdojo.springboot.repositories;

import academy.devdojo.springboot.domain.Movie;
import academy.devdojo.springboot.util.MovieCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Movie Repository")
@Log4j2
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Save persists movie when Successful")
    void save_PersistsMovie_WhenSuccessful(){
        Movie movieToBeSaved = MovieCreator.createMovieToBeSaved();
        Movie savedMovie = this.movieRepository.save(movieToBeSaved);

        Assertions.assertThat(savedMovie).isNotNull();
        Assertions.assertThat(savedMovie.getId()).isNotNull();
        Assertions.assertThat(savedMovie.getName()).isEqualTo(movieToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates movie when Successful")
    void save_UpdatesMovie_WhenSuccessful(){
        Movie movieToBeSaved = MovieCreator.createMovieToBeSaved();
        Movie savedMovie = this.movieRepository.save(movieToBeSaved);

        savedMovie.setName("Test Update Movie");
        Movie updatedMovie = this.movieRepository.save(savedMovie);

        Assertions.assertThat(updatedMovie).isNotNull();
        Assertions.assertThat(updatedMovie.getId()).isNotNull();
        Assertions.assertThat(updatedMovie.getName()).isEqualTo(savedMovie.getName());
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty(){
        Movie movieToBeSaved = new Movie();

//        Assertions.assertThatThrownBy(() ->
//                this.movieRepository.save(movieToBeSaved))
//                .isInstanceOf(ConstraintViolationException.class
//        );

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.movieRepository.save(movieToBeSaved))
                .withMessageContaining("The name of this movie cannot be empty");
    }

    @Test
    @DisplayName("Delete removes movie when Successful")
    void delete_RemovesMovie_WhenSuccessful(){
        Movie movieToBeSaved = MovieCreator.createMovieToBeSaved();
        Movie savedMovie = this.movieRepository.save(movieToBeSaved);

        this.movieRepository.delete(savedMovie);

        Optional<Movie> deletedMovieOnDatabase = this.movieRepository.findById(savedMovie.getId());

        Assertions.assertThat(deletedMovieOnDatabase).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of anime when Successful")
    void findByName_ReturnListOfAnime_WhenSuccessful(){
        Movie movieToBeSaved = MovieCreator.createMovieToBeSaved();
        Movie savedMovie = this.movieRepository.save(movieToBeSaved);

        List<Movie> moviesList = this.movieRepository.findByName(savedMovie.getName());

        Assertions.assertThat(moviesList).isNotEmpty().contains(savedMovie);
    }

    @Test
    @DisplayName("Find By Name returns empty list when no movie is found")
    void findByName_ReturnsEmptyList_WhenNoMovieIsFound(){
        List<Movie> moviesList = this.movieRepository.findByName("Test");

        Assertions.assertThat(moviesList).isEmpty();
    }

}