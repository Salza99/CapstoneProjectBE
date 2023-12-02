package DavideSalzani.ImmobiliareProjectBE.authorization;

import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.mailSenderConfig.MailSenderConfig;
import DavideSalzani.ImmobiliareProjectBE.mailSenderConfig.MailSenderService;
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
    MailSenderService mailSenderService;
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
        String htmlBody = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <link\n" +
                "      href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\"\n" +
                "      rel=\"stylesheet\"\n" +
                "      integrity=\"sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN\"\n" +
                "      crossorigin=\"anonymous\"\n" +
                "    />\n" +
                "    <title>Document</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"container\">\n" +
                "      <div class=\"card mb-3\">\n" +
                "        <img\n" +
                "          src=\"https://images.unsplash.com/photo-1560518883-ce09059eeffa?q=80&w=1973&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D\"\n" +
                "          class=\"card-img-top\"\n" +
                "          alt=\"...\"\n" +
                "        />\n" +
                "        <div class=\"card-body\">\n" +
                "          <h5 class=\"card-title mb-3\">La sua nuova password è stata generata.</h5>\n" +
                "          <h6 class=\"card-subtitle mb-3 text-body-secondary\">Benvenuto/a " + body.name() + " " + body.surname() + "</h6>\n" +
                "          <p class=\"card-text\">\n" +
                "            Questa mail è stata generata automaticamente in seguito al suo inserimento sul portale\n" +
                "            <span class=\"fw-bold\">PropManageHub</span>, la password che le è stata assegnata è univoca per Lei.\n" +
                "          </p>\n" +
                "          <p class=\"card-text\">La sua nuova password generata è: <span class=\"fw-bold\">"+ newPassword + "</span></p>\n" +
                "          <p class=\"card-text\">\n" +
                "            La preghiamo di non rispondere a questa mail nè di condividerne con nessuno il suo contenuto, se ha ricevuto\n" +
                "            questa email per sbaglio la preghiamo di eliminarla.\n" +
                "          </p>\n" +
                "          <p class=\"card-text\">La ringraziamo per l'attenzione, Team PropManageHub.</p>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <script\n" +
                "      src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js\"\n" +
                "      integrity=\"sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL\"\n" +
                "      crossorigin=\"anonymous\"\n" +
                "    ></script>\n" +
                "  </body>\n" +
                "</html>";

            mailSenderService.sendHtmlEmail(body.email(), "Nuova password generata", htmlBody );

        byCrypt.encode(newPassword);
        user = new User(body.username(), encryptedPassword, body.name(), body.surname(), body.email(), Long.parseLong(body.phone()),body.birthDay(), LocalDate.now());
        user.setRole(UserRole.SUPER_ADMIN);
        return userRepo.save(user);
    }

}
