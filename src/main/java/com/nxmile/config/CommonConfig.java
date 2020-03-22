package com.nxmile.config;

import com.nxmile.dto.CommonCodeDto;
import com.nxmile.dto.GroupCodeDto;
import com.nxmile.entity.Code;
import com.nxmile.entity.CommonCode;
import com.nxmile.entity.GroupCode;
import com.nxmile.service.CommonService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.acl.Group;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Configuration
public class CommonConfig {
    @Autowired
    CommonService commonService;


    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<GroupCodeDto, GroupCode> groupCodeMap = new PropertyMap<GroupCodeDto, GroupCode>() {
            protected void configure() {
                map().setApplyYn(source.isApplyYn());
                map().setGrpCode(source.getGrpCode());
                map().setName(source.getGroupCodeName());
            }
        };
        PropertyMap<GroupCodeDto, CommonCode> commCodeMap = new PropertyMap<GroupCodeDto, CommonCode>() {
            protected void configure() {

                map().setApplyYn(source.isApplyYn());
                map().getCommCode().setCommCode(source.getCommonCode());
                map().getCommCode().setArg(source.getArg());
                map().setName(source.getCommonCodeName());
            }
        };

        PropertyMap<CommonCodeDto, GroupCode> groupCodeMap2 = new PropertyMap<CommonCodeDto, GroupCode>() {
            protected void configure() {
                map().setGrpCode(source.getGrpCode());
            }
        };

        PropertyMap<CommonCodeDto, CommonCode> commCodeMap2 = new PropertyMap<CommonCodeDto, CommonCode>() {
            protected void configure() {
                map().setApplyYn(source.isApplyYn());
                map().getCommCode().setCommCode(source.getCommonCode());
                map().getCommCode().setArg(source.getArg());
                map().setName(source.getCommonCodeName());
                map().setDefaultYn(source.isDefaultYn());
            }
        };

        modelMapper.addMappings(groupCodeMap);
        modelMapper.addMappings(commCodeMap);
        modelMapper.addMappings(groupCodeMap2);
        modelMapper.addMappings(commCodeMap2);
        return modelMapper;
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {



            @Override
            public void run(ApplicationArguments args) throws Exception {

                IntStream.range(0,5).forEach(i-> generateCommon(i));


            }
        };
    }

    public void generateCommon(int val){
        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP1")
                .commonCode("COMM1").arg("11"+val).commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true).build();


           commonService.createGroupCommonCode(groupCodeDto);
    }

}
