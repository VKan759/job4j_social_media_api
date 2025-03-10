package ru.job4j.socialmedia.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
            select u from User u 
                        where u.username = :fUsername 
                                    and u.password = :fPassword
            """)
    Optional<User> findUserByLoginAndPassword(@Param("fUsername") String username, @Param("fPassword") String password);

    @Query(value = """
            SELECT * FROM users u 
                        JOIN subscriptions s ON u.id = s.subscriber_id
                                   WHERE u.id = :fId
            """, nativeQuery = true)
    List<User> findSubscribersById(@Param("fId") int id);

    List<User> findByIdIn(List<Integer> ids);

    Optional<User> findById(int id);

    User save(User user);

    Boolean existsByUsername(@NotBlank @Size(min = 3, max = 20) String username);

    Boolean existsByEmail(@NotBlank @Size(max = 50) @Email String email);

    Optional<User> findByUsername(String username);
}
