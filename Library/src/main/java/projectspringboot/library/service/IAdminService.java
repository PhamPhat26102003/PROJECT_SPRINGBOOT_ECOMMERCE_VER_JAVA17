package projectspringboot.library.service;

import projectspringboot.library.dto.AdminDto;
import projectspringboot.library.model.Admin;

public interface IAdminService {
    Admin findByUsername(String username);
    Admin save(AdminDto adminDto);
}
