package com.github.martmatix.pproproject.controllers.admin;

import com.github.martmatix.pproproject.DTOs.TicketDTO;
import com.github.martmatix.pproproject.custom_authorities.Role;
import com.github.martmatix.pproproject.database.entities.RecordEntity;
import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.database.entities.embeddable.RecordType;
import com.github.martmatix.pproproject.services.RecordService;
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
    private RecordService recordService;

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

    @GetMapping(path = "/admin/viewRecords/{username}")
    public String viewUserRecords(@PathVariable String username, Model model, Principal principal) {
        Optional<UserEntity> user = userService.getUser(username);
        if (user.isEmpty()) {
            return "redirect:/admin?status=userRetrieveFail";
        }
        if (username.equals(principal.getName())) {
            return "redirect:/admin?status=sameUser";
        }
        List<RecordEntity> recordsForUser = recordService.findRecordsForUser(username);
        model.addAttribute("records", recordsForUser);
        return "/administrator/userRecordsPage";
    }

    @PostMapping(path = "/admin/createTicket")
    public String createTicket(@ModelAttribute TicketDTO ticketDTO, Principal principal) {
        if (ticketDTO.getName().trim().isEmpty() || ticketDTO.getName().trim().isBlank()) {
            return "redirect:/admin?status=ticketNameEmpty";
        }
        if (ticketDTO.getDescription().trim().isEmpty() || ticketDTO.getDescription().trim().isBlank()) {
            return "redirect:/admin?status=ticketDescriptionEmpty";
        }
        Optional<RecordType> ticketByName = recordTypeService.findByName(ticketDTO.getName());
        if (ticketByName.isPresent()) {
            return "redirect:/admin?status=ticketNameTaken";
        }
        RecordType recordType = new RecordType();
        recordType.setName(ticketDTO.getName());
        recordType.setDescription(ticketDTO.getDescription());
        recordType.setClosed(false);
        recordType.setIssuer(principal.getName());
        recordTypeService.save(recordType);
        return "redirect:/admin?status=ticketCreated";
    }

    @PostMapping(path = "/admin/completeTicket/{ticketName}")
    public String closeTicket(@PathVariable String ticketName) {
        Optional<RecordType> ticketByName = recordTypeService.findByName(ticketName);
        if (ticketByName.isEmpty()) {
            return "redirect:/admin?status=ticketNotFound";
        }
        ticketByName.get().setClosed(true);
        recordTypeService.save(ticketByName.get());
        return "redirect:/admin?status=ticketClosed";
    }

    @PostMapping(path = "/admin/removeTicket/{ticketName}")
    public String removeTicket(@PathVariable String ticketName) {
        Optional<RecordType> ticketByName = recordTypeService.findByName(ticketName);
        if (ticketByName.isEmpty()) {
            return "redirect:/admin?status=ticketNotFound";
        }
        List<RecordEntity> byRecordType = recordService.findByRecordType(ticketByName.get());
        if (byRecordType.isEmpty()) {
            recordTypeService.deleteTicket(ticketByName.get());
            return "redirect:/admin?status=ticketDeleted";
        }
        return "redirect:/admin?status=ticketUsed";
    }

    @PostMapping(path = "/admin/reopenTicket/{ticketName}")
    public String reopenTicket(@PathVariable String ticketName) {
        Optional<RecordType> ticketByName = recordTypeService.findByName(ticketName);
        if (ticketByName.isEmpty()) {
            return "redirect:/admin?status=ticketNotFound";
        }
        ticketByName.get().setClosed(false);
        recordTypeService.save(ticketByName.get());
        return "redirect:/admin?status=ticketReopened";
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

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }
}
