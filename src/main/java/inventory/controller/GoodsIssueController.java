package inventory.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import inventory.model.*;
import inventory.service.InvoiceTempService;
import inventory.service.ProductInfoService;
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
import org.springframework.web.servlet.ModelAndView;

import inventory.service.GoodsReceiptReport;
import inventory.service.InvoiceService;
import inventory.util.Constant;
import inventory.validate.InvoiceValidator;

@Controller
public class GoodsIssueController {
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceValidator invoiceValidator;
	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private InvoiceTempService invoiceTempService;

	static final Logger log = Logger.getLogger(GoodsIssueController.class);
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if(binder.getTarget()==null) {
			return;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if(binder.getTarget().getClass()== Invoice.class) {
			binder.setValidator(invoiceValidator);
		}
	}
	@RequestMapping(value= {"/goods-issue/list","/goods-issue/list/"})
	
	public String redirect() {
		return "redirect:/goods-issue/list/1";
	}
	
	@RequestMapping(value="/goods-issue/list/{page}")
	public String showInvoiceList(Model model,HttpSession session , @ModelAttribute("searchForm") Invoice invoice,@PathVariable("page") int page) throws Exception {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		if(invoice==null) {
			invoice = new Invoice();
		}
		List<InvoiceTemp> invoiceTempList = invoiceTempService.findInvoiceTemp("activeFlag",1);
		for(InvoiceTemp invoiceTemp : invoiceTempList)
		{
			invoiceTempService.deleteInvoiceTemp(invoiceTemp);
		}
		invoice.setType(Constant.TYPE_GOODS_ISSUES);
		List<Invoice> invoices = invoiceService.getList(invoice,paging);
		for (Invoice invoice1 : invoices)
		{
			InvoiceTemp invoiceTemp = new InvoiceTemp();
			invoiceTemp.setCode(invoice1.getCode());
			invoiceTemp.setProductName(invoice1.getProductInfo().getName());
			invoiceTemp.setQty(invoice1.getQty());
			invoiceTemp.setPrice(invoice1.getPrice());
			invoiceTemp.setActiveFlag(1);
			invoiceTemp.setUpdateDate(invoice1.getUpdateDate());
			invoiceTempService.saveInvoiceTemp(invoiceTemp);

		}
		int totalQty = 0;
		BigDecimal totalPrice = new BigDecimal(0);
		for ( Invoice invoice1 : invoices)
		{
			totalQty+=invoice1.getQty();
			totalPrice = totalPrice.add(invoice1.getPrice());
		}
		model.addAttribute("totalQty",totalQty);
		model.addAttribute("totalPrice",totalPrice);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null ) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null ) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo", paging);
		model.addAttribute("invoices", invoices);
		return "goods-issue-list";
		
	}
	@GetMapping("/goods-issue/add")
	public String add(Model model) {
		model.addAttribute("titlePage", "Add Invoice");
		model.addAttribute("modelForm", new Invoice());
		model.addAttribute("viewOnly", false);
		model.addAttribute("mapProduct", initMapProduct());
		return "goods-issue-action";
	}
	@GetMapping("/goods-issue/edit/{id}")
	public String edit(Model model , @PathVariable("id") int id) {
		log.info("Edit invoice with id="+id);
		Invoice invoice = invoiceService.find("id",id).get(0);
		if(invoice!=null) {
			model.addAttribute("titlePage", "Edit Invoice");
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", false);
			model.addAttribute("mapProduct", initMapProduct());
			return "goods-issue-action";
		}
		return "redirect:/goods-issue/list";
	}
	@GetMapping("/goods-issue/view/{id}")
	public String view(Model model , @PathVariable("id") int id) {
		log.info("View invoice with id="+id);
		Invoice invoice = invoiceService.find("id",id).get(0);
		if(invoice!=null) {
			model.addAttribute("titlePage", "View Invoice");
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", true);
			return "goods-issue-action";
		}
		return "redirect:/goods-issue/list";
	}
	@PostMapping("/goods-issue/save")
	public String save(Model model,@ModelAttribute("modelForm") @Validated Invoice invoice,BindingResult result,HttpSession session) {
		if(result.hasErrors()) {
			if(invoice.getId()!=null) {
				model.addAttribute("titlePage", "Edit Invoice");
			}else {
				model.addAttribute("titlePage", "Add Invoice");
			}
			model.addAttribute("mapProduct", initMapProduct());
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", false);

			
		}
		invoice.setType(Constant.TYPE_GOODS_ISSUES);
		if(invoice.getId()!=null && invoice.getId()!=0) {
			
			try {
				invoiceService.update(invoice);
				session.setAttribute(Constant.MSG_SUCCESS, "Update success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			
		}else {
				try {
					invoiceService.save(invoice);
					session.setAttribute(Constant.MSG_SUCCESS, "Insert success!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
				}
		}
		return "redirect:/goods-issue/list";
		
	}

	@GetMapping("/goods-issue/delete/{id}")
	public String delete(Model model , @PathVariable("id") int id,HttpSession session) {
		log.info("Delete invoice with id="+id);
		Invoice invoice = invoiceService.find("id",id).get(0);
		if(invoice!=null) {
			try {
				invoiceService.deleteInvoice(invoice);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Delete has error!!!");
			}
		}
		return "redirect:/goods-issue/list";
	}


	@GetMapping("/goods-issue/export")
	public ModelAndView exportReport() {
		ModelAndView modelAndView = new ModelAndView();
		List<InvoiceTemp> invoiceTempList = invoiceTempService.findInvoiceTemp("activeFlag",1);

		modelAndView.addObject(Constant.KEY_GOODS_RECEIPT_REPORT, invoiceTempList);
		modelAndView.setView(new GoodsReceiptReport());
		return modelAndView;
	}
	

	private Map<String,String> initMapProduct(){
		List<ProductInfo> productInfos = productInfoService.getAllProductInfo(null, null);
		Map<String, String> mapProduct = new HashMap<>();
		for(ProductInfo productInfo : productInfos) {
			mapProduct.put(productInfo.getId().toString(),productInfo.getName());
		}
		
		return mapProduct;
	}
}
