package org.example.elm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.elm.entity.Favorite;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    /** 添加收藏 */
    int insertFavorite(@Param("userId") String userId, @Param("businessId") Integer businessId);

    /** 取消收藏 */
    int deleteFavorite(@Param("userId") String userId, @Param("businessId") Integer businessId);

    /** 检查是否已收藏 */
    int countByUserAndBusiness(@Param("userId") String userId, @Param("businessId") Integer businessId);

    /** 某商家被收藏次数 */
    int countByBusiness(@Param("businessId") Integer businessId);

    /** 查询用户收藏列表（含商家详情） */
    List<Favorite> listByUser(@Param("userId") String userId);
}
