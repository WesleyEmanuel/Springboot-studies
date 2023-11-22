package academy.devdojo.springboot.util;

import academy.devdojo.springboot.domain.Movie;

public class MovieCreator {
    public static Movie createMovieToBeSaved(){
        return Movie.builder()
                .name("Test Movie")
                .build();
    }

    public static Movie createValidMovie(){
        return Movie.builder()
                .name("Test Movie")
                .id(1L)
                .build();
    }

    public static Movie createValidUpdatedMovie(){
        return Movie.builder()
                .name("Test Update Movie")
                .id(1L)
                .build();
    }
}
