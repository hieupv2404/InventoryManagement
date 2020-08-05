package inventory.controller;

import inventory.model.Paging;
import inventory.model.Supplier;
import inventory.service.SupplierService;
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
import java.util.List;

@Controller
public class SupplierController {
	@Autowired
	private SupplierService supplierService;


	static final Logger log = Logger.getLogger(SupplierController.class);
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		
	}
	@RequestMapping(value= {"/supplier/list","/supplier/list/"})
	
	public String redirect() {
		return "redirect:/supplier/list/1";
	}
	
	@RequestMapping(value="/supplier/list/{page}")
	public String showSupplierList(Model model, HttpSession session , @ModelAttribute("searchForm") Supplier supplier, @PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Supplier> categories = supplierService.getAllSupplier(supplier,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("categories", categories);
		return "supplier-list";
		
	}

	@RequestMapping(value="/supplier/getAll/{page}")
	public String getAll(Model model, HttpSession session , @ModelAttribute("searchForm") Supplier supplier, @PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Supplier> categories = supplierService.getAllSupplier(null,paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("categories", categories);
		return "supplier-list";

	}
	@GetMapping("/supplier/add")
	public String add(Model model) {
		model.addAttribute("titlePage", "Add Supplier");
		model.addAttribute("modelForm", new Supplier());
		model.addAttribute("viewOnly", false);
		return "supplier-action";
	}
	@GetMapping("/supplier/edit/{id}")
	public String edit(Model model , @PathVariable("id") int id) {
		log.info("Edit supplier with id="+id);
		Supplier supplier = supplierService.findByIdSupplier(id);
		if(supplier!=null) {
			model.addAttribute("titlePage", "Edit Supplier");
			model.addAttribute("modelForm", supplier);
			model.addAttribute("viewOnly", false);
			return "supplier-action";
		}
		return "redirect:/supplier/list";
	}

	@GetMapping("/supplier/view/{id}")
	public String view(Model model , @PathVariable("id") int id) {
		log.info("View supplier with id="+id);
		Supplier supplier = supplierService.findByIdSupplier(id);
		if(supplier!=null) {
			model.addAttribute("titlePage", "View Supplier");
			model.addAttribute("modelForm", supplier);
			model.addAttribute("viewOnly", true);
			return "supplier-action";
		}
		return "redirect:/supplier/list";
	}
	@PostMapping("/supplier/save")
	public String save(Model model, @ModelAttribute("modelForm") @Validated Supplier supplier, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			if(supplier.getId()!=null) {
				model.addAttribute("titlePage", "Edit Supplier");
			}else {
				model.addAttribute("titlePage", "Add Supplier");
			}
			
			model.addAttribute("modelForm", supplier);
			model.addAttribute("viewOnly", false);
			return "supplier-action";
			
		}
		supplier.setActiveFlag(1);
		if(supplier.getId()!=null && supplier.getId()!=0) {
			try {
				supplierService.updateSupplier(supplier);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			
		}else {
				try {
					supplierService.saveSupplier(supplier);
					session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
				}
		}
		return "redirect:/supplier/list";
		
	}
	@GetMapping("/supplier/delete/{id}")
	public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
		log.info("Delete supplier with id="+id);
		Supplier supplier = supplierService.findByIdSupplier(id);
		if(supplier!=null) {
			try {
				supplierService.deleteSupplier(supplier);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
			}
		}
		return "redirect:/supplier/list";
	}
	
}
