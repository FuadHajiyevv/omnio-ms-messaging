package az.atl.msmessaging.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private UserStatusEntity userStatus;

    @OneToMany(mappedBy = "senderId", cascade = CascadeType.ALL)
    private List<MessageEntity> sentMessages;

    @OneToMany(mappedBy = "receiverId", cascade = CascadeType.ALL)
    private List<MessageEntity> receivedMessages;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<FriendshipEntity> user;

    @OneToMany(mappedBy = "friendId", cascade = CascadeType.ALL)
    private List<FriendshipEntity> friend;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private ActivityReportEntity activityReport;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity entity = (UserEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(username, entity.username) && Objects.equals(phoneNumber, entity.phoneNumber) && Objects.equals(createdAt, entity.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, phoneNumber, createdAt);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
