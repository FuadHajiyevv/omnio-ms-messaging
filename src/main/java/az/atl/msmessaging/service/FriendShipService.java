package az.atl.msmessaging.service;

import az.atl.msmessaging.dto.request.AcceptFriendShipRequest;
import az.atl.msmessaging.dto.request.FriendShipRequest;
import az.atl.msmessaging.dto.response.AcceptFriendShipResponse;
import az.atl.msmessaging.dto.response.FriendListResponse;
import az.atl.msmessaging.dto.response.FriendShipResponse;

import java.util.List;

public interface FriendShipService {

    FriendShipResponse sendFriendshipRequest(FriendShipRequest request);
    FriendShipResponse acceptFriendshipRequest(AcceptFriendShipRequest request);
    FriendShipResponse rejectFriendshipRequest(String username);
    List<FriendListResponse> listFriends();
    List<AcceptFriendShipResponse> friendshipRequests();
    FriendShipResponse blockUser(String username);

    FriendShipResponse unblockUser(String username);


}
