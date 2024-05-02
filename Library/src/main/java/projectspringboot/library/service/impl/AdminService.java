package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import projectspringboot.library.dto.AdminDto;
import projectspringboot.library.model.Admin;
import projectspringboot.library.repository.IAdminRepository;
import projectspringboot.library.repository.IRoleRepository;
import projectspringboot.library.service.IAdminService;

import java.util.Arrays;

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
}
