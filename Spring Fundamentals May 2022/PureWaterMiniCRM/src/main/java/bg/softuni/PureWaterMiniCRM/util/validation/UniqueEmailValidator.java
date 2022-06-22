package bg.softuni.PureWaterMiniCRM.util.validation;

import bg.softuni.PureWaterMiniCRM.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepo;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userRepo
                .findByEmail(value)
                .isEmpty();
    }
}
