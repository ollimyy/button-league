package fi.ollimyy.buttonleague;

import fi.ollimyy.buttonleague.domain.AppUser;
import fi.ollimyy.buttonleague.domain.AppUserRepository;
import fi.ollimyy.buttonleague.domain.UserRole;
import fi.ollimyy.buttonleague.domain.UserRoleRepository;
import fi.ollimyy.buttonleague.service.TestDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ButtonLeagueApplication {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private TestDataInitializer testDataInitializer;

    public static void main(String[] args) {
        SpringApplication.run(ButtonLeagueApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner createAdminUser(BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            // Check if admin user already exists
            if (appUserRepository.findByUsername("admin") == null) {
                try {
                    // Create ADMIN role
                    // !!! CHANGE USERNAME FOR PRODUCTION !!!
                    UserRole adminRole = new UserRole("ADMIN");
                    adminRole = userRoleRepository.save(adminRole);

                    // Create admin user
                    // !!! CHANGE PASSWORD FOR PRODUCTION !!!
                    String hashedPassword = passwordEncoder.encode("admin");
                    System.out.println("Created password hash: " + hashedPassword);

                    AppUser adminUser = new AppUser("admin", hashedPassword, adminRole);
                    appUserRepository.save(adminUser);

                    System.out.println("Admin user created successfully");
                } catch (Exception e) {
                    System.err.println("Error creating admin user: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // Initialize test data, !!! comment this out in production if needed
            testDataInitializer.initializeTeams();
            testDataInitializer.initializeMatches();
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}