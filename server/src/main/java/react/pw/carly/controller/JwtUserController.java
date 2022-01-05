package react.pw.carly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import react.pw.carly.dao.UserRepository;
import react.pw.carly.models.User;


@RestController
@RequestMapping(path = "/V1/users")
@Profile({"jwt"})
@Slf4j
public class JwtUserController {
    @Autowired
    public UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserType())){
            user.setUserType(User.USER_TYPE_ADMIN);
        }
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String encodedPassword = MD5(user.getPassword());
        log.info("Encoded password [{}]", encodedPassword);
        user.setPassword(encodedPassword);
        repository.save(user);
        return ResponseEntity.ok(user);
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
