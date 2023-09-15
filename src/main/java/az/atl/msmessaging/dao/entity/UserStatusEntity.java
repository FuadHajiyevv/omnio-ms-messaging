package az.atl.msmessaging.dao.entity;

import az.atl.msmessaging.enums.Status;
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
@Table(name = "user_status")
public class UserStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_status_id")
    private Long userStatusId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "last_active_at")
    private Date lastActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatusEntity that = (UserStatusEntity) o;
        return Objects.equals(userStatusId, that.userStatusId) && Objects.equals(userId, that.userId) && status == that.status && Objects.equals(lastActive, that.lastActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userStatusId, userId, status, lastActive);
    }
}
