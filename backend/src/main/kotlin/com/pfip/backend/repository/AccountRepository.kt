package com.pfip.backend.repository

import com.pfip.backend.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface AccountRepository: JpaRepository< Account, String > {

}