package com.kislichenko.news.other;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.entity.AppUser;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {AppUserRepositoryTest.Initializer.class})
public class AppUserRepositoryTest {

    @Autowired
    AppUserRepository appUserRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        AppUser appUser = new AppUser();
        appUser.setUsername("Alex");
        appUser.setPassword("password");
        appUser.setEmail("test@mail.ru");
        appUser.setSurname("Alexov");
//        entityManager.persist(appUser);
//        entityManager.flush();

        appUserRepository.save(appUser);

        // when
        AppUser found = appUserRepository.findByUsername(appUser.getUsername());

        // then
        assertThat(found.getName())
                .isEqualTo(appUser.getName());
    }
}
