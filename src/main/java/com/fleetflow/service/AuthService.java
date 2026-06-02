package com.fleetflow.service;

import com.fleetflow.dto.LoginRequestDto;
import com.fleetflow.dto.RegisterRequestDto;

import java.util.Map;

public interface AuthService {
    Map<String, Object> register(RegisterRequestDto request);
    Map<String, Object> login(LoginRequestDto request);
}
