package academy.devdojo.springboot.controllers;

import academy.devdojo.springboot.domain.Movie;
import academy.devdojo.springboot.requests.MoviePostRequestBody;
import academy.devdojo.springboot.requests.MoviePutRequestBody;
import academy.devdojo.springboot.services.MovieService;
import academy.devdojo.springboot.util.MovieCreator;
import academy.devdojo.springboot.util.MoviePostRequestBodyCreator;
import academy.devdojo.springboot.util.MoviePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class MovieControllerTest {
    @InjectMocks
    private MovieController movieController;
    @Mock
    private MovieService movieServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Movie> moviesPage = new PageImpl<>(List.of(MovieCreator.createValidMovie()));

        BDDMockito.when(movieServiceMock.listPaginatedMovies(ArgumentMatchers.any()))
                .thenReturn(moviesPage);

        BDDMockito.when(movieServiceMock.listAllMovies())
                .thenReturn(List.of(MovieCreator.createValidMovie()));

        BDDMockito.when(movieServiceMock.findMovieByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(MovieCreator.createValidMovie());

        BDDMockito.when(movieServiceMock.findMovieByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(MovieCreator.createValidMovie()));

        BDDMockito.when(movieServiceMock.saveMovie(ArgumentMatchers.any(MoviePostRequestBody.class)))
                .thenReturn(MovieCreator.createValidMovie());

        BDDMockito.doNothing().when(movieServiceMock).replace(ArgumentMatchers.any(MoviePutRequestBody.class));
    }

    @Test
    @DisplayName("listPaginatedMovies returns list of Movies inside page object when successful")
    void listPaginatedMovies_ReturnsListOfMoviesInsidePageObject_WhenSuccessful(){
        String expectedMovieName = MovieCreator.createValidMovie().getName();
        Page<Movie> moviesPage = movieController.listPaginatedMovies(null).getBody();

        Assertions.assertThat(moviesPage).isNotNull();
        Assertions.assertThat(moviesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(moviesPage.toList().get(0).getName()).isEqualTo(expectedMovieName);
    }

    @Test
    @DisplayName("listAllMovies returns list of Movies when successful")
    void listAllMovies_ReturnsListOfMovies_WhenSuccessful(){
        String expectedMovieName = MovieCreator.createValidMovie().getName();
        List<Movie> moviesList = movieController.listAllMovies().getBody();

        Assertions.assertThat(moviesList).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(moviesList.get(0).getName()).isEqualTo(expectedMovieName);
    }

    @Test
    @DisplayName("findMovieById returns Movie by id when successful")
    void findMovieById_ReturnsMovieById_WhenSuccessful(){
        long expectedMovieId = MovieCreator.createValidMovie().getId();

        Movie movie = movieController.findMovieById(1).getBody();

        Assertions.assertThat(movie).isNotNull();

        Assertions.assertThat(movie.getId()).isNotNull().isEqualTo(expectedMovieId);
    }

    @Test
    @DisplayName("findMovieByName returns list of Movies by name when successful")
    void findMovieByName_ReturnsListOfMoviesByName_WhenSuccessful(){
        String expectedMovieName = MovieCreator.createValidMovie().getName();

        List<Movie> moviesList = movieController.findMovieByName("Test Movie").getBody();

        Assertions.assertThat(moviesList).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(moviesList.get(0).getName()).isEqualTo(expectedMovieName);
    }

    @Test
    @DisplayName("findMovieByName returns an empty list when Movie is not found")
    void findMovieByName_ReturnsAnEmpty_WhenMovieIsNotFound(){
        BDDMockito.when(movieServiceMock.findMovieByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Movie> moviesList = movieController.findMovieByName("Non Existent Movie").getBody();

        Assertions.assertThat(moviesList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("saveMovie returns Movie when successful")
    void saveMovie_ReturnsMovie_WhenSuccessful(){
        MoviePostRequestBody movieToBeSaved = MoviePostRequestBodyCreator.createMovieToBeSaved();

        Movie movie = movieController.saveMovie(movieToBeSaved).getBody();

        Assertions.assertThat(movie).isNotNull().isEqualTo(MovieCreator.createValidMovie());
    }

    @Test
    @DisplayName("replaceMovie returns Movie when successful")
    void replaceMovie_ReturnsMovie_WhenSuccessful(){
        Assertions.assertThatCode(() -> movieController.replaceMovie(
                MoviePutRequestBodyCreator.createMovieToBeUpdate()
        )).doesNotThrowAnyException();

        ResponseEntity<Void> voidResponseEntity = movieController.replaceMovie(
                MoviePutRequestBodyCreator.createMovieToBeUpdate()
        );

        Assertions.assertThat(voidResponseEntity).isNotNull();

        Assertions.assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}