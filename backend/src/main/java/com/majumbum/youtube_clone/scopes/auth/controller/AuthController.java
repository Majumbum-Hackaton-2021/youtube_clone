package com.majumbum.youtube_clone.scopes.auth.controller;

import com.majumbum.youtube_clone.scopes.auth.forms.EditForm;
import com.majumbum.youtube_clone.scopes.auth.forms.LoginDto;
import com.majumbum.youtube_clone.scopes.auth.forms.SignUpDto;
import com.majumbum.youtube_clone.scopes.auth.payload.response.JwtResponse;
import com.majumbum.youtube_clone.scopes.auth.payload.response.MessageResponse;
import com.majumbum.youtube_clone.scopes.security.CustomAuthenticationProvider;
import com.majumbum.youtube_clone.scopes.security.StaticUtils;
import com.majumbum.youtube_clone.scopes.token.TokenServiceImpl;
import com.majumbum.youtube_clone.scopes.user.entities.User;
import com.majumbum.youtube_clone.scopes.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public UserService userService;

    @Autowired
    TokenServiceImpl tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid final LoginDto loginDto){
        Authentication authentication ;
        try {
            authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        }catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Email not Found or Password invalid"));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        // Public Key
        String jwt = tokenService.createToken(user);

        List<String> roles = user.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                roles,
                user.getFirstname(),
                user.getLastname(),
                user.getGender()
                ));
        }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody SignUpDto signUpDto) {

        if (userService.doesEmailAlreadyExists(signUpDto.getEmail()) ||
        userService.doesNicknameAlreadyExists(signUpDto.getNickname())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Pseudo or Email is already taken!"));
        }

        userService.addUser(signUpDto);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/info")
    public ResponseEntity<?> userInformations(@RequestHeader (name="Authorization") Optional<String> token) {

        if(token.isEmpty())
            ResponseEntity.badRequest().body(new MessageResponse("An Error occured"));

        try{
            Optional<User> user = userService.findUserByToken(token.get());
            if(user.isEmpty()){
                return ResponseEntity.ok(new MessageResponse("There are no users with this Token"));
            }
            return ResponseEntity.ok(user.get());

        }catch (Exception ignored){
            return ResponseEntity.ok(new MessageResponse("An Error occured"));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader (name="Authorization") Optional<String> token) {

        if(token.isEmpty())
            ResponseEntity.badRequest().body(new MessageResponse("An Error occured"));
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader (name="Authorization") Optional<String> token , @PathVariable Optional <Long> userId) {

        if(token.isEmpty())
            ResponseEntity.badRequest().body(new MessageResponse("An Error occured"));

        if(userId.isEmpty())
            ResponseEntity.badRequest().body(new MessageResponse("UserId is Empty"));

        userService.deleteUser(userId);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/editUser/{userId}")
    public ResponseEntity<?> editUser(@RequestHeader (name="Authorization") Optional<String> token , @PathVariable Optional <Long> userId ,
                                      @RequestBody EditForm editForm) {
        if(token.isEmpty())
            ResponseEntity.badRequest().body(new MessageResponse("An Error occured"));

        if(userId.isEmpty())
            ResponseEntity.badRequest().body(new MessageResponse("UserId is Empty"));

        if(editForm == null)
            ResponseEntity.badRequest().body(new MessageResponse("Editform is null"));

        System.out.println(editForm);

        userService.editUser(editForm , userId.get());
        return ResponseEntity.ok(userService.findUserById(userId.get()));
    }


    @GetMapping("/logout")
    public ResponseEntity<?> handleLogout(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                StaticUtils.logoutAndInvalidateSession();
            } catch (ServletException e) {
                logger.error("Error at log out of '" + authentication.getName() + "'.", e);
                return ResponseEntity.ok(new MessageResponse("Error during logout"));
            }
        }
        return ResponseEntity.ok(new MessageResponse("Logout successfully"));
    }
}
