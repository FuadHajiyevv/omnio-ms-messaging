package az.atl.msmessaging.service;

import az.atl.msmessaging.dto.request.MessageRequest;
import az.atl.msmessaging.dto.response.ChatListResponse;
import az.atl.msmessaging.dto.response.DeliverResponse;
import az.atl.msmessaging.dto.response.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    DeliverResponse sendMessage(MessageRequest request);
    List<MessageResponse> getMessages(String chat);

    List<ChatListResponse> getChatList();


}
