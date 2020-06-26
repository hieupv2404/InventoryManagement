package inventory.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import inventory.model.*;
import inventory.service.ProductDetailService;
import inventory.service.VatService;
import inventory.validate.VatValidator;
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

@Controller
public class VatController {
    @Autowired
    private VatService vatService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private VatValidator vatValidator;

    static final Logger log = Logger.getLogger(VatController.class);

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if(binder.getTarget()==null) {
            return;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        if(binder.getTarget().getClass()== Vat.class) {
            binder.setValidator(vatValidator);
        }
    }

    @RequestMapping(value= {"/vat/list","/vat/list/"})

    public String redirect() {
        return "redirect:/vat/list/1";
    }

    @RequestMapping(value= "/vat/list/{page}")
    public String showVat(Model model, HttpSession session , @ModelAttribute("searchForm") Vat vat, @PathVariable("page") int page) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<Vat> vats = vatService.getAllVat(vat,paging);
        if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("products", vats);
        return "vat-list";

    }
    @GetMapping("/vat/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Vat");
        model.addAttribute("modelForm", new Vat());

        List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
        Map<String, String> mapSupplier = new HashMap<>();
        for(Supplier supplier : suppliers) {
            mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
        }

        model.addAttribute("mapSupplier",mapSupplier);
        model.addAttribute("viewOnly", false);
        return "vat-action";
    }
    @GetMapping("/vat/edit/{id}")
    public String edit(Model model , @PathVariable("id") int id) {
        log.info("Edit vat with id="+id);
        Vat vat = vatService.findByIdVat(id);
        if(vat!=null) {

            List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
            Map<String, String> mapSupplier = new HashMap<>();
            for(Supplier supplier : suppliers) {
                mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
            }
            vat.setSupplierId(vat.getSupplier().getId());

            model.addAttribute("titlePage", "Edit Vat");
            model.addAttribute("mapSupplier", mapSupplier);
            model.addAttribute("modelForm", vat);
            model.addAttribute("viewOnly", false);
            return "vat-action";
        }
        return "redirect:/vat/list";
    }
    @GetMapping("/vat/view/{id}")
    public String view(Model model , @PathVariable("id") int id) {
        log.info("View vat with id="+id);
        Vat vat = vatService.findByIdVat(id);
        if(vat!=null) {
            model.addAttribute("titlePage", "View Vat");
            model.addAttribute("modelForm", vat);
            model.addAttribute("viewOnly", true);
            return "vat-action";
        }
        return "redirect:/vat/list";
    }
    @PostMapping("/vat/save")
    public String save(Model model, @ModelAttribute("modelForm")  @Validated Vat vat, BindingResult result, HttpSession session) {
        if(result.hasErrors())
        {
            if(vat.getId()!=null) {
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
            model.addAttribute("modelForm", vat);
            model.addAttribute("viewOnly", false);
        }

        Supplier supplier = new Supplier();
        supplier.setId(vat.getSupplierId());
        vat.setSupplier(supplier);

        if(vat.getId()!=null && vat.getId()!=0 ) {
            try {
                vatService.updateVat(vat);
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
                vatService.saveVat(vat);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
            }
        }
        return "redirect:/vat/list";

    }
    @GetMapping("/vat/delete/{id}")
    public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
        log.info("Delete productInfo with id="+id);
        Vat vat = vatService.findByIdVat(id);
        if(vat!=null) {
            try {
                vatService.deleteVat(vat);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
            }
        }
        return "redirect:/vat/list";
    }
}
