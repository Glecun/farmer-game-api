package com.glecun.farmergameapi.domain;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.port.UserPort;

@Service
public class SignUp {

   private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
   private final UserPort userPort;

   @Autowired
   public SignUp(UserPort userPort) {
      this.userPort = userPort;
   }

   public void execute(User user) {
      if (!isValidEmailAddress(user.getEmail())) {
         throw new RuntimeException(MessageFormat.format("Email: {0} isn't valid", user.getEmail()));
      }
      if (!isUsernameNotTooLong(user.getUsername())) {
         throw new RuntimeException(MessageFormat.format("Email: {0} isn't valid", user.getEmail()));
      }
      final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
      userPort.findByEmail(user.getEmail()).ifPresentOrElse(
            existingUser -> {throw new RuntimeException(MessageFormat.format("User with email: {0} already exists", existingUser.getEmail()));},
            () -> userPort.save(user.setPassword(encryptedPassword))
      );

   }

   private boolean isUsernameNotTooLong(String username) {
      return username.length() <= 20;
   }

   public boolean isValidEmailAddress(String email) {
      String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
      java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
      java.util.regex.Matcher m = p.matcher(email);
      return m.matches();
   }
}
