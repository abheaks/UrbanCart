package com.projects.urbancart.mongoController;

import com.projects.urbancart.mongoModel.User;
import com.projects.urbancart.mongoService.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) {
        System.out.println(id);
        return userService.getUser(id);
    }


    @PostMapping()
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
