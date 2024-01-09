package dev.manoj.EcomUserService.controller;

import dev.manoj.EcomUserService.dto.LoginRequestDto;
import dev.manoj.EcomUserService.dto.SignUpRequestDto;
import dev.manoj.EcomUserService.dto.UserDto;
import dev.manoj.EcomUserService.modal.Session;
import dev.manoj.EcomUserService.modal.User;
import dev.manoj.EcomUserService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
    @GetMapping("/session")

    public ResponseEntity<List<Session>>getAllSessions(){
        return new ResponseEntity<>(authService.getAllSesions(),HttpStatus.OK);
    }

    @GetMapping("/users")

    public ResponseEntity<List<User>>getAllUsers(){
        return new ResponseEntity<>(authService.getAllUsers(),HttpStatus.OK);
    }

}