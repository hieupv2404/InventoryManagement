package inventory.validate;

import inventory.model.Vat;
import inventory.service.VatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class VatValidator implements Validator {
	@Autowired
	private VatService vatService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Vat.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Vat vat = (Vat) target;
		ValidationUtils.rejectIfEmpty(errors, "supplier.name", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");

//		if (productInfo.getName() != null) {
//			List<ProductInfo> results = vatService.findProductInfo("name", productInfo.getName());
//			if (results != null && !results.isEmpty()) {
//				if (productInfo.getId() != null && productInfo.getId() != 0) {
//					if (results.get(0).getId() != productInfo.getId()) {
//						errors.rejectValue("name", "msg.name.exist");
//					}
//				} else {
//					errors.rejectValue("name", "msg.name.exist");
//				}
//
//			}
//		}


	}

}
