package dev.manoj.EcomUserService.service;

import dev.manoj.EcomUserService.dto.UserDto;
import dev.manoj.EcomUserService.exception.InvalidCredentialException;
import dev.manoj.EcomUserService.exception.SessionNotValidated;
import dev.manoj.EcomUserService.exception.UserNotFoundException;
import dev.manoj.EcomUserService.modal.Session;
import dev.manoj.EcomUserService.modal.SessionStatus;
import dev.manoj.EcomUserService.modal.User;
import dev.manoj.EcomUserService.repository.SessionRepository;
import dev.manoj.EcomUserService.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.rmi.server.ServerNotActiveException;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {

        private UserRepository userRepository;
        private SessionRepository sessionRepository;

        private BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String email,String password){
        Optional<User>userOPtional=userRepository.findByEmail(email);

        if(userOPtional.isEmpty()){
              throw new UserNotFoundException("User Not Found with given email");
        }
        User user=userOPtional.get();

        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new InvalidCredentialException("Credentails are wroung");
        }
        //removing previous sessions or just make sessionstatus as inactive
      Optional< List<Session>>allSessions= sessionRepository.findByUser_Id(user.getId());
            List<Session>allUserSesions=allSessions.get();
        if(!allSessions.isEmpty()){
            for(Session session:allUserSesions){
                if(session.getSessionStatus().equals(SessionStatus.ACTIVE)){
                    session.setSessionStatus(SessionStatus.INACTIVE);
                }
            }
            sessionRepository.saveAll(allUserSesions);

        }

        Map<String ,Object> jsonForJwt=new HashMap<>();

        jsonForJwt.put("email",user.getEmail());
        jsonForJwt.put("roles",user.getRoles());
        jsonForJwt.put("createdAt",new Date());
        jsonForJwt.put("expiryAt",new Date(LocalDate.now().plusDays(3).toEpochDay()));

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key=alg.key().build();

        String token=Jwts.builder()
                .claims(jsonForJwt)
                .signWith(key,alg)
                .compact();

        Session session=new Session();

        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        session.setLoginAt(new Date());

        sessionRepository.save(session);

        UserDto userDto=UserDto.from(user);

        MultiValueMapAdapter<String ,String >headers=new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE,token);
        return new ResponseEntity<>(userDto,headers, HttpStatus.OK);



    }

    public ResponseEntity<Void> logout(String token,Long userid){
            Optional<Session>sessionOPtonal=    sessionRepository.findByTokenAndUser_Id(token,userid);

            if(sessionOPtonal.isEmpty()){
                return null;
            }
            Session session=sessionOPtonal.get();
            session.setSessionStatus(SessionStatus.INACTIVE);
            sessionRepository.save(session);

            return ResponseEntity.ok().build();
    }
    public ResponseEntity<SessionStatus>validate(String token,Long userId)  {
        Optional<Session>sesionOptional=sessionRepository.findByTokenAndUser_Id(token,userId);
        if(sesionOptional.isEmpty() || sesionOptional.get().getSessionStatus().equals(SessionStatus.INACTIVE)){
            throw  new SessionNotValidated("Not valiadted");
        }
        return new ResponseEntity<>(SessionStatus.ACTIVE,HttpStatus.OK);

    }

    public UserDto singnUp(String email, String password){

        User user=new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User userRepo=userRepository.save(user);

        return UserDto.from(userRepo);

    }

    public List<Session> getAllSesions(){

        return sessionRepository.findAll();
    }
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

}
