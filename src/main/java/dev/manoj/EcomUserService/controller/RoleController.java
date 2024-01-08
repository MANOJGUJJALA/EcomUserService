package dev.manoj.EcomUserService.controller;

import dev.manoj.EcomUserService.dto.CreateRoleRequestDto;
import dev.manoj.EcomUserService.modal.Role;
import dev.manoj.EcomUserService.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.CacheRequest;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto){

        Role role=roleService.createRole(createRoleRequestDto.getName());

        return new ResponseEntity<>(role, HttpStatus.OK);

    }

}
