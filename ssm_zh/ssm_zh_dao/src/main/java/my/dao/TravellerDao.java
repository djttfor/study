package my.dao;

import my.domain.Traveller;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TravellerDao {
    @Select("select * from traveller where id in (select traveller_id from orders_traveller where orders_id=#{orderId})")
    @Results(id = "ftbIM" ,value = {
            @Result(property = "id",column = "id" ,id = true),
            @Result(property = "name",column = "name"),
            @Result(property = "sex",column = "sex"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "credentialsType",column = "credentials_type"),
            @Result(property = "credentialsNumber",column = "credentials_number"),
            @Result(property = "travellerType",column = "traveller_type")
    })
    List<Traveller> findTravellerById(String orderId);
}
