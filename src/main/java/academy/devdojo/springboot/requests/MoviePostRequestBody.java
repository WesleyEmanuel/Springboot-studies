package academy.devdojo.springboot.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoviePostRequestBody {
    @NotEmpty(message = "The name of this movie cannot be empty")
    @NotNull(message = "The name of this movie cannot be null")
    private String name;
}
