package projectspringboot.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"projectspringboot.library.*", "projectspringboot.customer.*"})
@EnableJpaRepositories(value = "projectspringboot.library.repository")
@EntityScan(value = "projectspringboot.library.model")
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}
}
