package com.nxmile.assmbler;

import com.nxmile.controller.CommonContoller;
import com.nxmile.entity.GroupCode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GroupAssembler implements RepresentationModelAssembler<GroupCode, EntityModel<GroupCode>> {


    @Override
    public EntityModel<GroupCode> toModel(GroupCode groupCode) {
        return new EntityModel<>(groupCode,
                linkTo(methodOn(CommonContoller.class).findGroupCode(groupCode.getGrpCode())).withSelfRel(),
                linkTo(methodOn(CommonContoller.class).findCommonCode(groupCode.getGrpCode())).withRel("get-commoncode-info"),
                linkTo(CommonContoller.class).slash("/group").withRel("register-groupcode-with-GroupCodeDto-by-post"),
                linkTo(CommonContoller.class).slash("/common").withRel("register-commoncode-with-CommonCodeDto-by-post"));
    }


}
