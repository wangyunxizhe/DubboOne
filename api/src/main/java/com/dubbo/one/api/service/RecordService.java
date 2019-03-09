package com.dubbo.one.api.service;

import com.dubbo.one.api.request.CreateOrderDto;
import com.dubbo.one.api.response.BaseResponse;

public interface RecordService {

    public BaseResponse createOrder(CreateOrderDto dto);

}
