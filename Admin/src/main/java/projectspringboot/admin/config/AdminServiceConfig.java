package projectspringboot.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projectspringboot.library.model.Admin;
import projectspringboot.library.model.Role;
import projectspringboot.library.repository.IAdminRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminServiceConfig implements UserDetailsService {

    @Autowired
    private IAdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("Could not found username!!");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for(Role role : admin.getRoles()){
            for(String permission : role.getPermissions()){
                grantedAuthorities.add(new SimpleGrantedAuthority(permission));
            }
        }
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                grantedAuthorities
        );
    }
}
