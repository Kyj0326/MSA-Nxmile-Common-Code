package com.nxmile.repository;

import com.nxmile.entity.GroupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GroupCodeRepositroy extends JpaRepository<GroupCode, String>, QuerydslPredicateExecutor<GroupCode>{
}
