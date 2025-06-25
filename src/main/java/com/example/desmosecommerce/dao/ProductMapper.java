package com.example.desmosecommerce.dao;

import com.example.desmosecommerce.entity.Product;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM products")
    List<Product> findAll();

    @Select("SELECT * FROM products WHERE id = #{id}")
    Product findById(Long id);

    @Select("SELECT * FROM products WHERE name LIKE CONCAT('%', #{searchTerm}, '%')")
    List<Product> searchByName(String searchTerm);

    @Insert("INSERT INTO products (name, description, price, stock, imageUrl, is_discounted) " +
            "VALUES (#{name}, #{description}, #{price}, #{stock}, #{imageUrl}, #{isDiscounted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    @Update("UPDATE products SET name = #{name}, description = #{description}, " +
            "price = #{price}, stock = #{stock}, imageUrl = #{imageUrl}, " +
            "is_discounted = #{isDiscounted} WHERE id = #{id}")
    int update(Product product);

    @Delete("DELETE FROM products WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE products SET stock = stock - #{quantity} WHERE id = #{id}")
    int updateStock(@Param("id") Long id, @Param("quantity") Integer quantity);
} 