package com.example.letsgongbu.messages;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void MessageInKorea() {
        String message = ms.getMessage("member.loginId", null, Locale.KOREA);
        Assertions.assertThat("아이디").isEqualTo(message);
    }

    @Test
    void MessageInEn() {
        String message = ms.getMessage("member.loginId", null, Locale.ENGLISH);
        Assertions.assertThat("ID").isEqualTo(message);
    }

    @Test
    void MessageInUnknown() {
        String message = ms.getMessage("member.loginId", null, null);
        Assertions.assertThat("아이디").isEqualTo(message);
    }

    @Test
    void CodeNotFound() {
        Assertions.assertThatThrownBy(()-> ms.getMessage("no_code", null, Locale.KOREA))
                .isInstanceOf(NoSuchMessageException.class);
    }
}
