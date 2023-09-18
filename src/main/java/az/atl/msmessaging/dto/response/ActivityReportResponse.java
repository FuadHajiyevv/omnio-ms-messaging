package az.atl.msmessaging.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityReportResponse {

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "total_friends")
    private Long totalFriends;

    @JsonProperty(value = "blocked_users")
    private Long blockedUsers;

    @JsonProperty(value = "active_friends")
    private Long activeFriends;

    @JsonProperty(value = "total_messages")
    private Long totalMessages;

    @JsonProperty(value = "total_chats")
    private Long totalChats;

    @JsonProperty(value = "average_response_time")
    private Double averageResponseTime;
}
