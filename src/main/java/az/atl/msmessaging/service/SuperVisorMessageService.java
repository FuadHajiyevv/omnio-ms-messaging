package az.atl.msmessaging.service;

import az.atl.msmessaging.dto.response.ActivityReportResponse;
import az.atl.msmessaging.dto.response.ChatListResponse;
import az.atl.msmessaging.dto.response.FriendListResponse;
import az.atl.msmessaging.dto.response.MessageResponse;

import java.util.List;

public interface SuperVisorMessageService {

    List<MessageResponse> getMessagesByUsername(String u1, String u2);

    List<ChatListResponse> getChatsOfUserByUsername(String username);

    List<FriendListResponse> getFriendsOfUser(String username);

    ActivityReportResponse getActivityReport(String username);
}
