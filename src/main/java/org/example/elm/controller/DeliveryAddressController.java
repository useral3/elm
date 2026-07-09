package org.example.elm.controller;

import org.example.elm.entity.DeliveryAddress;
import org.example.elm.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController {
    
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    
    /**
     * 根据用户编号查询所属送货地址
     * 参数：userId
     * 返回值：deliveryAddress数组
     * 功能：根据用户编号查询所属送货地址
     */
    @PostMapping("/listDeliveryAddressByUserId")
    public List<DeliveryAddress> listDeliveryAddressByUserId(@RequestParam String userId) {
        return deliveryAddressService.listDeliveryAddressByUserId(userId);
    }
    
    /**
     * 根据送货地址编号查询送货地址
     * 参数：daId
     * 返回值：deliveryAddress对象
     * 功能：根据送货地址编号查询送货地址
     */
    @PostMapping("/getDeliveryAddressById")
    public DeliveryAddress getDeliveryAddressById(@RequestParam Integer daId) {
        return deliveryAddressService.getDeliveryAddressById(daId);
    }
    
    /**
     * 向送货地址表中添加一条记录
     * 参数：contactName、contactSex、contactTel、address、userId
     * 返回值：int（影响的行数）
     * 功能：向送货地址表中添加一条记录
     */
    @PostMapping("/saveDeliveryAddress")
    public int saveDeliveryAddress(
            @RequestParam String contactName,
            @RequestParam Integer contactSex,
            @RequestParam String contactTel,
            @RequestParam String address,
            @RequestParam String userId) {
        System.out.println("保存送货地址: " + contactName + ", " + contactSex + ", " + contactTel + ", " + address + ", " + userId);
        return deliveryAddressService.saveDeliveryAddress(contactName, contactSex, contactTel, address, userId);
    }
    
    /**
     * 根据送货地址编号更新送货地址信息
     * 参数：daId、contactName、contactSex、contactTel、address、userId
     * 返回值：int（影响的行数）
     * 功能：根据送货地址编号更新送货地址信息
     */
    @PostMapping("/updateDeliveryAddress")
    public int updateDeliveryAddress(
            @RequestParam Integer daId,
            @RequestParam String contactName,
            @RequestParam Integer contactSex,
            @RequestParam String contactTel,
            @RequestParam String address,
            @RequestParam String userId) {
        return deliveryAddressService.updateDeliveryAddress(daId, contactName, contactSex, contactTel, address, userId);
    }
    
    /**
     * 根据送货地址编号删除一条记录
     * 参数：daId
     * 返回值：int（影响的行数）
     * 功能：根据送货地址编号删除一条记录
     */
    @PostMapping("/removeDeliveryAddress")
    public int removeDeliveryAddress(@RequestParam Integer daId) {
        return deliveryAddressService.removeDeliveryAddress(daId);
    }
}