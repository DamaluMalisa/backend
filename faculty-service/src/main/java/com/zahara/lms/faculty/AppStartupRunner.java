package com.zahara.lms.faculty;

import com.zahara.lms.faculty.model.Administrator;
import com.zahara.lms.faculty.repository.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zahara.lms.shared.security.SecurityUtils.ROOT_USER_ID;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
    private final AdministratorRepository administratorRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<Administrator> administrators =
                (List<Administrator>) administratorRepository.findAll();

        if (administrators.isEmpty()) {
            administratorRepository.save(
                    Administrator.builder()
                            .id(ROOT_USER_ID)
                            .userId(ROOT_USER_ID)
                            .firstName("Bojan")
                            .lastName("Zdelar")
                            .build());
        }
    }
}
