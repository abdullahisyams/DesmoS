package com.example.desmosecommerce.dao;

import com.example.desmosecommerce.entity.CartItem;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CartItemMapper {
    @Select("SELECT * FROM cart_items WHERE user_id = #{userId}")
    List<CartItem> findByUserId(Long userId);

    @Select("SELECT * FROM cart_items WHERE id = #{id}")
    CartItem findById(Long id);

    @Insert("INSERT INTO cart_items (user_id, product_id, quantity) VALUES (#{userId}, #{productId}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CartItem cartItem);

    @Update("UPDATE cart_items SET quantity = #{quantity} WHERE id = #{id}")
    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    @Delete("DELETE FROM cart_items WHERE id = #{id}")
    int delete(Long id);

    @Delete("DELETE FROM cart_items WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);
} 