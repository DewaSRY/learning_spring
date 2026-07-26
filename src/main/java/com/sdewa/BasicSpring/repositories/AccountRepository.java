package com.sdewa.BasicSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.sdewa.BasicSpring.models.AccountsEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountsEntity, Long> {

}
