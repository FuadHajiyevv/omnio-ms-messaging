package az.atl.msmessaging.dao.entity;

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
@Table(name = "activity_reports")
public class ActivityReportEntity {

    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userId;

    @Column(name = "total_messages")
    private long totalMessages;

    @Column(name = "response_time")
    private long responseTime;

    @Column(name = "total_chats")
    private long chats;

    @Column(name = "total_friends")
    private long totalFriends;

    @Column(name = "active_friends")
    private long activeFriends;

    @Column(name = "blocked_chats")
    private long blockedChats;

    @Column(name = "last_online_timestamp")
    private Date lastOnline;

    @Column(name = "last_offline_timestamp")
    private Date lastOffline;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityReportEntity entity = (ActivityReportEntity) o;
        return totalMessages == entity.totalMessages && responseTime == entity.responseTime && chats == entity.chats && totalFriends == entity.totalFriends && activeFriends == entity.activeFriends && blockedChats == entity.blockedChats && Objects.equals(reportId, entity.reportId) && Objects.equals(userId, entity.userId) && Objects.equals(lastOnline, entity.lastOnline) && Objects.equals(lastOffline, entity.lastOffline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, userId, totalMessages, responseTime, chats, totalFriends, activeFriends, blockedChats, lastOnline, lastOffline);
    }
}
