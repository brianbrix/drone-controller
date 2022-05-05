package com.musala.drone.Validators;


import com.musala.drone.enums.ModelEnum;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

class ModelValidator implements ConstraintValidator<Model,String>
{

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(ModelEnum.values()).map(ModelEnum::name).toList().contains(s);
    }
}
/*
Annotation to validate model field
 */
@Constraint(validatedBy = ModelValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
    String message() default "Must be in Model options";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};//additonal infor about annotation
}
