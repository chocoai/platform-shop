package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.LoginUser;
import com.platform.dto.BuyGoodsDTO;
import com.platform.entity.*;
import com.platform.redis.ApiBuyKey;
import com.platform.redis.RedisService;
import com.platform.service.ApiAddressService;
import com.platform.service.ApiCouponService;
import com.platform.service.ApiProductService;
import com.platform.util.ApiBaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buy")
public class ApiBuyController extends ApiBaseAction {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ApiAddressService addressService;
    @Autowired
    private ApiCouponService apiCouponService;
    @Autowired
    private ApiProductService productService;

    @RequestMapping("/add")
    public Object addBuy(@LoginUser UserVo loginUser) {
        JSONObject jsonParam = getJsonRequest();
        Integer goodsId = jsonParam.getInteger("goodsId");
        Integer productId = jsonParam.getInteger("productId");
        Integer number = jsonParam.getInteger("number");
        BuyGoodsDTO goodsDTO = new BuyGoodsDTO();
        goodsDTO.setGoodsId(goodsId);
        goodsDTO.setProductId(productId);
        goodsDTO.setNumber(number);
        this.redisService.set(ApiBuyKey.goods(), loginUser.getUserId()+"", goodsDTO);
        return toResponsMsgSuccess("添加成功");
    }

}
