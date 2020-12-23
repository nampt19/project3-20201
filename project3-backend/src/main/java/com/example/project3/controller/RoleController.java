package com.example.project3.controller;

import com.example.project3.domain.request.CreateRoleRequest;
import com.example.project3.domain.response.*;
import com.example.project3.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @CrossOrigin(origins = "*")
    @PostMapping("/createRole")
    public ResponseEntity<CreateRoleResponse> createRole(@RequestHeader("Authorization") String token, @RequestBody CreateRoleRequest createRoleRequest) {
        return ResponseEntity.ok(roleService.createRole(token, createRoleRequest));
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/editRole")
    public ResponseEntity<EditRoleResponse> editRole(@RequestHeader("Authorization") String token, @RequestBody CreateRoleRequest createRoleRequest) {
        return ResponseEntity.ok(roleService.editRole(token, createRoleRequest));
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/deleteRole/{idRole}")
    public ResponseEntity<BaseResponse> deleteRole(@RequestHeader("Authorization") String token, @PathVariable int idRole) {
        return ResponseEntity.ok(roleService.deleteRole(token, idRole));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/roleList")
    public ResponseEntity<RoleListResponse> getAllRole(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(roleService.getAllRole(token));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getRolePageAction/{idRole}")
    public ResponseEntity<RolePageListActionResponse> getRolePageActionByIdRole(@RequestHeader("Authorization") String token, @PathVariable int idRole) {
        return ResponseEntity.ok(roleService.getRolePageAction(token,idRole));
    }


}
