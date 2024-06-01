package projectspringboot.library.service;

import projectspringboot.library.dto.AccountAdminDto;
import projectspringboot.library.dto.AdminDto;
import projectspringboot.library.model.Admin;

import java.util.List;

public interface IAdminService {
    Admin findByUsername(String username);
    Admin save(AdminDto adminDto);
    List<Admin> findAll();
    Admin createAccount(AccountAdminDto accountAdminDto);
}
