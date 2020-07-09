package inventory.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import inventory.model.*;
import inventory.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.analysis.function.Abs;
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

import inventory.util.Constant;
import inventory.validate.InvoiceValidator;

@Controller
public class GoodsReceiptController {
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceValidator invoiceValidator;
	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private UserService userService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private InvoiceTempService invoiceTempService;

	@Autowired
	private ProductDetailService productDetailService;
	static final Logger log = Logger.getLogger(GoodsReceiptController.class);
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
	@RequestMapping(value= {"/goods-receipt/list","/goods-receipt/list/"})
	
	public String redirect() {
		return "redirect:/goods-receipt/list/1";
	}
	
	@RequestMapping(value="/goods-receipt/list/{page}")
	public String showInvoiceList(Model model,HttpSession session ,
//								  HttpServletRequest request, @RequestParam("digest") int digest,
								  @ModelAttribute("searchForm") Invoice invoice,@PathVariable("page") int page) throws Exception {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		if (invoice == null) {
			invoice = new Invoice();
		}
		List<InvoiceTemp> invoiceTempList = invoiceTempService.findInvoiceTemp("activeFlag",1);
		for(InvoiceTemp invoiceTemp : invoiceTempList)
		{
			invoiceTempService.deleteInvoiceTemp(invoiceTemp);
		}
		invoice.setType(Constant.TYPE_GOODS_RECEIPT);
		List<Invoice> invoices = invoiceService.getList(invoice, paging);
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

//		if (session.getAttribute("DigestRequest") == null) {
//			// lan vao dau tien, Digest null va tham so null
//
//			if (StringUtils.isEmpty(invoice.getCode()) && invoice.getFromDate() == null && invoice.getToDate() == null) {
//				session.setAttribute("DigestRequest", System.currentTimeMillis());
//			}
//
//		}
//		// load lai trang, giu nguyen session
//		else
//		{
//			// tu trang khac ve
//			if ()
//		}
//			session.setAttribute("searchedCode", invoice.getCode());
//			session.setAttribute("searchedFromDate", invoice.getFromDate());
//			session.setAttribute("searchedToDate", invoice.getToDate());
//		}

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

		return "goods-receipt-list";
		
	}
	@GetMapping("/goods-receipt/add")
	public String add(Model model) {

		model.addAttribute("titlePage", "Add Invoice");
		model.addAttribute("modelForm", new Invoice());

		List<Supplier> suppliers = supplierService.getAllSupplier(null, null);
		Map<String, String> mapSupplier = new HashMap<>();
		for(Supplier supplier : suppliers) {
			mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
		}


		model.addAttribute("mapSupplier",mapSupplier);
		model.addAttribute("mapSupplier",mapSupplier);
//		model.addAttribute("mapSupplier",initMapSupplier());
		model.addAttribute("viewOnly", false);
		model.addAttribute("mapProduct", initMapProduct());
		return "goods-receipt-action";
	}
	@GetMapping("/goods-receipt/edit/{id}")
	public String edit(Model model , @PathVariable("id") int id) {
		log.info("Edit invoice with id="+id);
		Invoice invoice = invoiceService.find("id",id).get(0);
		if(invoice!=null) {
			model.addAttribute("titlePage", "Edit Invoice");

			List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
			Map<String, String> mapSupplier = new HashMap<>();
			for(Supplier supplier : suppliers) {
				mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
			}

			invoice.setSupplierId(invoice.getSupplier().getId());

			model.addAttribute("mapSupplier",initMapSupplier());
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", false);
			model.addAttribute("mapProduct", initMapProduct());
			return "goods-receipt-action";
		}
		return "redirect:/goods-receipt/list";
	}
	@GetMapping("/goods-receipt/view/{id}")
	public String view(Model model , @PathVariable("id") int id) {
		log.info("View invoice with id="+id);
		Invoice invoice = invoiceService.find("id",id).get(0);
		if(invoice!=null) {
			model.addAttribute("titlePage", "View Invoice");
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", true);
			return "goods-receipt-action";
		}
		return "redirect:/goods-receipt/list";
	}
	@PostMapping("/goods-receipt/save")
	public String save(Model model,@ModelAttribute("modelForm") @Validated Invoice invoice,BindingResult result,HttpSession session) throws Exception {
		if(result.hasErrors()) {
			if(invoice.getId()!=null) {
				model.addAttribute("titlePage", "Edit Invoice");
			}else {
				model.addAttribute("titlePage", "Add Invoice");
			}

			List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
			Map<String, String> mapSupplier = new HashMap<>();
			for(Supplier supplier : suppliers) {
				mapSupplier.put(String.valueOf(supplier.getId()), supplier.getName());
			}

			model.addAttribute("mapSupplier",mapSupplier);
			model.addAttribute("mapProduct", initMapProduct());
			model.addAttribute("modelForm", invoice);
			model.addAttribute("viewOnly", false);


		}
		Supplier supplier = new Supplier();
		supplier.setId(invoice.getSupplierId());
		invoice.setSupplier(supplier);

		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(invoice.getProductId());
		invoice.setProductInfo(productInfo);

		invoice.setType(Constant.TYPE_GOODS_RECEIPT);
		Users user = userService.findByProperty("status",1).get(0);
		invoice.setUser(user);

		List <Invoice> invoiceListCode = invoiceService.find("code",invoice.getCode());


		if (invoiceListCode.size()!=0)
		{
			for(Invoice invoice1:invoiceListCode)
			{
				if(invoice.getProductInfo().getId().equals(invoice1.getProductInfo().getId()))
				{
					invoice1.setQty(invoice1.getQty()+ invoice.getQty());
					if (invoice.getPrice().compareTo(invoice1.getPrice())!=0) {
						invoice1.setPrice(invoice.getPrice());
					}

					invoiceService.update(invoice1);
					session.setAttribute(Constant.MSG_SUCCESS, "Update success "+invoice1.getCode());

					return "redirect:/goods-receipt/list";
				}
			}
		}
		if(invoice.getId()!=null && invoice.getId()!=0) {
			try {
				int checkQty=0, checkPrice = 0;

					if(invoice.getPrice().compareTo(new BigDecimal(0)) < 0)
					{
						invoice.setPrice(invoice.getPrice().abs());
						checkPrice = 1;
					}
					if(invoice.getQty()<0)
					{
						invoice.setQty(Math.abs(invoice.getQty()));
						checkQty=1;
					}

				invoiceService.update(invoice);
				if (checkQty==1) session.setAttribute(Constant.MSG_SUCCESS,"Qty has ABS-ed and Update success!!!");
				if (checkPrice==1) session.setAttribute(Constant.MSG_SUCCESS,"Price has ABS-ed and Update success!!!");
				if (checkPrice==1 && checkQty==1) session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS and Price has ABS and Update success!!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				session.setAttribute(Constant.MSG_ERROR, "Update has error");
			}
			
		}else {
				try {
					int checkQty=0, checkPrice = 0;

					if(invoice.getPrice().compareTo(new BigDecimal(0)) < 0)
					{
						invoice.setPrice(invoice.getPrice().abs());
						checkPrice = 1;
					}
					if(invoice.getQty()<0)
					{
						invoice.setQty(Math.abs(invoice.getQty()));
						checkQty=1;
					}

					invoiceService.save(invoice);
					if (checkQty==1) session.setAttribute(Constant.MSG_SUCCESS,"Qty has ABS-ed and Insert success!!!");
					if (checkPrice==1) session.setAttribute(Constant.MSG_SUCCESS,"Price has ABS-ed and Insert success!!!");
					if (checkPrice==1 && checkQty==1) session.setAttribute(Constant.MSG_SUCCESS, "Qty has ABS and Price has ABS and Insert success!!!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					session.setAttribute(Constant.MSG_ERROR, "Insert has error!!!");
				}
		}
		return "redirect:/goods-receipt/list";
		
	}

	@GetMapping("/goods-receipt/delete/{id}")
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
		return "redirect:/goods-receipt/list";
	}
	@GetMapping("/goods-receipt/export")
	public ModelAndView exportReport(Model model, HttpSession session , @ModelAttribute("searchForm") @RequestBody InvoiceTemp invoiceTemp
								) {
		ModelAndView modelAndView = new ModelAndView();
//		invoiceTemp.setType(Constant.TYPE_GOODS_RECEIPT);
//		invoice.setCode(code);
//		invoice.setFromDate(fromDate);
//		invoice.setToDate(toDate);
//		List<Invoice> invoices = invoiceService.getList(invoiceTemp, null);
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

	private Map<String,String> initMapSupplier(){
		List<Supplier> suppliers = productDetailService.getAllSupplier(null, null);
		Map<String, String> mapSupplier = new HashMap<>();
		for(Supplier supplier : suppliers) {
			mapSupplier.put(supplier.getId().toString(),supplier.getName());
		}

		return mapSupplier;
	}
}
