package it.itresources.springtut.springtutorial.controller;

import java.util.Optional;

import javax.validation.Valid;

import it.itresources.springtut.springtutorial.model.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import it.itresources.springtut.springtutorial.entity.UserEntity;
import it.itresources.springtut.springtutorial.mapper.UserMapper;
import it.itresources.springtut.springtutorial.model.dto.UserDTO;
import it.itresources.springtut.springtutorial.security.PrincipalUtils;
import it.itresources.springtut.springtutorial.services.impl.ServiceUserImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final ServiceUserImpl serviceUserImpl;
	
	@Autowired
	public UserController (ServiceUserImpl serviceUserImpl)
	{
		this.serviceUserImpl=serviceUserImpl;
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<String> getCreatorName(@Valid @RequestBody String createdBy ) {
        
        String fullName="";
        UserDTO user=UserMapper.entityToDto(serviceUserImpl.loadByUsername(createdBy).get());
        fullName=user.getFirstName()+""+user.getLastName();
        if (fullName!="")
        {
        	return ResponseEntity.status(HttpStatus.OK).body(fullName);
        }
        else {
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not get the user information");
        }
        
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getProfile(@PathVariable(value="id") Long id) {
        UserProfileDTO myProfile = UserMapper.entityToProfile(serviceUserImpl.loadById(id).get());
        if (myProfile.getClassrooms()==null)
        {
            myProfile.setClassrooms(serviceUserImpl.getTeacherClassrooms(id));
        }
        if (myProfile != null) {
            return ResponseEntity.ok().body(myProfile);
        }
        return ResponseEntity.badRequest().build();
    }
}
