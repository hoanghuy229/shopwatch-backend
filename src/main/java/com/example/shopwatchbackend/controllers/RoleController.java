package com.example.shopwatchbackend.controllers;

import com.example.shopwatchbackend.dtos.request.RoleRequest;
import com.example.shopwatchbackend.dtos.response.RoleResponse;
import com.example.shopwatchbackend.services.interfaces.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService iRoleService;


    @GetMapping()
    public ResponseEntity<?> getAll(){
        List<RoleResponse> roleResponseList = iRoleService.getAll();
        return ResponseEntity.ok(roleResponseList);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleRequest roleRequest) throws Exception {
        try{
            String result = iRoleService.addRole(roleRequest);
            return ResponseEntity.ok().body(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
