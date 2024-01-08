package dev.manoj.EcomUserService.service;

import dev.manoj.EcomUserService.dto.UserDto;
import dev.manoj.EcomUserService.modal.Role;
import dev.manoj.EcomUserService.modal.User;
import dev.manoj.EcomUserService.repository.RoleRepository;
import dev.manoj.EcomUserService.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(Long userId){

        Optional<User> user=userRepository.findById(userId);

        if(user.isEmpty()){
            return null;
        }
        return UserDto.from( user.get());
    }

    public UserDto setuserRoles(Long userId, List<Long> roleIds){

        Optional<User>optionalUser=userRepository.findById(userId);
        Set<Role> roles=roleRepository.findAllByIdIn(roleIds);

        if(optionalUser.isEmpty()){
            return null;
        }

        User user=optionalUser.get();

        user.setRoles( roles);

        User userSaved=userRepository.save(user);

        return UserDto.from(userSaved);

    }

}
