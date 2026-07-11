package org.example.elm.service.impl;

import org.example.elm.entity.DeliveryAddress;
import org.example.elm.mapper.DeliveryAddressMapper;
import org.example.elm.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    
    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;
    
    @Override
    public List<DeliveryAddress> listDeliveryAddressByUserId(String userId) {
        return deliveryAddressMapper.listDeliveryAddressByUserId(userId);
    }
    
    @Override
    public DeliveryAddress getDeliveryAddressById(Integer daId) {
        return deliveryAddressMapper.getDeliveryAddressById(daId);
    }
    
    @Override
    public int saveDeliveryAddress(String contactName, Integer contactSex, String contactTel, String address, String userId) {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setContactName(contactName);
        deliveryAddress.setContactSex(contactSex);
        deliveryAddress.setContactTel(contactTel);
        deliveryAddress.setAddress(address);
        deliveryAddress.setUserId(userId);
        return deliveryAddressMapper.saveDeliveryAddress(deliveryAddress);
    }
    
    @Override
    public int updateDeliveryAddress(Integer daId, String contactName, Integer contactSex, String contactTel, String address, String userId) {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setDaId(daId);
        deliveryAddress.setContactName(contactName);
        deliveryAddress.setContactSex(contactSex);
        deliveryAddress.setContactTel(contactTel);
        deliveryAddress.setAddress(address);
        deliveryAddress.setUserId(userId);
        return deliveryAddressMapper.updateDeliveryAddress(deliveryAddress);
    }
    
    @Override
    public int removeDeliveryAddress(Integer daId) {
        return deliveryAddressMapper.removeDeliveryAddress(daId);
    }
}