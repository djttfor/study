package my.controller;

import com.github.pagehelper.PageInfo;
import my.domain.Product;
import my.service.ProductService;
import my.service.impl.ProductServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Resource(name = "productService")
    private ProductService productService;

    /**
    * Description:
    * <未分页>
    * @return:  ModelAndView
    * @Author: DJ
    * @Date: 2020-03-29 16:51
    */
    @RequestMapping("/all-ll")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<Product> productList = productService.findAll();
        mv.addObject("productList", productList);
        mv.setViewName("product-list");
        return mv;
    }

    @RequestMapping("/all")
    /**
    * Description:
    * <分页查询所有产品>
    * @param pageNum 当前页
     * @param pageSize 当前页数
    * @return: org.springframework.web.servlet.ModelAndView
    * @Author: DJ
    * @Date: 2020-03-29 17:00
    */
    public ModelAndView findAllPaging(@RequestParam(name = "pageNum",required = true,defaultValue = "1") int pageNum,
                                      @RequestParam(name = "pageSize",required = true,defaultValue = "4") int pageSize){
        ModelAndView mv = new ModelAndView();
        List<Product> productList = productService.findAllPaging(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(productList);
        //pageInfo.setList(productList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("product-list");
        return mv;
    }


    /**
     * Description:
     * <添加商品>
     * @param product
     * @return: java.lang.String
     * @Author: DJ
     * @Date: 2020-04-04 17:20
     */
    @RequestMapping("/addProduct")
    public String addProduct(Product product){
        int i = productService.saveProduct(product);
        System.out.println(i);
        return "redirect:all";
    }

    @RequestMapping("/show")
    public ModelAndView showProduct(@RequestParam(name = "id",required = true) String id) throws Exception {
        Product product = productService.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("product", product);
        mv.setViewName("product_show");
        return mv;
    }

    /**
     * 删除产品
     * @return
     */
    @RequestMapping("/delete")
    public String deleteProduct(@RequestParam(name = "id",required = true) String id) throws Exception {
        productService.deleteProduct(id);
        return "redirect:all";
    }
    @RequestMapping("/delAll")
    public String delAll(@RequestParam(name = "pids",required = true)String pids) throws Exception {
        String[] ids = pids.split("@");
        for (String id : ids) {
            System.out.println(id);
        }
        productService.delSelected(pids);
        return "redirect:all";
    }


/**
* Description:
* <测试>
* @return: mv
* @Author: DJ
* @Date: 2020-04-04 17:21
*/
    @RequestMapping("/test")
    public ModelAndView test(Product product){
        ModelAndView mv = new ModelAndView();
        System.out.println(product);
        mv.setViewName("main");
        return mv;
    }

}