package com.dubbo.one.server.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.one.api.enums.StatusCode;
import com.dubbo.one.api.request.CreateOrderDto;
import com.dubbo.one.api.response.BaseResponse;
import com.dubbo.one.api.service.RecordService;
import com.dubbo.one.model.entity.ItemInfo;
import com.dubbo.one.model.entity.OrderRecord;
import com.dubbo.one.model.mapper.ItemInfoMapper;
import com.dubbo.one.model.mapper.OrderRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Service(protocol = {"dubbo", "rest"}, validation = "true", version = "1.0", timeout = 3000)
@Path("record")
public class RecordServiceImpl implements RecordService {

    private static final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private ItemInfoMapper itemInfoMapper;

    @Autowired
    private OrderRecordMapper orderMapper;

    /**
     * 下单服务
     * <p>
     * 注意：使用dubbo框架的rest协议时，在配置完成后使用以下请求，可直接从浏览器请求到生产者的接口
     * http://localhost:9013/dubboone/record/create
     * 详细配置：
     * 1)端口配置在dubbo.properties中的dubbo.protocol.rest.port
     * 2)路径中的dubboone为自定义的上下文，配置在spring-dubbo.xml中，name为rest的<dubbo:protoco>标签中
     * 3)/record/create与springmvc中的@RequestMapping注解效果类似，dubbo中使用@Path注解，
     * 作用都是表明请求时的详细路径
     */
    @Override
    @Path("create")
    @POST//当dubbo使用http协议时（即rest），必须以post方式请求。注意：这是dubbo的注解
    @Consumes(value = MediaType.APPLICATION_JSON)//以json格式提交请求参数
    @Produces(value = MediaType.APPLICATION_JSON)
    public BaseResponse createOrder(CreateOrderDto dto) {
        if (dto.getItemId() == null || dto.getItemId() <= 0 ||
                StringUtils.isEmpty(dto.getCustomerName()) || dto.getTotal() == null) {
            return new BaseResponse(StatusCode.ErrParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            //校验商品是否存在
            ItemInfo itemInfo = itemInfoMapper.selectByPrimaryKey(dto.getItemId());
            if (itemInfo == null) {
                return new BaseResponse(StatusCode.ItemNotExist);
            }
            //这里省略各种业务校验
            //下单
            OrderRecord order = new OrderRecord();
            BeanUtils.copyProperties(dto, order);
            order.setOrderTime(new Date());
            orderMapper.insertSelective(order);
            response.setData(order.getId());
        } catch (Exception e) {
            log.error("下单异常：{}", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

}
