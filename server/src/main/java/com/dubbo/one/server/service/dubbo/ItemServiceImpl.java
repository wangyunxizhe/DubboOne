package com.dubbo.one.server.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.one.api.enums.StatusCode;
import com.dubbo.one.api.response.BaseResponse;
import com.dubbo.one.api.service.ItemService;
import com.dubbo.one.model.entity.ItemInfo;
import com.dubbo.one.model.mapper.ItemInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * 注意：这里的@Service注解是阿里巴巴包下关于dubbo的Service注解，而不是spring的那个
 */
@Service(protocol = {"dubbo", "rest"}, validation = "true", version = "1.0", timeout = 3000)
@Path("dubboOne")//该注解的作用是标明dubbo服务的路径，就像java中的包名一样，避免重复
public class ItemServiceImpl implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemInfoMapper itemInfoMapper;

    /**
     * 列表查询服务
     */
    @Path("item/list")
    @Override
    public BaseResponse listItems() {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            List<ItemInfo> itemInfos = itemInfoMapper.selectAll();
            response.setData(itemInfos);
            log.info("列表数据：{}", itemInfos);
        } catch (Exception e) {
            log.error("列表查询异常：{}", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

    /**
     * 列表分页查询服务
     */
    @Path("item/page/list")
    @Override
    public BaseResponse listPageItems(@QueryParam("pageNo") Integer pageNo,
                                      @QueryParam("pageNo") Integer pageSize) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            PageHelper.startPage(pageNo, pageSize);
            PageInfo<ItemInfo> info = new PageInfo<>(itemInfoMapper.selectAll());
            response.setData(info);
        } catch (Exception e) {
            log.error("分页查询异常：{}", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

    /**
     * 列表分页模糊查询服务
     */
    @Path("item/page/list/param")
    @Override
    public BaseResponse listPageByParams(@QueryParam("pageNo") Integer pageNo,
                                         @QueryParam("pageNo") Integer pageSize,
                                         @QueryParam("pageNo") String name) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            PageHelper.startPage(pageNo, pageSize);
            PageInfo<ItemInfo> info = new PageInfo<>(itemInfoMapper.listPageByParams(name));
            response.setData(info);
        } catch (Exception e) {
            log.error("分页模糊查询异常：{}", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

}

