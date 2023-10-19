package academy.devdojo.springboot.requests;

import lombok.Data;

@Data
public class MoviePutRequestBody {
    private Long id;
    private String name;
}
