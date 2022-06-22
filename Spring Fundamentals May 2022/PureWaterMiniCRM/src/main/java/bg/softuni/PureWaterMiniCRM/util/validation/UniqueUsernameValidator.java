package bg.softuni.PureWaterMiniCRM.util.validation;

import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepository userRepo;

    @Autowired
    public UniqueUsernameValidator(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userRepo
                .findByUsername(value)
                .isEmpty();
    }
}
