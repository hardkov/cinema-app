package helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    @Test
    public void testSendingEmail() {
        EmailService service = new EmailService();
        service.sendMail("gregory.janosz@gmail.com", "Testowy mail", "Tutaj jego zawartość");
    }

}