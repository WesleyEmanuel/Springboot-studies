package academy.devdojo.springboot.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoviePostRequestBody {
    @NotEmpty(message = "The name of this movie cannot be empty")
    private String name;
}
