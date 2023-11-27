package academy.devdojo.springboot.util;

import academy.devdojo.springboot.requests.MoviePostRequestBody;

public class MoviePostRequestBodyCreator {
    public static MoviePostRequestBody createMovieToBeSaved(){
        return MoviePostRequestBody.builder()
                .name(MovieCreator.createMovieToBeSaved().getName())
                .build();
    }
}
