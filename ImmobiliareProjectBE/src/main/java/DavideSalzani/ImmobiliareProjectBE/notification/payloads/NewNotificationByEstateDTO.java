package DavideSalzani.ImmobiliareProjectBE.notification.payloads;

import java.util.UUID;

public record NewNotificationByEstateDTO(
    UUID estateId
) {
}
