package com.alisonadamus.astronix.config;

import com.alisonadamus.astronix.model.Location;
import com.alisonadamus.astronix.model.Role;
import com.alisonadamus.astronix.model.User;
import com.alisonadamus.astronix.repository.LocationRepository;
import com.alisonadamus.astronix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedData(UserRepository userRepo,
                               LocationRepository locationRepo) {

        return args -> {
            if (userRepo.count() > 0 || locationRepo.count() > 0) {
                return;
            }

            // USERS
            User admin = new User();
            admin.setEmail("admin@astronix.com");
            admin.setLogin("alison");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);

            User user1 = new User();
            user1.setEmail("user1@astronix.com");
            user1.setLogin("user1");
            user1.setPassword(passwordEncoder.encode("password123"));
            user1.setRole(Role.ROLE_USER);

            User user2 = new User();
            user2.setEmail("user2@astronix.com");
            user2.setLogin("user2");
            user2.setPassword(passwordEncoder.encode("password123"));
            user2.setRole(Role.ROLE_USER);

            userRepo.save(admin);
            userRepo.save(user1);
            userRepo.save(user2);

            //  LOCATIONS

            Location solarSystem = new Location();
            solarSystem.setName("Сонячна система");
            solarSystem.setDescription(
                    "Сонячна система — це планетна система, яка обертається навколо зорі Сонце. " +
                            "До її складу входять вісім планет, карликові планети, астероїди, комети та інші небесні тіла. " +
                            "Планети поділяються на кам’янисті (Меркурій, Венера, Земля, Марс) та газові гіганти " +
                            "(Юпітер, Сатурн, Уран, Нептун). Сонце містить понад 99% всієї маси системи і є джерелом енергії для життя на Землі."
            );
            solarSystem.setImageUrl("https://assets.newsweek.com/wp-content/uploads/2025/08/2168730-planets-solar-system.jpg?w=1600&quality=80&webp=1");

            Location jupiter = new Location();
            jupiter.setName("Юпітер");
            jupiter.setDescription(
                    "Юпітер — найбільша планета Сонячної системи, газовий гігант, маса якого більш ніж удвічі перевищує масу всіх інших планет разом узятих. " +
                            "Він складається переважно з водню та гелію і не має твердої поверхні. " +
                            "Юпітер відомий своєю Великою Червоною Плямою — гігантським штормом, який триває вже понад 300 років. " +
                            "Планета має десятки супутників, серед яких Ганімед — найбільший супутник у Сонячній системі."
            );
            jupiter.setImageUrl("https://static.vecteezy.com/system/resources/previews/051/075/283/non_2x/jupiter-the-largest-planet-in-our-solar-system-free-png.png");

            Location saturn = new Location();
            saturn.setName("Сатурн");
            saturn.setDescription(
                    "Сатурн — газовий гігант, відомий своєю вражаючою системою кілець, що складаються з льоду, каміння та пилу. " +
                            "Він є другою за розмірами планетою Сонячної системи. " +
                            "Атмосфера Сатурна складається переважно з водню та гелію. " +
                            "Планета має багато супутників, серед яких Титан — один із найцікавіших, адже має щільну атмосферу та рідкі вуглеводні на поверхні."
            );
            saturn.setImageUrl("https://static.vecteezy.com/system/resources/previews/052/295/178/non_2x/the-planet-saturn-is-shown-in-this-image-png.png");

            locationRepo.save(solarSystem);
            locationRepo.save(jupiter);
            locationRepo.save(saturn);
        };
    }
}