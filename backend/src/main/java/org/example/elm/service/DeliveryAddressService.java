package org.example.elm.service;

import org.example.elm.entity.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {
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
    int saveDeliveryAddress(String contactName, Integer contactSex, String contactTel, String address, String userId);
    
    /**
     * 根据送货地址编号更新送货地址信息
     */
    int updateDeliveryAddress(Integer daId, String contactName, Integer contactSex, String contactTel, String address, String userId);
    
    /**
     * 根据送货地址编号删除一条记录
     */
    int removeDeliveryAddress(Integer daId);
}