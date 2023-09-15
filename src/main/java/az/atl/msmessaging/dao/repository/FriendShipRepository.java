package az.atl.msmessaging.dao.repository;

import az.atl.msmessaging.dao.entity.FriendshipEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendShipRepository extends JpaRepository<FriendshipEntity, Long> {


    @Query(value = "SELECT u.username " +
            "FROM users u " +
            "JOIN friendships f ON u.id = f.user_id " +
            "JOIN users friend ON friend.id = f.friend_id " +
            "WHERE friend.username = :friendUsername " +
            "  AND f.status = 'PENDING'",
            nativeQuery = true)
    List<String> getListOfPendingFriends(@Param("friendUsername") String username);


    @Modifying
    @Transactional
    @Query(value = "UPDATE friendships " +
            "SET status = 'ACCEPTED' " +
            "WHERE user_id IN (SELECT id FROM users WHERE username = :username) " +
            "AND friend_id IN (SELECT id FROM users WHERE username = :friendUsername)", nativeQuery = true)
    void acceptFriendShip(@Param(value = "friendUsername") String user, @Param(value = "username") String friend);


    @Query(value = "SELECT status FROM friendships " +
            "WHERE user_id IN (SELECT id FROM users WHERE username = :friendUsername) " +
            "AND friend_id IN (SELECT id FROM users WHERE username = :username)", nativeQuery = true)
    String getFriendshipStatus(@Param("username") String username, @Param("friendUsername") String friendUsername);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM friendships " +
            "WHERE user_id IN (SELECT id FROM users WHERE username = :friendUsername) " +
            "AND friend_id IN (SELECT id FROM users WHERE username = :username)", nativeQuery = true)
    void deleteFriendship(@Param("username") String username, @Param("friendUsername") String friend);


//    @Query(value = "SELECT u.username " +
//            "FROM users u " +
//            "JOIN friendships f ON u.id = f.friend_id " +
//            "WHERE f.user_id = (SELECT id FROM users WHERE username = :username) " +
//            "AND f.status = 'ACCEPTED'",nativeQuery = true)
//    List<String> getListOfFriends(@Param(value = "username") String username);


    @Query(value = "SELECT u.username, us.status, us.last_active_at " +
            "FROM users u " +
            "JOIN friendships f ON u.id = f.friend_id " +
            "JOIN user_status us ON u.id = us.user_id " +
            "WHERE f.user_id = (SELECT id FROM users WHERE username = :username) " +
            "AND f.status = 'ACCEPTED'", nativeQuery = true)
    List<Object[]> getListOfFriends(@Param(value = "username") String username);


    @Modifying
    @Transactional
    @Query(value = "UPDATE friendships " +
            "SET status = :newStatus " +
            "WHERE " +
            "(user_id = (SELECT id FROM users WHERE username = :user) " +
            "AND friend_id = (SELECT id FROM users WHERE username = :friend))", nativeQuery = true)
    void changeFriendshipStatusByUsernames(@Param("user") String user,
                                           @Param("friend") String friend,
                                           @Param("newStatus") String newStatus);


    @Modifying
    @Transactional
    @Query(value = "UPDATE friendships " +
            "SET status = 'BLOCKED' " +
            "WHERE (user_id = (SELECT id FROM users WHERE username = :username1) " +
            "       AND friend_id = (SELECT id FROM users WHERE username = :username2) " +
            "       AND status = 'ACCEPTED') " +
            "   OR " +
            "      (user_id = (SELECT id FROM users WHERE username = :username2) " +
            "       AND friend_id = (SELECT id FROM users WHERE username = :username1) " +
            "       AND status = 'ACCEPTED')", nativeQuery = true)
    void blockFriendshipByUsernames(@Param("username1") String username1,
                                    @Param("username2") String username2);


    @Query(nativeQuery = true, value = "UPDATE friendships " +
            "SET last_update = CURRENT_TIMESTAMP " +
            "WHERE user_id IN (SELECT u.id FROM users u WHERE u.username = :userId) " +
            "AND friend_id IN (SELECT u.id FROM users u WHERE u.username = :friendId)")
    void updateLastUpdateByUsername(@Param(value = "userId") String userIdUsername, @Param(value = "friendId") String friendIdUsername);


    @Query(value = "SELECT status FROM friendships " +
            "WHERE user_id IN (SELECT id FROM users WHERE username = :username) " +
            "AND friend_id IN (SELECT id FROM users WHERE username = :friendUsername)", nativeQuery = true)
    String friendShipStatus(@Param("username") String username, @Param("friendUsername") String friendUsername);


}


