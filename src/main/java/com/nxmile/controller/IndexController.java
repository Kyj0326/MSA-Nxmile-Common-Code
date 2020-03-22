package com.nxmile.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {

    private final String test ="test";

    @GetMapping
    public RepresentationModel index() {
        RepresentationModel index = new RepresentationModel();
        index.add(linkTo(IndexController.class).slash("docs/index.html").withRel("profile"));
        return index;
    }

}
