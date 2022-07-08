/*
 * Copyright \(C\) open knowledge GmbH\.
 *
 * Licensed under the Apache License, Version 2\.0 \(the "License"\);
 * you may not use this file except in compliance with the License\.
 * You may obtain a copy of the License at
 *
 *     http://www\.apache\.org/licenses/LICENSE-2\.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied\.
 * See the License for the specific language governing permissions and
 * limitations under the License\.
 */
package com.example.mitiappbackend.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mitiappbackend.domain.role.Erole;
import com.example.mitiappbackend.domain.role.Role;
import com.example.mitiappbackend.domain.role.RoleRepositoryTwo;
import com.example.mitiappbackend.domain.role.RoleService;
import com.example.mitiappbackend.domain.user.User;
import com.example.mitiappbackend.domain.user.UserDetailsImpl;
import com.example.mitiappbackend.domain.user.UserRepository;
import com.example.mitiappbackend.infrastructure.security.jwt.JwtUtils;
import com.example.mitiappbackend.infrastructure.security.payload.request.LoginRequest;
import com.example.mitiappbackend.infrastructure.security.payload.request.SignupRequest;
import com.example.mitiappbackend.infrastructure.security.payload.response.JwtResponse;
import com.example.mitiappbackend.infrastructure.security.payload.response.MessageResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private static final Logger LOGGER = Logger.getLogger(AuthResource.class.getSimpleName());

    private static final String ROLEERRORMESSAGE = "Error: Role is not found.";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepositoryTwo roleRepositoryTwo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/role", consumes = "application/json")
    public void createRole(@RequestBody Role role) {
        roleService.createRole(role);
        LOGGER.info("RESTful call 'POST role'");
    }

    @GetMapping(value = "/role", produces = "application/json")
    public List<Role> readRole() {
        LOGGER.info("RESTful call 'GET role'");
        return roleService.readRole();
    }

    @GetMapping(value = "/user", produces = "application/json")
    public List<User> readUser() {
        LOGGER.info("RESTful call 'GET user'");
        return userRepository.findAll();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepositoryTwo.findByName(Erole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException(ROLEERRORMESSAGE));
            roles.add(userRole);
        }
        strRoles.forEach(role -> {
            if ("ROLE_USER".equals(role)) {
                Role userRole = roleRepositoryTwo.findByName(Erole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ROLEERRORMESSAGE));
                roles.add(userRole);
            } else {
                Role adminRole = roleRepositoryTwo.findByName(Erole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException(ROLEERRORMESSAGE));
                roles.add(adminRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

