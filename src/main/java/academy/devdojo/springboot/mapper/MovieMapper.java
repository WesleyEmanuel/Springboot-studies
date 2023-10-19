package academy.devdojo.springboot.mapper;

import academy.devdojo.springboot.domain.Movie;
import academy.devdojo.springboot.requests.MoviePostRequestBody;
import academy.devdojo.springboot.requests.MoviePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class MovieMapper {
    public static final MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    public abstract Movie toMovie(MoviePostRequestBody moviePostRequestBody);

    public abstract Movie toMovie(MoviePutRequestBody moviePutRequestBody);
}
