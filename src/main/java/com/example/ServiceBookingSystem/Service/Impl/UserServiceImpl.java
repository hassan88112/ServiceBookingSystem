package com.example.ServiceBookingSystem.Service.Impl;

import com.example.ServiceBookingSystem.Models.Security.UserRole;
import com.example.ServiceBookingSystem.Models.User;
import com.example.ServiceBookingSystem.Repository.RoleRepos;
import com.example.ServiceBookingSystem.Repository.UserRepos;
import com.example.ServiceBookingSystem.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Author : hassan shalash
 *
 * 25/5/2023
 *
 * */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepos userRepos;

    @Autowired
    private RoleRepos roleRepos;



    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public void save(User user) {
        userRepos.save(user);
    }

    public User findByUsername(String username) {
        return userRepos.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepos.findByEmail(email);
    }





    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(email)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (findByUsername(username) != null  ) {
            return true;
        }

        return false;
    }

    public boolean checkEmailExists(String email) {
        if (findByEmail(email) != null  ) {
            return true;
        }

        return false;
    }

    public User createUser(User user, Set<UserRole> userRoles) {

        try {
            User localUser = userRepos.findByUsername(user.getUsername());

            if (localUser != null) {
                LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
            } else {
                localUser=new User();
                String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encryptedPassword);

//                for (UserRole ur : userRoles) {
//                    roleRepos.save(ur.setRole(1));
//                }

                user.getUserRoles().addAll(userRoles);
                user.setUserId(user.getUserId());
                user.setFirstName(user.getFirstName());
                user.setLastName(user.getLastName());
                user.setEmail(user.getEmail());
                user.setPhone(user.getPhone());
                user.setUsername(user.getUsername());

                localUser = userRepos.saveAndFlush(user);
            }

            return localUser;
        }catch (Exception e){
            System.out.println("error is "+e);
        }
       return null;
    }

}
