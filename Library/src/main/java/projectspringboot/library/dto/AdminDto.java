package projectspringboot.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    @Size(min = 3, max = 10, message = "Invalid first name!(3-10 character)")
    private String firstName;
    @Size(min = 3, max = 10, message = "Invalid last name!(3-10 character)")
    private String lastName;
    private String username;
    @Size(min = 3, max = 20, message = "Invalid password!(3-20 character)")
    private String password;
    private String repeatPassword;
}
