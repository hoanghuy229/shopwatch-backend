package com.example.shopwatchbackend.services.interfaces;

import com.example.shopwatchbackend.dtos.request.RoleDTO;
import com.example.shopwatchbackend.dtos.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    List<RoleResponse> getAll();
    String addRole(RoleDTO roleDTO) throws Exception;
}
