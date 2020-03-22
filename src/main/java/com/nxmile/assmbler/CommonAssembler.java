package com.nxmile.assmbler;

import com.nxmile.controller.CommonContoller;
import com.nxmile.entity.CommonCode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommonAssembler implements RepresentationModelAssembler<CommonCode, EntityModel<CommonCode>> {


    @Override
    public EntityModel<CommonCode> toModel(CommonCode commonCode) {
        return new EntityModel<>(commonCode,
                linkTo(methodOn(CommonContoller.class).findCommonCode(commonCode.getGrpCode().getGrpCode())).withSelfRel(),
                linkTo(methodOn(CommonContoller.class).check(commonCode.getCommCode().getCommCode(),commonCode.getCommCode().getArg())).withRel("check-with-commoncode-arg-by-get"),
                linkTo(methodOn(CommonContoller.class).deleteCommonCode(commonCode.getCommCode().getCommCode(),commonCode.getCommCode().getArg())).withRel("delete-commoncode-with-commoncode-arg-by-delete"));
    }



    @Override
    public CollectionModel<EntityModel<CommonCode>> toCollectionModel(Iterable<? extends CommonCode> entities) {
        CollectionModel<EntityModel<CommonCode>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        //collectionModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        return collectionModel;


    }



}
