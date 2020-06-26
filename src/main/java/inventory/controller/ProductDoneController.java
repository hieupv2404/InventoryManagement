package inventory.controller;

import inventory.model.*;
import inventory.service.ProductDetailService;
import inventory.service.ProductStatusListService;
import inventory.service.UserService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductDoneController {
    @Autowired
    private ProductStatusListService productStatusListService;

    @Autowired
    private ProductDetailService productDetailService;
    
    @Autowired
    private UserService userService;

    static final Logger log = Logger.getLogger(ProductDoneController.class);

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//        if(binder.getTarget().getClass()== Vat.class) {
//            binder.setValidator(vatValidator);
//        }
    }

    @RequestMapping(value= {"/product-done-list/list","/product-done-list/list/"})

    public String redirect() {
        return "redirect:/product-done-list/list/1";
    }

    @RequestMapping(value= "/product-done-list/list/{page}")
    public String showProductDone(Model model, HttpSession session , @ModelAttribute("searchForm") ProductStatusList productStatusList, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        productStatusList.setType(Constant.PRODUCT_DONE);
        List<ProductStatusList> productStatusLists = productStatusListService.getAllProductStatusList(productStatusList,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", productStatusLists);
        return "product-done-list-list";

    }
    @GetMapping("/product-done-list/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Product Status");
        model.addAttribute("modelForm", new ProductStatusList());

        List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
        Map<String, String> mapSupplier = new HashMap<>();
        for(Supplier supplier : suppliers) {
            mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
        }



        model.addAttribute("mapSupplier",mapSupplier);
        model.addAttribute("viewOnly", false);
        return "product-done-list-action";
    }
    @GetMapping("/product-done-list/edit/{id}")
    public String edit(Model model , @PathVariable("id") int id) {
        log.info("Edit ProductStatusList with id="+id);
        ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(id);
        if(productStatusList!=null) {

            List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Supplier supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
            }
            productStatusList.setSupplierId(productStatusList.getSupplier().getId());

            model.addAttribute("titlePage", "Edit ProductStatusList");
            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("modelForm", productStatusList);
            model.addAttribute("viewOnly", false);
            return "product-done-list-action";
        }
        return "redirect:/product-done-list/list";
    }
    @GetMapping("/product-done-list/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View ProductStatusList with id="+id);
        ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(id);
        if(productStatusList!=null) {
            model.addAttribute("titlePage", "View ProductStatusList");
            model.addAttribute("modelForm", productStatusList);
            model.addAttribute("viewOnly", true);
            return "product-done-list-action";
        }
        return "redirect:/product-done-list/list";
    }
    @PostMapping("/product-done-list/save")
    public String save(Model model, @ModelAttribute("modelForm")  @Validated ProductStatusList productStatusList, BindingResult result, HttpSession session) {
        if(result.hasErrors())
        {
            if(productStatusList.getId()!=null) {
                model.addAttribute("titlePage", "Edit Vat");
            }else {
                model.addAttribute("titlePage", "Add Vat");
            }

            List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Supplier supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
            }



            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("modelForm", productStatusList);
            model.addAttribute("viewOnly", false);
        }

        Supplier supplier = new Supplier();
        supplier.setId(productStatusList.getSupplierId());
        productStatusList.setSupplier(supplier);

        Users user = userService.findByProperty("status",1).get(0);
        productStatusList.setUser(user);
        productStatusList.setUserId(user.getId());

        productStatusList.setType(Constant.PRODUCT_DONE);

        if(productStatusList.getId()!=null && productStatusList.getId()!=0 ) {
            try {
                productStatusListService.updateProductStatusList(productStatusList);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }
        }
        else {
            try {
                productStatusListService.saveProductStatusList(productStatusList);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/product-done-list/list";

    }
    @GetMapping("/product-done-list/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete ProductStatusList with id="+id);
        ProductStatusList productStatusList = productStatusListService.findByIdProductStatusList(id);
        if(productStatusList!=null) {
            try {
                productStatusListService.deleteProductStatusList(productStatusList);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-done-list/list";
    }
}
