package com.nxmile.repository;

import com.nxmile.entity.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommonCodeRepositroy extends JpaRepository<CommonCode, String>, QuerydslPredicateExecutor<CommonCode> {

    @Transactional
    @Modifying
    @Query(value = "delete from COMMON_CODE where comm_Code = :commonCode and arg = :arg" , nativeQuery = true)
    void deleteById(@Param("commonCode") String commonCode, @Param("arg") String arg);
}