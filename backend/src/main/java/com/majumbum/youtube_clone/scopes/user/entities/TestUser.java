package com.majumbum.youtube_clone.scopes.user.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TestUser {

    USER_ONE("admin" , "admin" , "admin@mail.com" ,
            "admin" , "test", Gender.male,
            new HashSet<UserRole>(Arrays.asList(UserRole.USER , UserRole.ADMIN , UserRole.MODERATOR))),

    USER_TWO("moderator" , "moderator" , "moderator@mail.com" ,
            "moderator" , "test", Gender.female,
            new HashSet<UserRole>(Arrays.asList(UserRole.USER , UserRole.MODERATOR))),
    USER_THREE("user" , "user" , "user@mail.com" ,
                     "user" , "test", Gender.female,
                     new HashSet<UserRole>(Arrays.asList(UserRole.USER , UserRole.MODERATOR)));


    public final String firstname;
    public final String lastname;
    public final String email;
    public final String nickname;
    public final String password;
    public final Gender gender;
    public final Set<UserRole> grantedAuthorities;
    TestUser(String firstname , String lastname , String email , String nickname ,String password , Gender gender,
             Set<UserRole> grantedAuthorities){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.gender = gender;
        this.grantedAuthorities = grantedAuthorities;

    }
}