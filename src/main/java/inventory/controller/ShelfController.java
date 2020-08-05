package inventory.controller;

import inventory.model.Paging;
import inventory.model.Shelf;
import inventory.service.ShelfService;
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
public class ShelfController {
	@Autowired
	private ShelfService shelfService;

	static final Logger log = Logger.getLogger(ShelfController.class);
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		
	}
	@RequestMapping(value= {"/shelfoop/list","/shelfoop/list/"})
	
	public String redirect() {
		return "redirect:/shelfoop/list/1";
	}
	
	@RequestMapping(value="/shelfoop/list/{page}")
	public String showShelfList(Model model, HttpSession session , @ModelAttribute("searchForm") Shelf category, @PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Shelf> categories = shelfService.getAllShelf(category,paging);
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
		return "shelf-list";
		
	}

	@RequestMapping(value="/shelfoop/getAll/{page}")
	public String getAll(Model model, HttpSession session , @ModelAttribute("searchForm") Shelf category, @PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Shelf> categories = shelfService.getAllShelf(null,paging);
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
		return "shelf-list";

	}

	@GetMapping("/shelfoop/add")
	public String add(Model model) {
		model.addAttribute("titlePage", "Add Shelf");
		model.addAttribute("modelForm", new Shelf());
		model.addAttribute("viewOnly", false);
		return "shelf-action";
	}
	@GetMapping("/shelfoop/edit/{id}")
	public String edit(Model model , @PathVariable("id") int id) {
		log.info("Edit category with id="+id);
		Shelf category = shelfService.findByIdShelf(id);
		if(category!=null) {
			model.addAttribute("titlePage", "Edit Shelf");
			model.addAttribute("modelForm", category);
			model.addAttribute("viewOnly", false);
			return "shelf-action";
		}
		return "redirect:/shelfoop/list";
	}

	@GetMapping("/shelfoop/view/{id}")
	public String view(Model model , @PathVariable("id") int id) {
		log.info("View category with id="+id);
		Shelf category = shelfService.findByIdShelf(id);
		if(category!=null) {
			model.addAttribute("titlePage", "View Shelf");
			model.addAttribute("modelForm", category);
			model.addAttribute("viewOnly", true);
			return "shelf-action";
		}
		return "redirect:/shelfoop/list";
	}
	@PostMapping("/shelfoop/save")
	public String save(Model model, @ModelAttribute("modelForm") @Validated Shelf category, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			if(category.getId()!=null) {
				model.addAttribute("titlePage", "Edit Shelf");
			}else {
				model.addAttribute("titlePage", "Add Shelf");
			}
			
			model.addAttribute("modelForm", category);
			model.addAttribute("viewOnly", false);
			return "shelf-action";
			
		}
		if(category.getId()!=null && category.getId()!=0) {
			try {
				Shelf shelfTemp = shelfService.findByIdShelf(category.getId());
				category.setQty(shelfTemp.getQty());
				shelfService.updateShelf(category);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			
		}else {
				try {
					category.setQty(0);
					category.setQtyRest(category.getTotal()-category.getQty());
					shelfService.saveShelf(category);
					session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
				}
		}
		return "redirect:/shelfoop/list";
		
	}
	@GetMapping("/shelfoop/delete/{id}")
	public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
		log.info("Delete category with id="+id);
		Shelf category = shelfService.findByIdShelf(id);
		if(category!=null) {
			try {
				shelfService.deleteShelf(category);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
			}
		}
		return "redirect:/shelfoop/list";
	}
	
}
