package kristiania.enterprise.exam.backend;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableAsync // IMPORTANT: otherwise @Async will not work
@SpringBootApplication
public class StubApplication {

}
