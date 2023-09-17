package az.atl.msmessaging.dao.repository;

import az.atl.msmessaging.dao.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM activity_reports WHERE user_id = :userId;" +
            "DELETE FROM friendships WHERE user_id = :userId OR friend_id = :userId;" +
            "DELETE FROM messages WHERE sender_id = :userId OR receiver_id = :userId;" +
            "DELETE FROM user_status WHERE user_id = :userId;" +
            "DELETE FROM users WHERE id = :userId;", nativeQuery = true)
    void deleteUserAndRelatedEntities(Long userId);

}
