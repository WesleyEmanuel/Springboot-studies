package academy.devdojo.springboot.util;

import academy.devdojo.springboot.requests.MoviePutRequestBody;

public class MoviePutRequestBodyCreator {
    public static MoviePutRequestBody createMovieToBeUpdate(){
        return MoviePutRequestBody.builder()
                .id(MovieCreator.createMovieToBeSaved().getId())
                .name(MovieCreator.createMovieToBeSaved().getName())
                .build();
    }
}
