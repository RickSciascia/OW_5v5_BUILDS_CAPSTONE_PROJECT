package ricksciascia.ow_5v5_build.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ricksciascia.ow_5v5_build.dto.UpdatedUserDTO;
import ricksciascia.ow_5v5_build.dto.UserDTO;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.services.UsersService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "username") String orderBy){
        return this.usersService.findAllUsers(page, size, orderBy);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
    return this.usersService.findById(userId);
    }

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PatchMapping("/me")
    public User updateCurrentUser(@AuthenticationPrincipal User currentUser, @RequestBody UpdatedUserDTO dto) {
        return this.usersService.updateUser(currentUser.getId(), dto);
    }
}
