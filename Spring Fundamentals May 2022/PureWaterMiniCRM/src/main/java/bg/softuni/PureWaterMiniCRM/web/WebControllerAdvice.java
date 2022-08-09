package bg.softuni.PureWaterMiniCRM.web;

import bg.softuni.PureWaterMiniCRM.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WebControllerAdvice {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ObjectNotFoundException.class})
    public String handleOrderNotFoundException (Model model, ObjectNotFoundException exception) {
        model.addAttribute("message", String.format("No %s with the given ID exists: %d", exception.getType(), exception.getId()));
        return "object-not-found";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public String handleAccessDeniedException () {
        return "access-denied";
    }
}
