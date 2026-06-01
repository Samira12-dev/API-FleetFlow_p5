package com.fleetflow.service;

import com.fleetflow.dto.LoginRequestDto;
import com.fleetflow.dto.RegisterRequestDto;

public interface AuthService {
    String register(RegisterRequestDto request);

    String login(LoginRequestDto request);
}
