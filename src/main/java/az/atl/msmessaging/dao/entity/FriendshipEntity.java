package az.atl.msmessaging.dao.entity;

import az.atl.msmessaging.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "friendships",
        indexes = @Index(name = "user_friend_id_unique",columnList = "user_id,friend_id",unique = true)

)
public class FriendshipEntity {

    @Id
    @Column(name = "friendship_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity userId;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = UserEntity.class)
    @JoinColumn(name = "friend_id",referencedColumnName = "id")
    private UserEntity friendId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "created_at")
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendshipEntity that = (FriendshipEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(friendId, that.friendId) && status == that.status && Objects.equals(lastUpdate, that.lastUpdate) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, friendId, status, lastUpdate, createdAt);
    }
}
