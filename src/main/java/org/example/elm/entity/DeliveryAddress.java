package org.example.elm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 送货地址实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress {
    private Integer daId; // 送货地址编号
    private String contactName; // 联系人姓名
    private Integer contactSex; // 联系人性别
    private String contactTel; // 联系人电话
    private String address; // 送货地址
    private String userId; // 所属用户编号
}