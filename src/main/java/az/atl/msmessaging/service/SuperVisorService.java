package az.atl.msmessaging.service;

import az.atl.msmessaging.dto.response.DeleteResponse;

public interface SuperVisorService {

    DeleteResponse deleteByUsername(String username);
}
