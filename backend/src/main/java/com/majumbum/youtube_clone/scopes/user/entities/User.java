package com.majumbum.youtube_clone.scopes.user.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails , Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column
    @NotBlank
    private String firstname;

    @Column
    @NotBlank
    @NotEmpty
    @NotNull
    private String lastname;
    
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String email;

    @Column
    @NotBlank
    @NotNull
    private String nickname;

    @Column
    @NotBlank
    @NotNull
    private String image;

    @Column
    @NotEmpty
    @NotNull
    public String hashedPassword;

    @Column
    @NotEmpty
    @NotNull
    public Gender gender;

    @Column
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Set<UserRole> grantedAuthorities = new HashSet<>();


    @Column
    private String validationToken;

    @Column
    @ElementCollection
    private Set<Long> savedVideoId = new HashSet<>();

    @SuppressWarnings("unused")
    public User(){};

    public User(String firstname , String lastname , String email , String nickname , String password , Set<UserRole> grantedAuthorities , Gender gender){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.nickname = nickname;
        this.hashedPassword = password;
        this.grantedAuthorities = grantedAuthorities;
        this.gender = gender;
        this.image = "https://avatars.dicebear.com/api/avataaars/"+gender.toString()+"/"+ (int)((Math.random() * 500) + 1) +".svg";
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String pseudo) {
        this.nickname = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Set<UserRole> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<UserRole> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.grantedAuthorities == null) {
            return Collections.emptySet();
        } else {
            return this.grantedAuthorities.stream()
                    .map(ga -> new SimpleGrantedAuthority(ga.toString()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getValidationToken() {
        return validationToken;
    }

    public void setValidationToken(String validationToken) {
        this.validationToken = validationToken;
    }

    public boolean hasAutorithy(String auth){
      for(UserRole role : getGrantedAuthorities()){
          if(role.toString().equals(auth.toUpperCase()))
              return true;
      }
      return false;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Long> getSavedVideoId() {
        return savedVideoId;
    }

    public void setSavedVideoId(Set<Long> savedVideoId) {
        this.savedVideoId = savedVideoId;
    }

    public void addVideoId(Long id){
        if(!savedVideoId.contains(id))
            this.savedVideoId.add(id);
    }

    public void removeVideoId(Long id){
        if(savedVideoId.contains(id))
            this.savedVideoId.remove(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", grantedAuthorities=" + grantedAuthorities +
                ", validationToken='" + validationToken + '\'' +
                '}';
    }
}
