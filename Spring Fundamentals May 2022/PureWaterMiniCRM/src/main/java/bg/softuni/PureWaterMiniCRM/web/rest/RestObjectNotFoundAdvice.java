package bg.softuni.PureWaterMiniCRM.web.rest;

import bg.softuni.PureWaterMiniCRM.exceptions.ApiNotSuccessfulDeleteException;
import bg.softuni.PureWaterMiniCRM.exceptions.ApiObjectNotFoundException;
import bg.softuni.PureWaterMiniCRM.models.errorModels.RestNotSuccessfulDeleteModel;
import bg.softuni.PureWaterMiniCRM.models.errorModels.RestObjectNotFoundModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestObjectNotFoundAdvice {

    @ExceptionHandler({ApiObjectNotFoundException.class})
    public ResponseEntity<RestObjectNotFoundModel> handleRestONFException(ApiObjectNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RestObjectNotFoundModel("404","Object Not Found Exception",
                        String.format("%s with the given ID [%d] does not exist", exception.getType(), exception.getId())));
    }

    @ExceptionHandler({ApiNotSuccessfulDeleteException.class})
    public ResponseEntity<RestNotSuccessfulDeleteModel> handleNotSuccessfulDeleteException(ApiNotSuccessfulDeleteException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestNotSuccessfulDeleteModel("400", "Not successful delete exception",
                        String.format("%s with id [%d]could not be deleted because there are not enough products in inventory to complete it.",
                                exception.getType(), exception.getId())));
    }
}
