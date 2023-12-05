package DavideSalzani.ImmobiliareProjectBE.authorization;

import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.UnauthorizedException;
import DavideSalzani.ImmobiliareProjectBE.mailSenderConfig.MailSenderConfig;
import DavideSalzani.ImmobiliareProjectBE.mailSenderConfig.MailSenderService;
import DavideSalzani.ImmobiliareProjectBE.security.JWTTools;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import DavideSalzani.ImmobiliareProjectBE.user.UserRepository;
import DavideSalzani.ImmobiliareProjectBE.user.UserRole;
import DavideSalzani.ImmobiliareProjectBE.user.payloads.NewUserDTO;
import DavideSalzani.ImmobiliareProjectBE.user.payloads.UserLoginDTO;
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
    PasswordEncoder bCrypt;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    JWTTools jwtTools;
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
    public String authenticateUser(UserLoginDTO body) {
        User user = userRepo.findByUsername(body.username()).orElseThrow(() -> new NotFoundException("utente "));
        if (bCrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Username o password invalido.");
        }
    }
    public User save(NewUserDTO body) {
        userRepo.findByEmail(body.email()).ifPresent(a -> {
            throw new BadRequestException("l'email " + a.getEmail() + " è già stata usata");
        });
        userRepo.findByUsername(body.username()).ifPresent(a -> {
            throw new BadRequestException("l'username " + a.getUsername() + " è già stato usato");
        });
        User user;
        String newPassword = generateRandomPassword(8);
        String encryptedPassword = bCrypt.encode(newPassword);
        String htmlBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "      .nav {\n" +
                "        margin-bottom: 1.2rem;\n" +
                "      }\n" +
                "      img {\n" +
                "        width: 100%;\n" +
                "        height: 100%;\n" +
                "      }\n" +
                "      .card {\n" +
                "        border: 1px solid black;\n" +
                "        padding: 1rem;\n" +
                "        margin: 0 20%;\n" +
                "      }\n" +
                "      p,\n" +
                "      h1,\n" +
                "      h2,\n" +
                "      h3,\n" +
                "      h4,\n" +
                "      h5,\n" +
                "      h6 {\n" +
                "        font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif;\n" +
                "      }\n" +
                "      p {\n" +
                "        font-size: 1.2rem;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"nav\">\n" +
                "      <img src=\"\" alt=\"logo\" />\n" +
                "    </div>\n" +
                "    <div class=\"card\">\n" +
                "      <div>\n" +
                "        <div>\n" +
                "          <h3>Benvenuto/a "+ body.name() + " " + body.surname() +"</h3>\n" +
                "          <p>\n" +
                "            Questa mail è stata generata automaticamente in seguito al suo inserimento sul portale\n" +
                "            <span style=\"font-weight: bold\">PropManageHub</span>, la password che le è stata assegnata è univoca per\n" +
                "            Lei.\n" +
                "          </p>\n" +
                "          <p>La sua nuova password generata è: <span style=\"font-weight: bold\">" + newPassword + "</span></p>\n" +
                "          <p>\n" +
                "            La preghiamo di non rispondere a questa mail nè di condividerne con nessuno il suo contenuto, se ha ricevuto\n" +
                "            questa email per sbaglio la preghiamo di eliminarla.\n" +
                "          </p>\n" +
                "          <p>La ringraziamo per l'attenzione, Team PropManageHub.</p>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";

            mailSenderService.sendHtmlEmail(body.email(), "Nuova password generata", htmlBody );

        bCrypt.encode(newPassword);
        user = new User(body.username(), encryptedPassword, body.name(), body.surname(), body.email(), Long.parseLong(body.phone()),body.birthDay(), LocalDate.now());
        user.setRole(UserRole.SUPER_ADMIN);
        return userRepo.save(user);
    }


}
