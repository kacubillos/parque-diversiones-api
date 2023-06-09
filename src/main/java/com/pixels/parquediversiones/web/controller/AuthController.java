package com.pixels.parquediversiones.web.controller;

import com.pixels.parquediversiones.domain.UserAccount;
import com.pixels.parquediversiones.domain.dto.AuthenticationRequest;
import com.pixels.parquediversiones.domain.dto.AuthenticationResponse;
import com.pixels.parquediversiones.domain.service.EmployeeService;
import com.pixels.parquediversiones.domain.service.UserService;
import com.pixels.parquediversiones.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<UserAccount> createUser(@RequestBody UserAccount userAccount) {
        int empId = userAccount.getEmployeeId();
        String email = userAccount.getEmail().toLowerCase();

        if(employeeService.getById(empId).isPresent() && !userService.existsUser(email)) {
            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            return new ResponseEntity<>(userService.save(userAccount), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = authenticationRequest.getEmail();
        String roleName = userService.getByEmail(email).map(user -> user.getEmployee().getRole().getName()).get();
        String token = jwtUtil.create(email, roleName);

        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }
}
