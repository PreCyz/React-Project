package react.pw.carly.security.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import react.pw.carly.security.configs.WebJwtSecurityConfig;
import react.pw.carly.security.models.JwtRequest;
import react.pw.carly.security.models.JwtResponse;
import react.pw.carly.security.services.JwtTokenService;
import react.pw.carly.security.services.JwtUserDetailsService;

@RestController
@RequestMapping(path = "/V1/authenticate")
@Profile({"jwt"})
public class JwtAuthenticationController {
    Logger log = LoggerFactory.getLogger(WebJwtSecurityConfig.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenService.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.info("================DisabledException",e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.info("================BadCredentialsException",e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
