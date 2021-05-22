package com.lms.configs;

import com.google.rpc.Code;
import com.lmsService.grpc.ErrorDetails;
import com.lmsService.grpc.ErrorResponse;

import javax.validation.*;
import java.util.Set;
import java.util.logging.Logger;

public class CustomValidatorClass{
        private static final ValidatorFactory factory =Validation.buildDefaultValidatorFactory();
        private static final Validator validator = factory.getValidator();

        public static final Logger logger = Logger.getLogger(CustomValidatorClass.class.getName());

    public static <T> ErrorResponse validate(T t){

            Set<ConstraintViolation<T>> violations =validator.validate(t);
            if(violations!=null && violations.size()!=0){

                ErrorResponse.Builder errorResponse = ErrorResponse.newBuilder();
                errorResponse.setCode(Code.OUT_OF_RANGE.getNumber());
                errorResponse.setStatus(Code.OUT_OF_RANGE.toString());
                for (ConstraintViolation<T> violation : violations) {
                    System.out.println(violation.toString());
                       errorResponse.addErrorDetails(ErrorDetails.newBuilder()
                               .setAtrName(violation.getPropertyPath().toString())
                               .setAtrValue(violation.getInvalidValue().toString())
                               .setError(violation.getMessage())
                               .build()
                       );
                }

                return errorResponse.build();
            }
            return null;
        }

}
