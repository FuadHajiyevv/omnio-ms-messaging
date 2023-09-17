package az.atl.msmessaging.service;

import az.atl.msmessaging.dto.response.DeleteResponse;
import az.atl.msmessaging.dto.response.UpdateResponse;

public interface AgentProfileService {

    UpdateResponse updateUsername(String username);

    DeleteResponse deleteUser();

}
