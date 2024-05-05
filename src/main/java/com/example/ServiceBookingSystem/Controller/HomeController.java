package com.example.ServiceBookingSystem.Controller;



import com.example.ServiceBookingSystem.Models.Security.UserRole;
import com.example.ServiceBookingSystem.Models.User;
import com.example.ServiceBookingSystem.Repository.RoleRepos;
import com.example.ServiceBookingSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepos roleRepos;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model){

        User user=new User();
        model.addAttribute("user",user);

        return "signup";
    }
    @PostMapping("/signup")
    public String signupPost(@ModelAttribute ("user") User user, Model model){

        try {
            if(userService.checkUserExists(user.getUsername(), user.getEmail()))  {

                if (userService.checkEmailExists(user.getEmail())) {
                    model.addAttribute("emailExists", true);
                }

                if (userService.checkUsernameExists(user.getUsername())) {
                    model.addAttribute("usernameExists", true);
                }

                return "signup";
            } else {
                Set<UserRole> userRoles = new HashSet<>();
                userRoles.add(new UserRole(user,roleRepos.findByName("ROLE_USER")));

                userService.createUser(user, userRoles);

                return "redirect:/";
            }
        }catch (Exception e){
            System.out.println("error is ++++++ "+e);
        }
        return null;
    }

    @GetMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        User user=userService.findByUsername(principal.getName());
        /*PrimaryAccount primaryAccount=user.getPrimaryAccount();
        SavingsAccount savingsAccount=user.getSavingsAccount();
        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);*/
        model.addAttribute("user",user);

        return "userFront";
    }


}
