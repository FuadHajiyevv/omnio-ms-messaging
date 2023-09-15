package az.atl.msmessaging.dao.repository;

import az.atl.msmessaging.dao.entity.ActivityReportEntity;
import az.atl.msmessaging.dao.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityReportRepository extends JpaRepository<ActivityReportEntity,Long> {

    @Query(value = "SELECT COUNT(*) AS online_friend_count " +
            "FROM friendships AS f " +
            "JOIN user_status AS us ON f.friend_id = us.user_id " +
            "WHERE f.user_id = :userId " +
            "AND f.status = 'ACCEPTED' " +
            "AND us.status = 'ONLINE'", nativeQuery = true)
    long activeFriends(@Param(value = "userId") Long id);

    @Query(value = "SELECT COUNT(*) AS blocked_user_count " +
            "FROM friendships AS f " +
            "WHERE f.user_id = :userId " +
            "AND f.status = 'BLOCKED'", nativeQuery = true)
    long blockUsers(@Param(value = "userId") Long id);

    @Query(value = "SELECT COUNT(*) AS total_messages " +
            "FROM messages " +
            "WHERE :userId IN (sender_id, receiver_id)", nativeQuery = true)
    long totalMessages(@Param(value = "userId") Long id);

    @Query(value = "SELECT COUNT(*) AS total_accepted_friendships " +
            "FROM friendships " +
            "WHERE user_id = :userId " +
            "AND status = 'ACCEPTED'", nativeQuery = true)
    long totalFriends(@Param(value = "userId") Long id);

    @Query(value = "SELECT COUNT(DISTINCT CASE " +
            "WHEN sender_id = :userId THEN receiver_id " +
            "WHEN receiver_id = :userId THEN sender_id " +
            "END) AS total_chats " +
            "FROM messages " +
            "WHERE sender_id = :userId OR receiver_id = :userId", nativeQuery = true)
    long totalChats(@Param(value = "userId") Long id);

    ActivityReportEntity findAllByUserId(UserEntity user);
    @Transactional
    @Modifying
    @Query(value = "UPDATE activity_reports " +
            "SET last_online_timestamp = NOW() " +
            "WHERE user_id = :userId", nativeQuery = true)
    void updateLastOnline(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE activity_reports " +
            "SET last_offline_timestamp = NOW() " +
            "WHERE user_id = :userId", nativeQuery = true)
    void updateLastOffline(@Param("userId") Long userId);


    @Modifying
    @Query(value = "UPDATE activity_reports " +
            "SET response_time = EXTRACT(EPOCH FROM (last_online_timestamp - last_offline_timestamp))" +
            "WHERE user_id = :userId", nativeQuery = true)
    void updateResponseTimeForUser(@Param("userId") Long userId);

    @Query(value = "SELECT AVG(ar.response_time/60) AS average_response_time " +
            "FROM activity_reports ar", nativeQuery = true)
    Double calculateAverageResponseTime();





}
