package DavideSalzani.ImmobiliareProjectBE.notification.payloads;

import DavideSalzani.ImmobiliareProjectBE.request.Request;

public record NewNotificationByRequestDTO(
    long requestId
) {
}
