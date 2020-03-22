package com.nxmile.service;

import com.nxmile.assmbler.CommonAssembler;
import com.nxmile.assmbler.GroupAssembler;
import com.nxmile.dto.CommonCodeDto;
import com.nxmile.dto.GroupCodeDto;
import com.nxmile.entity.*;
import com.nxmile.error.ErrorsResource;
import com.nxmile.exception.CommonCodeValidationException;
import com.nxmile.exception.CommonNotFoundException;
import com.nxmile.exception.GroupCodeNotFoundException;
import com.nxmile.repository.CommonCodeRepositroy;
import com.nxmile.repository.GroupCodeRepositroy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.persistence.PrePersist;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommonService {


    @Autowired
    private CommonCodeRepositroy commonCodeRepositroy;

    @Autowired
    private GroupCodeRepositroy groupCodeRepositroy;

    @Autowired
    private JPAQueryFactory queryfactory;

    @Autowired
    private CommonAssembler commonAssembler;

    @Autowired
    private GroupAssembler groupAssembler;

    @Autowired
    private ModelMapper modelMapper;

    QGroupCode grp = QGroupCode.groupCode;
    QCommonCode comm = QCommonCode.commonCode;

    public ResponseEntity createGroupCommonCode(GroupCodeDto groupCodeDto) {

        GroupCode groupCode = modelMapper.map(groupCodeDto, GroupCode.class);
        CommonCode commoncode = modelMapper.map(groupCodeDto, CommonCode.class);
        groupCode.addCommonCode(commoncode);

        EntityModel<GroupCode> entityModel = groupAssembler.toModel(groupCodeRepositroy.save(groupCode));

        return ResponseEntity.ok(entityModel)
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    public ResponseEntity createCommonCode(CommonCodeDto commonCodeDto) {

        CommonCode commonCode = modelMapper.map(commonCodeDto, CommonCode.class);
        System.out.println(commonCode.getCommCode().getArg() + "#############");
        GroupCode groupCode = groupCodeRepositroy.findById(commonCodeDto.getGrpCode())
                .orElseThrow(() -> new GroupCodeNotFoundException(commonCodeDto.getGrpCode()));
        //groupCode.setCommonCodes(commonCode);


        EntityModel<CommonCode> entityModel = commonAssembler.toModel(commonCodeRepositroy.save(commonCode));


        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    public ResponseEntity delete(String commonCode, String arg) {
        commonCodeRepositroy.deleteById(commonCode,arg);
        System.out.println("###############헤이");
        return ResponseEntity.noContent().build();

    }

    public ResponseEntity findGroupCode(String groupCode) {

        Optional<GroupCode> data = Optional.ofNullable(Optional.ofNullable(queryfactory.selectFrom(grp)
                .where(grp.grpCode.eq(groupCode))
                .fetchOne()).orElseThrow(() -> new GroupCodeNotFoundException(groupCode)));
        EntityModel<GroupCode> groupCodeEntityModel = groupAssembler.toModel(data.get());

        return ResponseEntity.ok(groupCodeEntityModel);
    }

    public CollectionModel<EntityModel<CommonCode>> findCommonCode(String groupCode) {
        List<CommonCode> data = queryfactory.selectFrom(comm)
                .where(comm.grpCode.grpCode.eq(groupCode))
                .fetch();
        if(data.size()==0){
            throw new GroupCodeNotFoundException(groupCode);
        }
        List<EntityModel<CommonCode>> collect = data.stream()
                .map(e -> commonAssembler.toModel(e)).collect(Collectors.toList());
        return new CollectionModel<>(collect);
    }

    public ResponseEntity check(String commonCode, String arg) {

        Optional<CommonCode> data = Optional.ofNullable(Optional.ofNullable(queryfactory.selectFrom(comm)
                .where(comm.commCode.commCode.eq(commonCode), comm.commCode.arg.eq(arg))
                .fetchOne()).orElseThrow(() -> new CommonNotFoundException(commonCode, arg)));

        bizCheck(data.get());

        return ResponseEntity.ok(commonAssembler.toModel(data.get()));
    }

    public ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }

    public void bizCheck(CommonCode commonCode){
        if ( commonCode.isApplyYn() == false){
            throw new CommonCodeValidationException("applyYn", "false" );
        } else if ( LocalDateTime.now().isBefore(commonCode.getValidFromDate()) ){
            throw new CommonCodeValidationException("validFromDate", commonCode.getValidFromDate().toString());
        } else if ( LocalDateTime.now().isAfter(commonCode.getValidToDate()) ){
            throw new CommonCodeValidationException("validToDate", commonCode.getValidToDate().toString());
        }
    }

}
