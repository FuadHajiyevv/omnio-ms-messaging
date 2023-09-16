package az.atl.msmessaging.service;

import az.atl.msmessaging.dto.response.*;
import org.aspectj.bridge.Message;

import java.util.List;

public interface SuperVisorMessageService {

    List<MessageResponse> getMessagesByUsername(String u1, String u2);

    List<ChatListResponse> getChatsOfUserByUsername(String username);

    List<FriendListResponse> getFriendsOfUser(String username);

    ActivityReportResponse getActivityReport(String username);
}
