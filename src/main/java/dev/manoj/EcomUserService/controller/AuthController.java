package dev.manoj.EcomUserService.controller;

import dev.manoj.EcomUserService.dto.LoginRequestDto;
import dev.manoj.EcomUserService.dto.SignUpRequestDto;
import dev.manoj.EcomUserService.dto.UserDto;
import dev.manoj.EcomUserService.dto.ValidateRequestDto;
import dev.manoj.EcomUserService.modal.Session;
import dev.manoj.EcomUserService.modal.SessionStatus;
import dev.manoj.EcomUserService.modal.User;
import dev.manoj.EcomUserService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request){

       return authService.login(request.getEmail(),request.getPassword());
//    return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @PostMapping("/signup")

    public ResponseEntity<UserDto>signUP(@RequestBody SignUpRequestDto request){
        System.out.println("hi hello");
        UserDto userDto=    authService.singnUp(request.getEmail(),request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logout(@PathVariable("id") Long userId,@RequestHeader("token")String token){

       return authService.logout(token,userId);

    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateRequestDto request){
     return    authService.validate(request.getToken(),request.getUserId());
    }
    @GetMapping("/session")

    public ResponseEntity<List<Session>>getAllSessions(){
        return new ResponseEntity<>(authService.getAllSesions(),HttpStatus.OK);
    }

    @GetMapping("/users")

    public ResponseEntity<List<User>>getAllUsers(){
        return new ResponseEntity<>(authService.getAllUsers(),HttpStatus.OK);
    }

}
