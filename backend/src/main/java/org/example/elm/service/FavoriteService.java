package org.example.elm.service;

import org.example.elm.entity.Favorite;

import java.util.List;
import java.util.Map;

public interface FavoriteService {

    /** 切换收藏状态（收藏→取消，未收藏→收藏），返回最终状态 */
    Map<String, Object> toggleFavorite(String userId, Integer businessId);

    /** 检查是否已收藏 */
    boolean isFavorited(String userId, Integer businessId);

    /** 查询用户收藏列表 */
    List<Favorite> listFavorites(String userId);
}
