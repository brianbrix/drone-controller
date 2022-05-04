package com.musala.drone.Validators;
import com.musala.drone.enums.StateEnum;
import lombok.extern.log4j.Log4j2;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

@Log4j2
class StateValidator implements ConstraintValidator<State,String>
{

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(StateEnum.values()).map(StateEnum::name).toList().contains(s);
    }
}
/*
Annotation to validate state field
 */
@Constraint(validatedBy = StateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String message() default "Must be in State options";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};//additonal infor about annotation
}
