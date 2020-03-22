package com.nxmile.controller;

import com.nxmile.dto.CommonCodeDto;
import com.nxmile.dto.GroupCodeDto;
import com.nxmile.entity.CommonCode;
import com.nxmile.entity.GroupCode;
import com.nxmile.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/nxm/api", produces = MediaTypes.HAL_JSON_VALUE)
public class CommonContoller {

    @Autowired
    private CommonService commonService;

    @PostMapping("/group")
    public ResponseEntity CreateGroupCommonCode(@RequestBody @Valid GroupCodeDto groupCodeDto, Errors errors){

        if( errors.hasErrors() ){
            return commonService.badRequest(errors);
        }
        return commonService.createGroupCommonCode(groupCodeDto);
    }

    @GetMapping("/group/{groupCode}")
    public ResponseEntity findGroupCode(@PathVariable String groupCode){
        return  commonService.findGroupCode(groupCode);
    }

    @PostMapping("/common")
    public ResponseEntity CreateCommonCode(@RequestBody @Valid CommonCodeDto commonCodeDto, Errors errors){
        if( errors.hasErrors() ){
            return commonService.badRequest(errors);
        }
        return commonService.createCommonCode(commonCodeDto);
    }

    @DeleteMapping("/common/{commonCode}/{arg}")
    public ResponseEntity deleteCommonCode(@PathVariable String commonCode, @PathVariable String arg){
        return commonService.delete(commonCode,arg);
    }


    @GetMapping("/common/{groupCode}")
    public CollectionModel<EntityModel<CommonCode>> findCommonCode(@PathVariable String groupCode){
        return  commonService.findCommonCode(groupCode);
    }

    @GetMapping("/{commonCode}/{arg}")
    public ResponseEntity check(@PathVariable String commonCode, @PathVariable String arg){
        return  commonService.check(commonCode,arg);
    }

}
