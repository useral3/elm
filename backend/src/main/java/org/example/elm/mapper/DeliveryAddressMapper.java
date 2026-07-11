package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.elm.entity.DeliveryAddress;

import java.util.List;

@Mapper
public interface DeliveryAddressMapper {
    /**
     * 根据用户编号查询所属送货地址
     */
    List<DeliveryAddress> listDeliveryAddressByUserId(String userId);
    
    /**
     * 根据送货地址编号查询送货地址
     */
    DeliveryAddress getDeliveryAddressById(Integer daId);
    
    /**
     * 向送货地址表中添加一条记录
     */
    int saveDeliveryAddress(DeliveryAddress deliveryAddress);
    
    /**
     * 根据送货地址编号更新送货地址信息
     */
    int updateDeliveryAddress(DeliveryAddress deliveryAddress);
    
    /**
     * 根据送货地址编号删除一条记录
     */
    int removeDeliveryAddress(Integer daId);
}