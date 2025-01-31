package ru.job4j.socialmedia.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clearAll() {
        userRepository.deleteAll();
    }

    @Test
    void whenSaveUserThenReturnUser() {
        User user = new User().setUsername("test username")
                .setPassword("test password")
                .setEmail("test@test.com");
        userRepository.save(user);
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getUsername()).isEqualTo("test username");
    }

    @Test
    void whenSaveUsersThenReturnAllUsers() {
        User user = new User().setUsername("test username 1");
        User user2 = new User().setUsername("test username 2");
        User user3 = new User().setUsername("test username 3");
        userRepository.saveAll(List.of(user, user2, user3));
        List<User> allUsers = (List) userRepository.findAll();
        assertThat(allUsers.size()).isEqualTo(3);
        assertThat(allUsers.get(1)).extracting(User::getUsername).isEqualTo("test username 2");
    }

}