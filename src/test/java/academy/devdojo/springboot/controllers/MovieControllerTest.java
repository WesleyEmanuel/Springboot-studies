package academy.devdojo.springboot.controllers;

import academy.devdojo.springboot.domain.Movie;
import academy.devdojo.springboot.services.MovieService;
import academy.devdojo.springboot.util.MovieCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        BDDMockito.when(movieServiceMock.listPaginatedMovies(ArgumentMatchers.any())).thenReturn(moviesPage);
    }
    @Test
    @DisplayName("List returns list of Movies inside page object when successful")
    void list_ReturnsListOfMoviesInsidePageObject_WhenSuccessful(){
        String expectedMovieName = MovieCreator.createValidMovie().getName();
        Page<Movie> moviesPage = movieController.listPaginatedMovies(null).getBody();

        Assertions.assertThat(moviesPage).isNotNull();
        Assertions.assertThat(moviesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(moviesPage.toList().get(0).getName()).isEqualTo(expectedMovieName);
    }
}