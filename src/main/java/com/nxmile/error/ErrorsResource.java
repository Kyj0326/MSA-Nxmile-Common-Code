package com.nxmile.error;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.Errors;

public class ErrorsResource extends EntityModel<Errors> {
    public ErrorsResource(Errors errors, Link... links) {
        super(errors, links);
//        add(linkTo(methodOn(SmsController.class).index()).withRel("index"));
    }
}

