package bg.softuni.PureWaterMiniCRM.exceptions;

public class ApiObjectNotFoundException extends RuntimeException{
    private Long id;
    private String type;

    public ApiObjectNotFoundException(Long id, String type) {
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
