package com.zh.ch.bigdata.base.util.mail;

import com.zh.ch.bigdata.base.util.exception.ProjectException;
import junit.framework.TestCase;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class ComplexEmailTest extends TestCase {

    public void testSetConfigs() {
        try {
            ComplexEmail complexEmail = new ComplexEmail();
            complexEmail.setMailConfigs("src/main/resources/testMailConfigs.properties");
            assertNotNull(complexEmail.configs);
        } catch (ClassNotFoundException | IOException | ProjectException | GeneralSecurityException ignore) {
        }

    }
}