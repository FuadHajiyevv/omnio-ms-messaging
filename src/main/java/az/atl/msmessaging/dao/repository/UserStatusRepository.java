package az.atl.msmessaging.dao.repository;

import az.atl.msmessaging.dao.entity.UserStatusEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_status SET status = 'ONLINE' " +
            "WHERE user_id = (SELECT id FROM users WHERE username = :username)", nativeQuery = true)
    void updateUserStatusToOnlineByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_status SET status = 'OFFLINE', last_active_at = CURRENT_TIMESTAMP " +
            "WHERE user_id = (SELECT id FROM users WHERE username = :username)", nativeQuery = true)
    void updateUserStatusToOfflineByUsername(String username);

    @Query(value = "select entity from UserStatusEntity entity where entity.userId = " +
            "(select user from UserEntity user where user.username = :username)")
    UserStatusEntity getStatusByUsername(@Param("username") String username);
}
