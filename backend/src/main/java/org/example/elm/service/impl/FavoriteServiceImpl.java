package org.example.elm.service.impl;

import org.example.elm.entity.Favorite;
import org.example.elm.mapper.FavoriteMapper;
import org.example.elm.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public Map<String, Object> toggleFavorite(String userId, Integer businessId) {
        Map<String, Object> result = new HashMap<>();

        int count = favoriteMapper.countByUserAndBusiness(userId, businessId);
        if (count > 0) {
            // 已收藏 → 取消
            favoriteMapper.deleteFavorite(userId, businessId);
            result.put("favorited", false);
        } else {
            // 未收藏 → 收藏
            favoriteMapper.insertFavorite(userId, businessId);
            result.put("favorited", true);
        }
        result.put("success", true);
        return result;
    }

    @Override
    public boolean isFavorited(String userId, Integer businessId) {
        return favoriteMapper.countByUserAndBusiness(userId, businessId) > 0;
    }

    @Override
    public List<Favorite> listFavorites(String userId) {
        return favoriteMapper.listByUser(userId);
    }
}
