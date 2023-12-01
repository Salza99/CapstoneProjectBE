package DavideSalzani.ImmobiliareProjectBE.authorization;

import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.mailSenderConfig.MailSenderConfig;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import DavideSalzani.ImmobiliareProjectBE.user.UserRepository;
import DavideSalzani.ImmobiliareProjectBE.user.UserRole;
import DavideSalzani.ImmobiliareProjectBE.user.payloads.NewUserDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder byCrypt;
    @Autowired
    JavaMailSender javaMailSender;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

    public String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
    public User save(NewUserDTO body) {
        userRepo.findByEmail(body.email()).ifPresent(a -> {
            throw new BadRequestException("l'email " + a.getEmail() + " è già stata usata");
        });
        User user;
        String newPassword = generateRandomPassword(8);
        String encryptedPassword = byCrypt.encode(newPassword);
        try{
            sendEmailWithPassword(body.email(), newPassword);
        }catch (MessagingException ex){
            ex.printStackTrace();
        }
        byCrypt.encode(newPassword);
        user = new User(body.username(), encryptedPassword, body.name(), body.surname(), body.email(), Long.parseLong(body.phone()),body.birthDay(), LocalDate.now());
        user.setRole(UserRole.SUPER_ADMIN);
        return userRepo.save(user);
    }
    private void sendEmailWithPassword(String recipientEmail, String password) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipientEmail);
        helper.setSubject("Password Generata");
        helper.setText("La tua password generata è: " + password);

        javaMailSender.send(message);
        System.out.println("Email inviata correttamente a " + recipientEmail);
    }
}
