package com.dubbo.one.api.service;

import com.dubbo.one.api.response.BaseResponse;

public interface ItemService {

    BaseResponse listItems();

    BaseResponse listPageItems(Integer pageNo, Integer pageSize);

    BaseResponse listPageByParams(Integer pageNo, Integer pageSize, String name);

}
