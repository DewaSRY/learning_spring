package com.sdewa.BasicSpring.services;

import com.sdewa.BasicSpring.models.PaginationDataResponse;
import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.models.AccountQuery;

public interface AccountListServices {

    PaginationDataResponse<AccountsEntity> getAccounts(AccountQuery query);
}
