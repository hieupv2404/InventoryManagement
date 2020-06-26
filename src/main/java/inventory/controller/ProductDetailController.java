package inventory.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import inventory.model.*;
import inventory.service.ProductDetailService;
import inventory.service.ProductInfoService;
import inventory.validate.ProductDetailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import inventory.util.Constant;
import inventory.validate.ProductInfoValidator;

@Controller
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductInfoValidator productInfoValidator;

    @Autowired
    private ProductDetailValidator productDetailValidator;

    @Autowired
    private ProductInfoService productInfoService;

    static final Logger log = Logger.getLogger(ProductDetailController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if(binder.getTarget().getClass()== ProductDetail.class) {
            binder.setValidator(productDetailValidator);
        }
    }
    @RequestMapping(value= {"/product-detail/list","/product-detail/list/"})

    public String redirect() {
        return "redirect:/product-detail/list/1";
    }

    @RequestMapping(value="/product-detail/list/{page}")
    public String showProductInfoList(Model model,HttpSession session , @ModelAttribute("searchForm") ProductDetail productDetail,@PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<ProductDetail> products = productDetailService.getAllProductDetail(productDetail,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", products);
        return "productDetail-list";

    }
    @GetMapping("/product-detail/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Product Detail");
        model.addAttribute("modelForm", new ProductDetail());

        List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
        Map<String, String> mapProductInfo = new HashMap<>();
        for(ProductInfo productInfo : productInfos) {
            mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
        }

        List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
        Map<String, String> mapSupplier = new HashMap<>();
        for(Supplier supplier : suppliers) {
            mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
        }

        List<Invoice> invoices = productDetailService.getAllInvoice(null, null);
        Map<String, String> mapInvoice = new HashMap<>();
        for(Invoice invoice : invoices) {
            mapInvoice.put(String.valueOf(invoice.getId()), invoice.getCode());
        }

        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapSupplier",mapSupplier);
        model.addAttribute("mapSupplier",mapSupplier);
        model.addAttribute("mapInvoice",mapInvoice);
        model.addAttribute("mapInvoice",mapInvoice);
        model.addAttribute("viewOnly", false);
        return "productDetail-action";
    }
    @GetMapping("/product-detail/edit/{id}")
    public String edit(Model model , @PathVariable("id") int id) {
        log.info("Edit productDetail with id="+id);
        ProductDetail productDetail = productDetailService.findByIdProductDetail(id);
        if(productDetail!=null) {

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }
            productDetail.setProductInfoId(productDetail.getProductInfo().getId());

            List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Supplier supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
            }
            productDetail.setSupplierId(productDetail.getSupplier().getId());

            List<Invoice> invoices = productDetailService.getAllInvoice(null, null);
            Map<String, String> mapInvoice = new HashMap<>();
            for(Invoice invoice : invoices) {
                mapInvoice.put(String.valueOf(invoice.getId()), invoice.getCode());
            }
            productDetail.setInvoiceId(productDetail.getInvoice().getId());

            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("mapInvoice",mapInvoice);
            model.addAttribute("titlePage", "Edit ProductDetail");
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("viewOnly", false);
            return "productDetail-action";
        }
        return "redirect:/product-detail/list";
    }
    @GetMapping("/product-detail/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View productDetail with id="+id);
        ProductDetail productDetail = productDetailService.findByIdProductDetail(id);
        if(productDetail!=null) {
            model.addAttribute("titlePage", "View ProductDetail");
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("viewOnly", true);
            return "productDetail-action";
        }
        return "redirect:/product-detail/list";
    }
    @PostMapping("/product-detail/save") 
    public String save(Model model,@ModelAttribute("modelForm") @Validated ProductDetail productDetail,BindingResult result,HttpSession session) {
        if(result.hasErrors()) {
            if(productDetail.getId()!=null) {
                model.addAttribute("titlePage", "Edit Product Detail");
            }else {
                model.addAttribute("titlePage", "Add Product Detail");
            }

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }

            List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Supplier supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
            }

            List<Invoice> invoices = productDetailService.getAllInvoice(null, null);
            Map<String, String> mapInvoice = new HashMap<>();
            for(Invoice invoice : invoices) {
                mapInvoice.put(String.valueOf(invoice.getId()), invoice.getCode());
            }

            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("mapInvoice",mapInvoice);
            model.addAttribute("modelForm", productDetail);
            model.addAttribute("viewOnly", false);
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(productDetail.getProductInfoId());
        productDetail.setProductInfo(productInfo);

        Supplier supplier = new Supplier();
        supplier.setId(productDetail.getSupplierId());
        productDetail.setSupplier(supplier);

        Invoice invoice = new Invoice();
        invoice.setId(productDetail.getInvoiceId());
        productDetail.setInvoice(invoice);

        if(productDetail.getId()!=null && productDetail.getId()!=0) {
            try {

                productDetailService.updateProductDetail(productDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        }else {
            try {
                productDetailService.saveProductDetail(productDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/product-detail/list";

    }
    @GetMapping("/product-detail/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete productDetail with id="+id);
        ProductDetail productDetail = productDetailService.findByIdProductDetail(id);
        if(productDetail!=null) {
            try {
                productDetailService.deleteProductDetail(productDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/product-detail/list";
    }

}
