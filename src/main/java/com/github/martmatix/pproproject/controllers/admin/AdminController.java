package com.github.martmatix.pproproject.controllers.admin;

import com.github.martmatix.pproproject.DTOs.TicketDTO;
import com.github.martmatix.pproproject.custom_authorities.Role;
import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;
import com.github.martmatix.pproproject.services.RecordTypeService;
import com.github.martmatix.pproproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private UserService userService;
    private RecordTypeService recordTypeService;

    @GetMapping(path = "/admin")
    public String returnAdminPage(Model model) {
        List<UserEntity> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);

        List<RecordType> recordTypes = recordTypeService.findAllRecordTypes();
        model.addAttribute("recordTypes", recordTypes);
        return "/administrator/adminPage";
    }

    @PostMapping(path = "/admin/approve/{username}")
    public String approveUser(@PathVariable String username, Principal principal) {
        String returnPath = validateByUsername(username, principal);
        if (returnPath != null) {
            return returnPath;
        }
        Optional<UserEntity> user = userService.getUser(username);
        if (user.isEmpty()) {
            return "redirect:/admin?status=userRetrieveFail";
        }
        UserEntity userEntity = user.get();
        userEntity.setEnabled(true);
        userService.saveUser(userEntity);
        return "redirect:/admin?status=approved";
    }

    @PostMapping(path = "/admin/revoke/{username}")
    public String revokeUser(@PathVariable String username, Principal principal) {
        String returnPath = validateByUsername(username, principal);
        if (returnPath != null) {
            return returnPath;
        }
        Optional<UserEntity> user = userService.getUser(username);
        if (user.isEmpty()) {
            return "redirect:/admin?status=userRetrieveFail";
        }
        UserEntity userEntity = user.get();
        userEntity.setEnabled(false);
        userService.saveUser(userEntity);
        return "redirect:/admin?status=revoked";
    }

    @PostMapping(path = "/admin/elevate/{username}")
    public String elevateUser(@PathVariable String username, Principal principal) {
        String returnPath = validateByUsername(username, principal);
        if (returnPath != null) {
            return returnPath;
        }
        Optional<UserEntity> userOptional = userService.getUser(username);
        if (userOptional.isEmpty()) {
            return "redirect:/admin/status=userRetrieveFail";
        }
        UserEntity userEntity = userOptional.get();
        userEntity.setRole(Role.ADMINISTRATOR);
        userService.saveUser(userEntity);
        return "redirect:/admin?status=elevated";
    }

    @PostMapping(path = "/admin/degrade/{username}")
    public String degradeUser(@PathVariable String username, Principal principal) {
        String returnPath = validateByUsername(username, principal);
        if (returnPath != null) {
            return returnPath;
        }
        Optional<UserEntity> userOptional = userService.getUser(username);
        if (userOptional.isEmpty()) {
            return "redirect:/admin/status=userRetrieveFail";
        }
        UserEntity userEntity = userOptional.get();
        userEntity.setRole(Role.USER);
        userService.saveUser(userEntity);
        return "redirect:/admin?status=degraded";
    }

    @PostMapping(path = "/admin/delete/{username}")
    public String deleteUser(@PathVariable String username, Principal principal, RedirectAttributes redirectAttributes) {
        String returnPath = validateByUsername(username, principal);
        if (returnPath != null) {
            return returnPath;
        }
        Optional<UserEntity> userOptional = userService.getUser(username);
        if (userOptional.isEmpty()) {
            return "redirect:/admin/status=userRetrieveFail";
        }
        UserEntity userEntity = userOptional.get();
        userService.delete(userEntity);
        redirectAttributes.addFlashAttribute("deletedUsername", username);
        return "redirect:/admin?status=userDeleted";
    }

    @PostMapping(path = "/admin/createTicket")
    public String createTicket(@ModelAttribute TicketDTO ticketDTO) {
        System.out.println(ticketDTO.getName());
        return "redirect:/admin?status=ticketCreated";
    }

    private String validateByUsername(String username, Principal principal) {
        if (username.trim().isBlank() || username.trim().isEmpty()) {
            return "redirect:/admin?status=idNotValid";
        }
        if (username.equals(principal.getName())) {
            return "redirect:/admin?status=alterSelfError";
        }
        return null;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRecordTypeService(RecordTypeService recordTypeService) {
        this.recordTypeService = recordTypeService;
    }
}
