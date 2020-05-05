package inventory.validate;

import inventory.model.ProductDetail;
import inventory.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ProductDetailValidator implements Validator {
	@Autowired
	private ProductDetailService productDetailService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == ProductDetail.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductDetail productDetail = (ProductDetail) target;
		ValidationUtils.rejectIfEmpty(errors, "productInfo.name", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
//		ValidationUtils.rejectIfEmpty(errors, "supplier.name", "msg.required");
//		if(productDetail.getId()!=null) {
//			ValidationUtils.rejectIfEmpty(errors, "multipartFile", "msg.required");
//		}
		if (productDetail.getCode() != null) {
			List<ProductDetail> results = productDetailService.findProductDetail("code", productDetail.getCode());
			if (results != null && !results.isEmpty()) {
				if (productDetail.getId() != null && productDetail.getId() != 0) {
					if (results.get(0).getId() != productDetail.getId()) {
						errors.rejectValue("code", "msg.name.exist");
					}
				} else {
					errors.rejectValue("code", "msg.name.exist");
				}

			}
		}

	}

}
