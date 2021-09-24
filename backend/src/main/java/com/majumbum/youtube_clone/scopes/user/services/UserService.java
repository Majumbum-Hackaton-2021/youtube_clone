package com.majumbum.youtube_clone.scopes.user.services;

import com.majumbum.youtube_clone.scopes.auth.forms.EditForm;
import com.majumbum.youtube_clone.scopes.auth.forms.SignUpDto;
import com.majumbum.youtube_clone.scopes.token.TokenServiceImpl;
import com.majumbum.youtube_clone.scopes.user.entities.Gender;
import com.majumbum.youtube_clone.scopes.user.entities.User;
import com.majumbum.youtube_clone.scopes.user.entities.UserRole;
import com.majumbum.youtube_clone.scopes.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public TokenServiceImpl tokenService;

    @Autowired
    public  PasswordEncoder passwordEncoder;


    public void saveUser(final User user){
        if(user == null) throw new NullPointerException("User must not be null");
        userRepository.save(user);
    }

    public void generateAndSaveNewValidationTokenForUser(final User user) {
        if (user == null) throw new NullPointerException("User must not be null.");
        String token = tokenService.createToken(user);
        user.setValidationToken(token);
        saveUser(user);
    }


    public void updatePassword(final String newPassword, final User user) {
        if (newPassword == null) throw new NullPointerException("NewPassword must not be null.");
        if (newPassword.isEmpty()) throw new IllegalArgumentException("NewPassword must not be empty.");
        if (user == null) throw new NullPointerException("User must not be null.");

        user.hashedPassword = passwordEncoder.encode(newPassword);
        saveUser(user);
    }

    public void addUser(SignUpDto signUpDto){
        if (signUpDto == null) throw new NullPointerException("signUpDto must not be null.");

        Gender gender;
        if(signUpDto.gender.equals("male"))
            gender = Gender.male;
        else
            gender = Gender.female;

        User user = new User(signUpDto.firstname, signUpDto.lastname, signUpDto.email, signUpDto.nickname,
                signUpDto.password, new HashSet<UserRole>(Arrays.asList(UserRole.USER)), gender);

        generateAndSaveNewValidationTokenForUser(user);
        rehashPassword(user.hashedPassword , user);
    }
    public void rehashPassword(final String password, final User user) {
        this.updatePassword(password, user);
    }

    public boolean isPasswordCorrect(final String password, final User user) {
        if (password == null) throw new NullPointerException("Password must not be null.");
        if (password.isEmpty()) throw new IllegalArgumentException("Password must not be empty.");
        if (user == null) throw new NullPointerException("User must not be null.");

        return passwordEncoder.matches(password, user.getHashedPassword());
    }

    public Optional<User> findUserByEmail(String email) {
        if (email == null) throw new NullPointerException("EMail must not be null.");
        if (email.isEmpty()) throw new NullPointerException("EMail must not be empty.");

        return userRepository.findFirstByEmail(email.toLowerCase().trim());
    }

    public Optional<User> findUserByToken(String token) {
        if (token == null) throw new NullPointerException("Token must not be null.");
        return userRepository.findFirstByEmail(tokenService.getEmailFromToken(token).toLowerCase().trim());
    }

    public Optional<User> findUserById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId;
    }

    public boolean doesEmailAlreadyExists(final String email) {
        if (email == null) throw new NullPointerException("Email must not be null.");
        if (email.isEmpty()) throw new NullPointerException("Email must not be empty.");

        return findUserByEmail(email).isPresent();
    }

    public boolean doesNicknameAlreadyExists(final String nickname) {
        if (nickname == null) throw new NullPointerException("nickname must not be null.");
        if (nickname.isEmpty()) throw new NullPointerException("nickname must not be empty.");

        return userRepository.findUsersByNickname(nickname).isPresent();
    }

    public Set<User> searchAllUsersWith(String search){
        Set<User> users = new HashSet<>();

        for(String str : search.trim().split(" ") ) {
            if(!str.isEmpty()){
                users.addAll(userRepository.findUsersByEmailContaining(str.toLowerCase()));
                users.addAll(userRepository.findUsersByFirstnameContaining(str.toLowerCase()));
                users.addAll(userRepository.findUsersByLastnameContaining(str.toLowerCase()));

                users.addAll(userRepository.findUsersByEmailContaining(str.toUpperCase()));
                users.addAll(userRepository.findUsersByFirstnameContaining(str.toUpperCase()));
                users.addAll(userRepository.findUsersByLastnameContaining(str.toUpperCase()));

                users.addAll(userRepository.findUsersByEmailContaining(capitalize(str)));
                users.addAll(userRepository.findUsersByFirstnameContaining(capitalize(str)));
                users.addAll(userRepository.findUsersByLastnameContaining(capitalize(str)));
            }
        }
        return users;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Optional<Long> id){
        if(id.isEmpty()) throw new NullPointerException("Id musst not be null");
        if(findUserById(id.get()).isEmpty()) throw new NullPointerException("The user with this Id doesn't exist");

        userRepository.deleteById(id.get());
    }
    private String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void editUser(EditForm editForm , Long id) {
        Optional<User> user = findUserById(id);
        if(user.isEmpty()) throw new NullPointerException("The user with this Id doesn't exist");

        if(passwordEncoder.matches(editForm.password, user.get().getHashedPassword())) {
            user.get().setFirstname(editForm.firstname);
            user.get().setLastname(editForm.lastname);
            user.get().setNickname(editForm.nickname);
            Gender gender;
            if(editForm.gender.equals(Gender.female.toString()))
                gender = Gender.female;
            else
                gender = Gender.male;

            user.get().setGender(gender);
            updatePassword(editForm.newPassword,user.get());
        }
    }
}
