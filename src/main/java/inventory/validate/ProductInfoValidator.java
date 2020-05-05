package inventory.validate;

import java.util.List;

import inventory.service.ProductInfoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import inventory.model.ProductInfo;

@Component
public class ProductInfoValidator implements Validator {
	@Autowired
	private ProductInfoService productInfoService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == ProductInfo.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductInfo productInfo = (ProductInfo) target;
		ValidationUtils.rejectIfEmpty(errors, "category.name", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
		if(productInfo.getId()!=null) {
			ValidationUtils.rejectIfEmpty(errors, "multipartFile", "msg.required");
		}
		if (productInfo.getName() != null) {
			List<ProductInfo> results = productInfoService.findProductInfo("name", productInfo.getName());
			if (results != null && !results.isEmpty()) {
				if (productInfo.getId() != null && productInfo.getId() != 0) {
					if (results.get(0).getId() != productInfo.getId()) {
						errors.rejectValue("name", "msg.name.exist");
					}
				} else {
					errors.rejectValue("name", "msg.name.exist");
				}

			}
		}
		if (!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {
			String extension = FilenameUtils.getExtension(productInfo.getMultipartFile().getOriginalFilename());
			if (!extension.equals("jpg") && !extension.equals("png")) {
				errors.rejectValue("multipartFile", "msg.file.extension.error");
			}
		}

	}

}
