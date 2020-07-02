package inventory.controller;

import inventory.model.*;
import inventory.service.*;
import inventory.util.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProductDoneDetailController {
    @Autowired
    private ProductStatusDetailService productStatusDetailService;
    @Autowired
    private ProductStatusListService productStatusListService;
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductDetailService productDetailService;

    static final Logger log = Logger.getLogger(ProductDoneDetailController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

    }

    @RequestMapping(value= {"/product-done-detail/list","/product-done-detail/list/"})

    public String redirect() {
        return "redirect:/product-done-detail/list/1";
    }

    @RequestMapping(value="/product-done-detail/list/{page}")
    public String showProductInfoList(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusDetail productStatusDetail, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        if (productStatusDetail.getProductStatusList() == null) {
            productStatusDetail.setProductStatusList(new ProductStatusList());
        }
        productStatusDetail.getProductStatusList().setType(Constant.PRODUCT_DONE);
        List<ProductStatusDetail> productStatusDetails = productStatusDetailService.getAllProductStatusDetail(productStatusDetail,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", productStatusDetails);
        return "productDoneDetail-list";

    }
    @GetMapping("/product-done-detail/{productDoneListId}/add")
    public String add(Model model, @PathVariable("productDoneListId") int productDoneListId) {
        model.addAttribute("titlePage", "Add Product Done Detail");
//
        ProductStatusList productStatusListFind = productStatusListService.findByIdProductStatusList(productDoneListId);
//        vatDetail.setVat(vat);
        model.addAttribute("modelForm", new ProductStatusDetail());

        List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
        Map<String, String> mapProductInfo = new HashMap<>();
        for(ProductInfo productInfo : productInfos) {
            mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
        }

//        Map<String, String> mapVat = new HashMap<>();
//        mapVat.put(String.valueOf(vat.getId()), vat.getCode());
        List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(productStatusListFind, null);
//        Collections.sort(productStatusLists,new UpdateDateCompatatorVat());
        Map<String, String> mapProductStatusList = new HashMap<>();
        for(ProductStatusList productStatusList : productStatusLists) {
            mapProductStatusList.put(String.valueOf(productStatusList.getId()), productStatusList.getCode());
        }

        model.addAttribute("mapProductStatusList",mapProductStatusList);
        model.addAttribute("mapProductStatusList",mapProductStatusList);
        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapProductInfo", mapProductInfo);

        model.addAttribute("viewOnly", false);
        return "productDoneDetail-action";
    }

    @GetMapping("/product-done-detail/edit/{id}")
    public String edit(Model model ,@PathVariable("id") int id) {
        log.info("Edit Product Done Detail with id="+id);
        ProductStatusDetail productStatusDetail = productStatusDetailService.findByIdProductStatusDetail(id);
        if(productStatusDetail!=null) {

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }
            productStatusDetail.setProductInfoId(productStatusDetail.getProductInfo().getId());



            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("titlePage", "Edit Product Done Detail");
            model.addAttribute("modelForm", productStatusDetail);
            model.addAttribute("viewOnly", false);
            return "productDoneDetail-action";
        }
        return "redirect:/product-done-detail/list";
    }

    @GetMapping("/product-done-detail/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View productDetail with id="+id);
        ProductStatusDetail productStatusDetail = productStatusDetailService.findByIdProductStatusDetail(id);
        if(productStatusDetail!=null) {
            model.addAttribute("titlePage", "View Product Done Detail");
            model.addAttribute("modelForm", productStatusDetail);
            model.addAttribute("viewOnly", true);
            return "productDoneDetail-action";
        }
        return "redirect:/product-done-detail/list";
    }

    @PostMapping("/product-done-detail/save")
    public String save(Model model,@ModelAttribute("modelForm") @Validated ProductStatusDetail productStatusDetail,BindingResult result,HttpSession session) {
        if(result.hasErrors()) {
            if(productStatusDetail.getId()!=null) {
                model.addAttribute("titlePage", "Edit Product Done Detail");
            }else {
                model.addAttribute("titlePage", "Add Product Done Detail");
            }

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }

            List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(null, null);
//            Collections.sort(productStatusLists,new UpdateDateCompatatorVat());
            Map<String, String> mapProductStatusList = new HashMap<>();
            for(ProductStatusList productStatusList : productStatusLists) {
                mapProductStatusList.put(String.valueOf(productStatusList.getId()), productStatusList.getCode());
            }

//            List<Vat> vats = vatService.findVat("code",vatDetail.getVat().getCode());
//            Map<String, String> mapVat = new HashMap<>();
//            for (Vat vat : vats) {
//                mapVat.put(String.valueOf(vat.getId()), vat.getCode());
//            }
//            model.addAttribute("mapVat",vats);
            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapProductStatusList", mapProductStatusList);

            model.addAttribute("modelForm", productStatusDetail);
            model.addAttribute("viewOnly", false);
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(productStatusDetail.getProductInfoId());
        productStatusDetail.setProductInfo(productInfo);

        ProductStatusList productStatusList = new ProductStatusList();
        productStatusList.setId(productStatusDetail.getProductStatusListId());
        productStatusDetail.setProductStatusList(productStatusList);


        if(productStatusDetail.getId()!=null && productStatusDetail.getId()!=0) {
            try {

                productStatusDetailService.updateProductStatusDetail(productStatusDetail);
                ProductStatusList productStatusList1 = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
                productStatusList1.setPrice(productStatusList1.getPrice().add(productStatusDetail.getPriceTotal()));

                productStatusListService.updateProductStatusList(productStatusList1);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        }else {
            try {
                productStatusDetailService.saveProductStatusDetail(productStatusDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
                ProductStatusList productStatusList1 = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
                productStatusList1.setPrice(productStatusList1.getPrice().add(productStatusDetail.getPriceTotal()));
                productStatusListService.updateProductStatusList(productStatusList1);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/product-done-detail/list";

    }
    @GetMapping("/product-done-detail/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete productStatusDetail with id="+id);
        ProductStatusDetail productStatusDetail = productStatusDetailService.findByIdProductStatusDetail(id);
        if(productStatusDetail!=null) {
            try {
                productStatusDetailService.deleteProductStatusDetail(productStatusDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
                ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(productStatusDetail.getProductStatusList().getId());
                productStatusList.setPrice(productStatusList.getPrice().subtract(productStatusDetail.getPriceTotal()));
                productStatusListService.updateProductStatusList(productStatusList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-done-detail/list";
    }
}
