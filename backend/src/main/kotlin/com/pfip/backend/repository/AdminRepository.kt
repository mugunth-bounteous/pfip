package com.pfip.backend.repository

import com.pfip.backend.model.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface AdminRepository : JpaRepository<Admin, String> {


}