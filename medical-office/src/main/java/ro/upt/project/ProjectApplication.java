package ro.upt.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import ro.upt.project.models.Role;
import ro.upt.project.models.Specialization;
import ro.upt.project.models.User;
import ro.upt.project.repositories.UserRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
/*
@Component
class asdasdd {
    private final UserRepository userRepository;

    @Autowired
    public asdasdd(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void asdasd() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setFirstName("Admin");
        user.setLastName("Adminel");
        user.setRole(Role.DOCTOR);
        user.setSpecialization(Specialization.GINECOLOGIE);

        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("admin2");
        user2.setPassword("admin2");
        user2.setFirstName("Gigel");
        user2.setLastName("Mirel");
        user2.setRole(Role.DOCTOR);
        user2.setSpecialization(Specialization.GASTROENTEROLOGIE);

        userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("pacient");
        user3.setPassword("pacient");
        user3.setFirstName("Bogdy");
        user3.setLastName("Biceps");
        user3.setRole(Role.PATIENT);
        user3.setSpecialization(Specialization.NONE);

        userRepository.save(user3);
    }
}
*/