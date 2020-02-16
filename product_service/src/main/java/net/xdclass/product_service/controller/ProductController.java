package net.xdclass.product_service.controller;

import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/product")
//@RefreshScope用于从git不重启服务，更新配置文件bootstrap.yml，一般在最先调用的服务上配置
//必须先手动刷新http://localhost:8771/actuator/bus-refresh后,其它服务才会动态刷新修改的配置，修改env的值进行测试。
//使用@RefreshScope时，必须指定可读spring.cloud.config.discovery.enabled: true!
//@RefreshScope
public class ProductController {

    @Value("${server.port}")
    private String port;

//    @Value("${env}")
//    private String env;

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品列表
     * @return
     */
    @RequestMapping("list")
    public Object list(){
        return productService.listProduct();
    }


    /**
     * 根据id查找商品详情
     * @param id
     * @return
     */
    @RequestMapping("find")
    public Object findById(int id){
    /*链接追踪，测试延迟*/
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Product product = productService.findById(id);

        Product result = new Product();
        BeanUtils.copyProperties(product,result);
//        result.setName( result.getName() + " data from port="+port +" env = "+env );
        result.setName( result.getName() + " data from port="+port );
        return result;
    }
}
