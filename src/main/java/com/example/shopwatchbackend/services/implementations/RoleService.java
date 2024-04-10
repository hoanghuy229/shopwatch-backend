package com.example.shopwatchbackend.services.implementations;

import com.example.shopwatchbackend.dtos.request.RoleDTO;
import com.example.shopwatchbackend.models.Role;
import com.example.shopwatchbackend.repositories.RoleRepository;
import com.example.shopwatchbackend.dtos.response.RoleResponse;
import com.example.shopwatchbackend.services.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RoleResponse> getAll() {
        List<Role> roleList = roleRepository.findAll();

        return  roleList.stream().map(role -> modelMapper.map(role,RoleResponse.class)).toList();
    }

    @Override
    @Transactional
    public String addRole(RoleDTO roleDTO) throws Exception {
        boolean exist = roleRepository.existsByRoleName(roleDTO.getRoleName());
        if(exist){
            throw new Exception("role exist");
        }
        Role role = Role.builder().roleName(roleDTO.getRoleName()).build();
        roleRepository.save(role);
        return "add success";
    }
}
