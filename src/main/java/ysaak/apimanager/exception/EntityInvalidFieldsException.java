package ysaak.apimanager.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityInvalidFieldsException extends Exception {
    public EntityInvalidFieldsException(Class<?> clazz, Set<ConstraintViolation<?>> violations) {
        super(generateMessage(clazz.getSimpleName(), toList(violations)));
    }

    public EntityInvalidFieldsException(Class clazz, String... violations) {
        super(generateMessage(clazz.getSimpleName(), Arrays.asList(violations)));
    }

    private static String generateMessage(String entity, List<String> fields) {
        return StringUtils.capitalize(entity) + " fields are invalids " + fields;
    }

    private static List<String> toList(Set<ConstraintViolation<?>> violations) {
        Validate.notNull(violations, "Violations list is null");

        return violations.stream().map(violation -> violation.getPropertyPath().toString()).sorted().collect(Collectors.toList());
    }
}
