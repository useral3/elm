package org.example.elm.controller;

import org.example.elm.entity.Favorite;
import org.example.elm.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /** 切换收藏状态 */
    @PostMapping("/toggleBusiness")
    public Map<String, Object> toggleBusiness(@RequestParam String userId, @RequestParam Integer businessId) {
        return favoriteService.toggleFavorite(userId, businessId);
    }

    /** 检查是否已收藏 */
    @PostMapping("/isBusinessFavorited")
    public Map<String, Object> isBusinessFavorited(@RequestParam String userId, @RequestParam Integer businessId) {
        boolean favorited = favoriteService.isFavorited(userId, businessId);
        return Map.of("favorited", favorited);
    }

    /** 用户收藏列表 */
    @PostMapping("/listFavoriteBusinesses")
    public List<Favorite> listFavoriteBusinesses(@RequestParam String userId) {
        return favoriteService.listFavorites(userId);
    }
}
