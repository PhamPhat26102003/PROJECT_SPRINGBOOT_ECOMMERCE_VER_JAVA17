package projectspringboot.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import projectspringboot.library.model.Admin;
import projectspringboot.library.model.Permission;
import projectspringboot.library.model.Role;
import projectspringboot.library.repository.IAdminRepository;
import projectspringboot.library.repository.IPermissionRepository;
import projectspringboot.library.repository.IRoleRepository;

import java.util.Set;

@SpringBootApplication(scanBasePackages = {"projectspringboot.library.*", "projectspringboot.admin.*"})
@EnableJpaRepositories(value = "projectspringboot.library.repository")
@EntityScan(value = "projectspringboot.library.model")
@Component
public class AdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
