package com.glecun.farmergameapi.domain;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.port.UserPort;

@ExtendWith(MockitoExtension.class)
class SignUpTest {

   @Mock
   private UserPort userPort;

   @InjectMocks
   SignUp signUp;

   @Test
   void should_save_user() {
      var user = new User("grewa", "grewa@gmail.com", "lol");
      when(userPort.findByEmail(user.getEmail())).thenReturn(Optional.empty());

      signUp.execute(user);

      verify(userPort).save(user);
   }

   @Test
   void should_not_save_user_when_already_exists() {
      var user = new User("grewa", "grewa@gmail.com", "lol");
      when(userPort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

      Assertions.assertThatThrownBy(() -> signUp.execute(user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_not_save_user_when_incorrect_email() {
      var user = new User("grewa", "incorrect email", "lol");
      Assertions.assertThatThrownBy(() -> signUp.execute(user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_not_save_user_when_username_too_long() {
      var user = new User("lkdjsflsmdjflmqksdjlfjkdjlfksjd", "incorrect email", "lol");
      Assertions.assertThatThrownBy(() -> signUp.execute(user)).isInstanceOf(RuntimeException.class);
   }
}
