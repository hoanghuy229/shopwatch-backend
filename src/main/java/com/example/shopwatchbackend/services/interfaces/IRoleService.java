package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.RoleRequest;
import com.example.shopwatchbackend.dtos.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    List<RoleResponse> getAll();
    String addRole(RoleRequest roleRequest) throws Exception;
}
