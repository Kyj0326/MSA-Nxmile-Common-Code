package com.nxmile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nxmile.dto.CommonCodeDto;
import com.nxmile.dto.GroupCodeDto;
import com.nxmile.service.CommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class CommonContollerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CommonService commonService;


    @Test
    @Transactional
    public void createGroupCodeTest() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP")
                                        .commonCode("COMM").arg("111").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true).build();

        mockMvc.perform(post("/nxm/api/group")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(groupCodeDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.get-commoncode-info").exists())
                .andExpect(jsonPath("_links.register-groupcode-with-GroupCodeDto-by-post").exists())
                .andExpect(jsonPath("_links.register-commoncode-with-CommonCodeDto-by-post").exists())
                .andDo( document( "create-groupCode",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("get-commoncode-info").description("link to get info commoncode that you register"),
                                linkWithRel("register-groupcode-with-GroupCodeDto-by-post").description("link to register groupcode with GroupCodeDto by post method"),
                                linkWithRel("register-commoncode-with-CommonCodeDto-by-post").description("link to register groupcode with CommonCodeDto by post method")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("grpCode").description("Type : String, Not null."),
                                fieldWithPath("commonCode").description("Type : String, Not null."),
                                fieldWithPath("arg").description("Type : String, Not null."),
                                fieldWithPath("groupCodeName").description("Type : String, Not null."),
                                fieldWithPath("commonCodeName").description("Type : String, Not null."),
                                fieldWithPath("applyYn").description("Type : Boolean, Not null."),
                                fieldWithPath("validFromDate").description("Type : LocalDateTime, default : LocalDateTime.now()"),
                                fieldWithPath("validToDate").description("Type : LocalDateTime, default : LocalDateTime.MAX")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Info"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("grpCode").description("GroupCode"),
                                fieldWithPath("name").description("Name of GroupCode"),
                                fieldWithPath("validFromDate").description("validFromDate"),
                                fieldWithPath("validToDate").description("validToDate"),
                                fieldWithPath("applyYn").description("applyYn"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.get-commoncode-info.href").description("link to get info commoncode that you register"),
                                fieldWithPath("_links.register-groupcode-with-GroupCodeDto-by-post.href").description("link to register groupcode with GroupCodeDto by post method"),
                                fieldWithPath("_links.register-commoncode-with-CommonCodeDto-by-post.href").description("link to register groupcode with CommonCodeDto by post method")
                        )
                ));

    }

    @Test
    @Transactional
    public void findGroupCode() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP")
                .commonCode("COMM").arg("111").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true).build();
        commonService.createGroupCommonCode(groupCodeDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/nxm/api/group/{groupCode}",groupCodeDto.getGrpCode()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.get-commoncode-info").exists())
                .andExpect(jsonPath("_links.register-groupcode-with-GroupCodeDto-by-post").exists())
                .andExpect(jsonPath("_links.register-commoncode-with-CommonCodeDto-by-post").exists())
                .andDo(document("get-groupCode",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("get-commoncode-info").description("link to get info commoncode that you register"),
                                linkWithRel("register-groupcode-with-GroupCodeDto-by-post").description("link to register groupcode with GroupCodeDto by post method"),
                                linkWithRel("register-commoncode-with-CommonCodeDto-by-post").description("link to register groupcode with CommonCodeDto by post method")
                        ),
                        pathParameters(
                                parameterWithName("groupCode").description("Type : String, It must be not null")
                        ),
                        responseFields(
                                fieldWithPath("grpCode").description("GroupCode"),
                                fieldWithPath("name").description("Name of GroupCode"),
                                fieldWithPath("validFromDate").description("validFromDate"),
                                fieldWithPath("validToDate").description("validToDate"),
                                fieldWithPath("applyYn").description("applyYn"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.get-commoncode-info.href").description("link to get info commoncode that you register"),
                                fieldWithPath("_links.register-groupcode-with-GroupCodeDto-by-post.href").description("link to register groupcode with GroupCodeDto by post method"),
                                fieldWithPath("_links.register-commoncode-with-CommonCodeDto-by-post.href").description("link to register groupcode with CommonCodeDto by post method")
                        )
                ));

    }

    @Test
    @Transactional
    public void findGroupCode_NotFound() throws Exception{

        mockMvc.perform(RestDocumentationRequestBuilders.get("/nxm/api/group/{groupCode}","NONONO"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("biz-error-1",
                        pathParameters(
                                parameterWithName("groupCode").description("Type : String, It must be not null")
                        ),
                        responseFields(
                                fieldWithPath("[0]logref").description("It is type of log"),
                                fieldWithPath("[0]message").description("Description of error"),
                                fieldWithPath("[0]links").description("profile")
                        )
                ));

    }

    @Test
    @Transactional
    public void badCreateGroupCodeTest() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().build();

        mockMvc.perform(post("/nxm/api/group")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(groupCodeDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo( document( "request-error_1",
                        responseFields(
                                subsectionWithPath("content").description("when you create groupcode, it is errors that can be possible.")
                        )
                ));

    }

    @Test
    @Transactional
    public void createCommonCodeTest() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP")
                .commonCode("COMM").arg("111").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true).build();
        commonService.createGroupCommonCode(groupCodeDto);

        CommonCodeDto commonCodeDto = CommonCodeDto.builder().grpCode("GROUP")
                   .commonCode("COMM").arg("1112").commonCodeName("TEST2").applyYn(true).defaultYn(false).build();

        mockMvc.perform(post("/nxm/api/common")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(commonCodeDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.check-with-commoncode-arg-by-get").exists())
                .andExpect(jsonPath("_links.delete-commoncode-with-commoncode-arg-by-delete").exists())
                .andDo( document( "create-commoncode",
                        links(
                                linkWithRel("self").description("link to self. It desplay all commoncode of groupcode."),
                                linkWithRel("check-with-commoncode-arg-by-get").description("link to check with commoncode, arg. It is Main API"),
                                linkWithRel("delete-commoncode-with-commoncode-arg-by-delete").description("link to delete commoncode.")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("grpCode").description("Type : String, Not null."),
                                fieldWithPath("commonCode").description("Type : String, Not null."),
                                fieldWithPath("arg").description("Type : String, Not null."),
                                fieldWithPath("commonCodeName").description("Type : String, Not null."),
                                fieldWithPath("applyYn").description("Type : Boolean, Not null."),
                                fieldWithPath("defaultYn").description("Type : Boolean."),
                                fieldWithPath("validFromDate").description("Type : LocalDateTime, default : LocalDateTime.now()"),
                                fieldWithPath("validToDate").description("Type : LocalDateTime, default : LocalDateTime.MAX")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location Info"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("commCode.commCode").description("Common Code"),
                                fieldWithPath("commCode.arg").description("Arg"),
                                fieldWithPath("name").description("Name of commoncode"),
                                fieldWithPath("validFromDate").description("validFromDate"),
                                fieldWithPath("validToDate").description("validToDate"),
                                fieldWithPath("applyYn").description("applyYn"),
                                fieldWithPath("defaultYn").description("defaultYn"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.check-with-commoncode-arg-by-get.href").description("link to get info commoncode that you register"),
                                fieldWithPath("_links.delete-commoncode-with-commoncode-arg-by-delete.href").description("link to register groupcode with CommonCodeDto by post method")
                        )
                ));

    }

    @Test
    @Transactional
    public void badCreateCommonCodeTest1() throws Exception{

        CommonCodeDto commonCodeDto = CommonCodeDto.builder().grpCode("GROUP11")
                .commonCode("COMM").arg("1112").commonCodeName("TEST2").applyYn(true).defaultYn(false).build();

        mockMvc.perform(post("/nxm/api/common")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(commonCodeDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo( document( "biz-error-2",
                        responseFields(
                                fieldWithPath("[0]logref").description("It is type of log"),
                                fieldWithPath("[0]message").description("Description of error"),
                                fieldWithPath("[0]links").description("profile")
                        )
                ));

    }


    @Test
    @Transactional
    public void badCreateGroupCodeTest2() throws Exception{

        CommonCodeDto commonCodeDto = CommonCodeDto.builder().build();

        mockMvc.perform(post("/nxm/api/common")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(commonCodeDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo( document( "request-error_2",
                        responseFields(
                                subsectionWithPath("content").description("when you create commoncode, it is errors that can be possible.")
                        )
                ));

    }



    @Test
    @Transactional
    public void deleteCommonCodeTest() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP")
                .commonCode("COMM").arg("111").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true).build();
        commonService.createGroupCommonCode(groupCodeDto);

        CommonCodeDto commonCodeDto = CommonCodeDto.builder().grpCode("GROUP")
                .commonCode("COMM").arg("1112").commonCodeName("TEST2").applyYn(true).defaultYn(false).build();

        mockMvc.perform(RestDocumentationRequestBuilders
                .delete("/nxm/api/common/{commonCode}/{arg}",commonCodeDto.getCommonCode(),commonCodeDto.getArg())
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-commoncode",
                        pathParameters(
                                parameterWithName("commonCode").description("Type : String, It must be not null"),
                                parameterWithName("arg").description("Type : String, It must be not null")
                )));

    }

    @Test
    @Transactional
    public void mainAPI_check() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP33")
                .commonCode("COM44M").arg("1112").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true).build();
        commonService.createGroupCommonCode(groupCodeDto);


        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/nxm/api/{commonCode}/{arg}",groupCodeDto.getCommonCode(),groupCodeDto.getArg())
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.check-with-commoncode-arg-by-get").exists())
                .andExpect(jsonPath("_links.delete-commoncode-with-commoncode-arg-by-delete").exists())
                .andDo( document( "mainAPI-check",
                        links(
                                linkWithRel("self").description("link to self. It desplay all commoncode of groupcode."),
                                linkWithRel("check-with-commoncode-arg-by-get").description("link to check with commoncode, arg. It is Main API"),
                                linkWithRel("delete-commoncode-with-commoncode-arg-by-delete").description("link to delete commoncode.")
                        ),
                        pathParameters(
                                parameterWithName("commonCode").description("Type : String, It must be not null"),
                                parameterWithName("arg").description("Type : String, It must be not null")
                        ),
                        responseFields(
                                fieldWithPath("commCode.commCode").description("Common Code"),
                                fieldWithPath("commCode.arg").description("Arg"),
                                fieldWithPath("name").description("Name of commoncode"),
                                fieldWithPath("validFromDate").description("validFromDate"),
                                fieldWithPath("validToDate").description("validToDate"),
                                fieldWithPath("applyYn").description("applyYn"),
                                fieldWithPath("defaultYn").description("defaultYn"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.check-with-commoncode-arg-by-get.href").description("link to get info commoncode that you register"),
                                fieldWithPath("_links.delete-commoncode-with-commoncode-arg-by-delete.href").description("link to register groupcode with CommonCodeDto by post method")
                        )
                ));

    }

    @Test
    @Transactional
    public void mainAPI_check_error_1() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP33")
                .commonCode("COMM44").arg("11155").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(false).build();
        commonService.createGroupCommonCode(groupCodeDto);


        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/nxm/api/{commonCode}/{arg}",groupCodeDto.getCommonCode(),groupCodeDto.getArg())
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("mainAPI-check-error-1",
                        pathParameters(
                                parameterWithName("commonCode").description("Type : String, It must be not null"),
                                parameterWithName("arg").description("Type : String, It must be not null")
                        ),
                        responseFields(
                                fieldWithPath("[0]logref").description("It is type of log"),
                                fieldWithPath("[0]message").description("Description of error"),
                                fieldWithPath("[0]links").description("profile")
                        )
                ));

    }

    @Test
    @Transactional
    public void mainAPI_check_error_2() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP22")
                .commonCode("COMM22").arg("11122").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true)
                .validFromDate(LocalDateTime.MAX).build();
        commonService.createGroupCommonCode(groupCodeDto);


        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/nxm/api/{commonCode}/{arg}",groupCodeDto.getCommonCode(),groupCodeDto.getArg())
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("mainAPI-check-error-2",
                        pathParameters(
                                parameterWithName("commonCode").description("Type : String, It must be not null"),
                                parameterWithName("arg").description("Type : String, It must be not null")
                        ),
                        responseFields(
                                fieldWithPath("[0]logref").description("It is type of log"),
                                fieldWithPath("[0]message").description("Description of error"),
                                fieldWithPath("[0]links").description("profile")
                        )
                ));

    }


    @Test
    @Transactional
    public void mainAPI_check_error_3() throws Exception{

        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP11")
                .commonCode("COMM121").arg("11331").commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true)
                .validToDate(LocalDateTime.now()).build();
        commonService.createGroupCommonCode(groupCodeDto);


        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/nxm/api/{commonCode}/{arg}",groupCodeDto.getCommonCode(),groupCodeDto.getArg())
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("mainAPI-check-error-3",
                        pathParameters(
                                parameterWithName("commonCode").description("Type : String, It must be not null"),
                                parameterWithName("arg").description("Type : String, It must be not null")
                        ),
                        responseFields(
                                fieldWithPath("[0]logref").description("It is type of log"),
                                fieldWithPath("[0]message").description("Description of error"),
                                fieldWithPath("[0]links").description("profile")
                        )
                ));

    }
    
//    @Test
//    public void findCommoncodes() throws Exception {
//        IntStream.range(0, 5).forEach(i -> generateCommon(i));
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get("/nxm/api/common/{groupCode}","GROUP1")
//                .accept(MediaTypes.HAL_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    public void generateCommon(int val){
//        GroupCodeDto groupCodeDto = GroupCodeDto.builder().grpCode("GROUP1")
//                .commonCode("COMM1").arg("11"+val).commonCodeName("commonCode").groupCodeName("groupCode").applyYn(true).build();
//
//
//        commonService.createGroupCommonCode(groupCodeDto);
//    }
}