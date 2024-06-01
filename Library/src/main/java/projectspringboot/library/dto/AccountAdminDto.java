package projectspringboot.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projectspringboot.library.model.Role;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountAdminDto {
    private String firstName;
    private String lastName;
    private String username;
    private Collection<Role> roles;
    private String password;
    private String repeatPass;
}
