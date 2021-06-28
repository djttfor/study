package my.service;


import my.domain.Product;

import java.util.List;

public interface ProductService {
    /**
     * 未分页
     * @return product结果集
     */
    List<Product> findAll();

    /**
     * 分页
     * @param pageNum 当前页
     * @param pageSize 当前页数
     * @return List<Product>结果集
     */
    List<Product> findAllPaging(int pageNum,int pageSize);

    /**
     * 保存新产品
     * @param product 封装的数据
     * @return 返回结果
     */
    int saveProduct(Product product);

    Product findById(String id)throws Exception;

    int deleteProduct(String id)throws Exception;

    int delSelected(String pids) throws Exception;
}
