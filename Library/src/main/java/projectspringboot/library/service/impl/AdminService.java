package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import projectspringboot.library.dto.AccountAdminDto;
import projectspringboot.library.dto.AdminDto;
import projectspringboot.library.model.Admin;
import projectspringboot.library.repository.IAdminRepository;
import projectspringboot.library.repository.IRoleRepository;
import projectspringboot.library.service.IAdminService;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private IAdminRepository adminRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin createAccount(AccountAdminDto accountAdminDto) {
        Admin admin = new Admin();
        admin.setFirstName(accountAdminDto.getFirstName());
        admin.setLastName(accountAdminDto.getLastName());
        admin.setUsername(accountAdminDto.getUsername());
        admin.setPassword(accountAdminDto.getPassword());
        admin.setRoles(accountAdminDto.getRoles());
        return adminRepository.save(admin);
    }
}
