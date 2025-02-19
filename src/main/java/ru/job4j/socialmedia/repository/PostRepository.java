package ru.job4j.socialmedia.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser(User user);

    List<Post> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Post p set p.header = :fHeader, p.description = :fDescription where p.id = :fPostId
            """)
    int updatePost(@Param("fPostId") int id, @Param("fHeader") String header, @Param("fDescription") String description);

    @Modifying(clearAutomatically = true)
    @Query("""
            update Post p set p.attachmentFile = ' '
                        where p.id =:fPostId""")
    int deleteAttachment(@Param("fPostId") int id);

    @Modifying
    @Query("""
            delete Post p where p.id = :fPostId
            """)
    int deletePost(@Param("fPostId") int id);

    @Query(value = """
            SELECT * FROM posts p
            WHERE p.j_user_id IN 
                        (SELECT s.subscriber_id_to
                                    FROM subscriptions s 
                                    WHERE s.subscriber_id = :fId)
            """, nativeQuery = true)
    List<Post> findSubscribersPostsByUserId(int id);

    Optional<Post> findById(int id);

    @Query(value = "SELECT p.* FROM posts p WHERE p.user_id IN :userIds", nativeQuery = true)
    List<Post> findAllByUserIdIn(@Param("userIds") List<Integer> userIds);
}
