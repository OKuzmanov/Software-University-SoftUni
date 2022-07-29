package bg.softuni.PureWaterMiniCRM.models.errorModels;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestNotSuccessfulDeleteModel {
    private LocalDateTime dateTime;
    private String status;
    private String error;
    private String message;

    public RestNotSuccessfulDeleteModel(String status, String error, String message) {
        this.status = status;
        this.error = error;
        this.dateTime = LocalDateTime.now();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
