package com.majumbum.youtube_clone.scopes.general;


import com.majumbum.youtube_clone.scopes.user.entities.TestUser;
import com.majumbum.youtube_clone.scopes.user.entities.User;
import com.majumbum.youtube_clone.scopes.user.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Initializer {
    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    public UserService userService;


    @Autowired
    public Initializer(UserService userService,
                       final @Value("${spring.jpa.hibernate.ddl-auto}") String database ,
                       final @Value("${spring.profiles.active}") String profile){

        this.userService = userService;
        if(setupDemoUsers(database)){
            setupTestUsers();
        }
    }

    private void setupTestUsers() {
        LOG.info("Creating default Test Users.");
        User user ;
        for (TestUser testUser : TestUser.values()) {
            user = new User(testUser.firstname , testUser.lastname , testUser.email, testUser.nickname,
                    testUser.password , testUser.grantedAuthorities , testUser.gender);

            userService.generateAndSaveNewValidationTokenForUser(user);
            userService.rehashPassword(testUser.password , user);
        }
    }

    private boolean setupDemoUsers(String database){
        return "create".equals(database);
    }

}
