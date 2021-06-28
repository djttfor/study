package my.dao;

import my.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

public interface ProductDao {
    /*结果集*/
    @Results(id = "productMap",value = {
            @Result (property = "pid",column = "id" ,id = true ),
            @Result (property = "productNumber",column = "product_number" ),
            @Result (property = "productName",column = "product_name" ),
            @Result (property = "cityName",column = "city_name" ),
            @Result (property = "departureTime",column = "departure_time" ),
            @Result (property = "productPrice",column = "product_price" ),
            @Result (property = "productDesc",column = "product_desc" ),
            @Result (property = "productStatus",column = "product_status" )
    })
    @Select("select * from product")
    /**
    * Description:
    * <查询所有产品>
    * @param
    * @return: java.util.List<my.domain.Product>
    * @Author: DJ
    * @Date: 2020-03-29 21:56
    */
    List<Product> findAll();


    @Insert("Insert into product(product_number,product_name,city_name,departure_time,product_price,product_desc,product_status) values(" +
            "#{productNumber,jdbcType=VARCHAR}," +
            "#{productName,jdbcType=VARCHAR}," +
            "#{cityName,jdbcType=VARCHAR}," +
            "#{departureTime,jdbcType=TIMESTAMP}," +
            "#{productPrice,jdbcType=VARCHAR}," +
            "#{productDesc,jdbcType=VARCHAR}," +
            "#{productStatus,jdbcType=INTEGER})")
    /**
    * Description:
    * <保存产品>
    * @param product
    * @return: int
    * @Author: DJ
    * @Date: 2020-03-29 21:56
    */
    int saveProduct(Product product);


    /**
    * Description:
    * <根据id查找产品>
    * @param id uid
    * @return: my.domain.Product
    * @Author: DJ
    * @Date: 2020-03-29 22:05
    */
    @Select("select * from product where id =#{id}")
    @ResultMap("productMap")
    Product findProductById(String id)throws Exception;

    @Delete("delete from product where id = #{id}")
    int deleteProduct(String id);

}
