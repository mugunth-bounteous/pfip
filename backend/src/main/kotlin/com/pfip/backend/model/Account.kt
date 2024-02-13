package com.pfip.backend.model

import com.pfip.backend.dto.AccountType
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
public class Account {
    @Id
    var username: String? = null
    var password: String? = null
    var type: AccountType? = null
    var id: Int? = null
}