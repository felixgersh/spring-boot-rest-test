package nz.gershunov.testtask.pccw.controller;

import nz.gershunov.testtask.pccw.exception.ResourceNotFoundException;
import nz.gershunov.testtask.pccw.exception.BadRequestException;
import nz.gershunov.testtask.pccw.model.User;
import nz.gershunov.testtask.pccw.model.UserEmail;
import nz.gershunov.testtask.pccw.model.UserFull;
import nz.gershunov.testtask.pccw.repository.UserRepository;
import nz.gershunov.testtask.pccw.repository.UserEmailRepository;
import nz.gershunov.testtask.pccw.email.SendEmailSMTP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.PersistenceContext;
//import javax.persistence.EntityManager;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.lang.Long;


@RestController
@RequestMapping("/api/v1")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEmailRepository userEmailRepository;

    @Autowired
    private Environment environment;

//    @PersistenceContext
//    private EntityManager entityManager;

    @GetMapping("/users")
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    protected User getUserByStringId(String userIdStr) throws BadRequestException, ResourceNotFoundException
    {
        Long userId;
        try {
            userId = Long.valueOf(userIdStr);
        } catch (NumberFormatException nfe) {
            throw new BadRequestException("User ID should be numeric");
        }
        return userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found"));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserFull> getUserById(@PathVariable(value = "id") String userIdStr)
        throws BadRequestException, ResourceNotFoundException
    {
        User user = getUserByStringId(userIdStr);

        UserEmail userEmail = null;
        List<UserEmail> userEmailList = userEmailRepository.findByUserId(user.getId());
        if (userEmailList.size() > 0) {
            userEmail = userEmailList.get(0);
        }

        return ResponseEntity.ok().body(new UserFull(user, userEmail));
    }

    @Transactional
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) throws BadRequestException
    {
        user.validate();
        User savedUser = userRepository.saveAndFlush(user);
        //entityManager.flush();
        //entityManager.refresh(savedUser);

        SendEmailSMTP emailService = new SendEmailSMTP();
        String emailText = emailService.sendEmail(environment, savedUser);

        UserEmail userEmail = new UserEmail();
        userEmail.setUserId(savedUser.getId());
        userEmail.setEmailText(emailText);
        userEmailRepository.save(userEmail);

        return savedUser;
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
        @PathVariable(value = "id") String userIdStr, @Valid @RequestBody User userDetails)
        throws BadRequestException, ResourceNotFoundException
    {
        User user = getUserByStringId(userIdStr);

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setUpdatedAt(new Date());
        user.validate();

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{ids}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "ids") String userIds) throws Exception
    {
        User user;
        StringTokenizer st = new StringTokenizer(userIds, ",");
        while (st.hasMoreElements()) {
            try {
                user = userRepository.findById(Long.valueOf(st.nextElement().toString())).orElse(null);
                if (user != null) {
                    user.setIsDeleted((byte)1);
                    userRepository.save(user);
                }
            } catch (NumberFormatException nfe) {
            }
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
