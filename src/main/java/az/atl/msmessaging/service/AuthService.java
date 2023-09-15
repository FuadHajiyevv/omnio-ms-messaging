package az.atl.msmessaging.service;

import az.atl.msmessaging.dto.request.SwitchStatusRequest;
import az.atl.msmessaging.dto.request.UserAuthRequest;
import az.atl.msmessaging.dto.response.AuthResponse;
import az.atl.msmessaging.dto.response.SwitchResponse;

public interface AuthService {

     AuthResponse saveNewRegisterUser(UserAuthRequest user);

     SwitchResponse switchStatus(SwitchStatusRequest request);
}

