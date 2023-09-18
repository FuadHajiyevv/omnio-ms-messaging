package az.atl.msmessaging.dao.repository;

import az.atl.msmessaging.dao.entity.MessageEntity;
import az.atl.msmessaging.dto.response.SupervisorMessageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query(value = "SELECT DISTINCT u.username " +
            "FROM users u " +
            "INNER JOIN messages m ON u.id = m.sender_id OR u.id = m.receiver_id " +
            "WHERE u.username != :username", nativeQuery = true)
    List<String> getChats(@Param("username") String username);


    @Query(value = "SELECT m.timestamp, u.username, m.content " +
            "FROM messages m " +
            "JOIN users u ON m.sender_id = u.id " +
            "JOIN users u2 ON m.receiver_id = u2.id " +
            "WHERE (u.username = :username AND u2.username = :friendUsername) " +
            "   OR (u.username = :friendUsername AND u2.username = :username) " +
            "ORDER BY m.timestamp", nativeQuery = true)
    List<Object[]> getMessages(@Param("username") String username, @Param("friendUsername") String friendUsername);
}

