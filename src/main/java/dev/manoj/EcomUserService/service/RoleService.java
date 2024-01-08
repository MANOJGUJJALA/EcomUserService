package dev.manoj.EcomUserService.service;

import dev.manoj.EcomUserService.modal.Role;
import dev.manoj.EcomUserService.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name){
        Role role=new Role();
        role.setRole(name);

      return  roleRepository.save(role);
    }
}
