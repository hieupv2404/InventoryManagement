package inventory.controller;

import inventory.model.*;
import inventory.service.*;
import inventory.util.Constant;
import inventory.validate.InvoiceValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class VatDetailController {
    @Autowired
    private VatDetailService vatDetailService;
    @Autowired
    private VatService vatService;
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductDetailService productDetailService;

    static final Logger log = Logger.getLogger(VatDetailController.class);
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

    }

    @RequestMapping(value= {"/vat-detail/list","/vat-detail/list/"})

    public String redirect() {
        return "redirect:/vat-detail/list/1";
    }

    @RequestMapping(value="/vat-detail/list/{page}")
    public String showProductInfoList(Model model, HttpSession session , @ModelAttribute("searchForm") VatDetail vatDetail, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
//        Vat vat = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        List<VatDetail> vatDetails = vatDetailService.getAllVatDetail(vatDetail,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", vatDetails);
        return "vatDetail-list";

    }
    @GetMapping("/vat-detail/{vatId}/add")
    public String add(Model model, @PathVariable("vatId") int vatId) {
        model.addAttribute("titlePage", "Add Vat Detail");
//
        Vat vatFind = vatService.findByIdVat(vatId);
//        vatDetail.setVat(vat);
        model.addAttribute("modelForm", new VatDetail());

        List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
        Map<String, String> mapProductInfo = new HashMap<>();
        for(ProductInfo productInfo : productInfos) {
            mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
        }

//        Map<String, String> mapVat = new HashMap<>();
//        mapVat.put(String.valueOf(vat.getId()), vat.getCode());
        List<Vat> vats = vatService.getAllVat(vatFind, null);
        Collections.sort(vats,new UpdateDateCompatatorVat());
        Map<String, String> mapVat = new HashMap<>();
        for(Vat vat : vats) {
            mapVat.put(String.valueOf(vat.getId()), vat.getCode());
        }

        model.addAttribute("mapVat",mapVat);
        model.addAttribute("mapVat",mapVat);
        model.addAttribute("mapProductInfo", mapProductInfo);
        model.addAttribute("mapProductInfo", mapProductInfo);

        model.addAttribute("viewOnly", false);
        return "vatDetail-action";
    }

    @GetMapping("/vat-detail/edit/{id}")
    public String edit(Model model ,@PathVariable("id") int id) {
        log.info("Edit vat Detail with id="+id);
        VatDetail vatDetail = vatDetailService.findByIdVatDetail(id);
        if(vatDetail!=null) {

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }
            vatDetail.setProductInfoId(vatDetail.getProductInfo().getId());



            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("titlePage", "Edit Vat Detail");
            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("viewOnly", false);
            return "vatDetail-action";
        }
        return "redirect:/vat-detail/list";
    }

    @GetMapping("/vat-detail/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View productDetail with id="+id);
        VatDetail vatDetail = vatDetailService.findByIdVatDetail(id);
        if(vatDetail!=null) {
            model.addAttribute("titlePage", "View Vat Detail");
            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("viewOnly", true);
            return "vatDetail-action";
        }
        return "redirect:/vat-detail/list";
    }

    @PostMapping("/vat-detail/save")
    public String save(Model model,@ModelAttribute("modelForm") @Validated VatDetail vatDetail,BindingResult result,HttpSession session) {
        if(result.hasErrors()) {
            if(vatDetail.getId()!=null) {
                model.addAttribute("titlePage", "Edit Vat Detail");
            }else {
                model.addAttribute("titlePage", "Add Vat Detail");
            }

            List<ProductInfo> productInfos = productDetailService.getAllProductInfo(null, null);
            Map<String, String> mapProductInfo = new HashMap<>();
            for(ProductInfo productInfo : productInfos) {
                mapProductInfo.put(String.valueOf(productInfo.getId()), productInfo.getName());
            }

            List<Vat> vats = vatService.getAllVat(null, null);
            Collections.sort(vats,new UpdateDateCompatatorVat());
            Map<String, String> mapVat = new HashMap<>();
            for(Vat vat : vats) {
                mapVat.put(String.valueOf(vat.getId()), vat.getCode());
            }

//            List<Vat> vats = vatService.findVat("code",vatDetail.getVat().getCode());
//            Map<String, String> mapVat = new HashMap<>();
//            for (Vat vat : vats) {
//                mapVat.put(String.valueOf(vat.getId()), vat.getCode());
//            }
//            model.addAttribute("mapVat",vats);
            model.addAttribute("mapProductInfo", mapProductInfo);
            model.addAttribute("mapVat", mapVat);

            model.addAttribute("modelForm", vatDetail);
            model.addAttribute("viewOnly", false);
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(vatDetail.getProductInfoId());
        vatDetail.setProductInfo(productInfo);

        Vat vat = new Vat();
        vat.setId(vatDetail.getVatId());
        vatDetail.setVat(vat);

        if(vatDetail.getId()!=null && vatDetail.getId()!=0) {
            try {

                vatDetailService.updateVatDetail(vatDetail);
                Vat vat1 = vatService.findByIdVat(vatDetail.getVat().getId());
                vat1.setPrice(vat1.getPrice().add(vatDetail.getPriceTotal()));
                vat1.setTotal(vat1.getPrice().add(vat1.getPercent().multiply(vat1.getPrice())));
                vatService.updateVat(vat1);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.error(e.getMessage());
                session.setAttribute(Constant.MSG_ERROR, "Update has error");
            }

        }else {
            try {
                vatDetailService.saveVatDetail(vatDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
                Vat vat1 = vatService.findByIdVat(vatDetail.getVat().getId());
                vat1.setPrice(vat1.getPrice().add(vatDetail.getPriceTotal()));
                vat1.setTotal(vat1.getPrice().add(vat1.getPercent().multiply(vat1.getPrice())));
                vatService.updateVat(vat1);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/vat-detail/list";

    }
    @GetMapping("/vat-detail/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete vatDetail with id="+id);
        VatDetail vatDetail = vatDetailService.findByIdVatDetail(id);
        if(vatDetail!=null) {
            try {
                vatDetailService.deleteVatDetail(vatDetail);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
                Vat vat = vatService.findByIdVat(vatDetail.getVat().getId());
                vat.setPrice(vat.getPrice().subtract(vatDetail.getPriceTotal()));
                vat.setTotal(vat.getPrice().add(vat.getPercent().multiply(vat.getPrice())));
                vatService.updateVat(vat);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/vat-detail/list";
    }
}
