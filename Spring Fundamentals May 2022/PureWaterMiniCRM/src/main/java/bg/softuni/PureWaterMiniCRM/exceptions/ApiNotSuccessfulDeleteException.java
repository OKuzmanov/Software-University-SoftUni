package bg.softuni.PureWaterMiniCRM.exceptions;

public class ApiNotSuccessfulDeleteException extends RuntimeException {

    private final Long id;
    private final String type;

    public ApiNotSuccessfulDeleteException(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
