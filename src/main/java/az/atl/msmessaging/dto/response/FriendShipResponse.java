package az.atl.msmessaging.dto.response;

import az.atl.msmessaging.enums.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FriendShipResponse {

    private FriendshipStatus status;

}
