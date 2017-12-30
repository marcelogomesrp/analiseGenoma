package org.analiseGenoma.model.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.analiseGenoma.model.validator.impl.ObrigatorioValidator;


@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ObrigatorioValidator.class})
public @interface Obrigatorio {
        //public String message() default "{javax.validation.constraints.Size.message}";
    public String message();

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
