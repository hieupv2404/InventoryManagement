<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h2>${titlePage}</h2>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">

					<div class="x_content">
						<br />
						<%--@elvariable id="modelForm" type=""--%>
						<form:form modelAttribute="modelForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/product-done-detail/save" method="POST">
							<form:hidden path="id" />


<%--							<div class="form-group">--%>
<%--								&lt;%&ndash;@declare id="vatId"&ndash;%&gt;--%>
<%--									<label class="control-label col-md-3 col-sm-3 col-xs-12" for="vatId">Vat <span class="required">*</span>--%>
<%--								</label>--%>
<%--								<div class="col-md-6 col-sm-6 col-xs-12">--%>
<%--									<form:input path="vatId" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />--%>
<%--									<div class="has-error">--%>
<%--										<form:errors path="vatId" cssClass="help-block"></form:errors>--%>
<%--									</div>--%>
<%--								</div>--%>
<%--							</div>--%>

							<div class="form-group">


								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="productStatusListId">Product Done<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<c:choose>
										<c:when test="${!viewOnly}">

											<form:select path="productStatusListId" cssClass="form-control">
												<form:options items="${mapProductStatusList}" />
											</form:select>
											<div class="has-error">
												<form:errors path="productStatusListId" cssClass="help-block"></form:errors>
											</div>
										</c:when>
										<c:otherwise>
											<form:input path="productStatusList.code" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
										</c:otherwise>
									</c:choose>
								</div>

							</div>

							<div class="form-group">

								<%--@declare id="productinfoid"--%>
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="productInfoId">Name<span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<c:choose>
										<c:when test="${!viewOnly}">
										
											<form:select path="productInfoId" cssClass="form-control">
												<form:options items="${mapProductInfo}" />
											</form:select>
											<div class="has-error">
												<form:errors path="productInfoId" cssClass="help-block"></form:errors>
											</div>
										</c:when>
										<c:otherwise>
											<form:input path="productInfo.name" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
										</c:otherwise>
									</c:choose>
								</div>

							</div>


							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="qty">Qty <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="qty" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />
									<div class="has-error">
										<form:errors path="qty" cssClass="help-block"></form:errors>
									</div>
								</div>
							</div>

<%--							<div class="form-group">--%>
<%--								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="productInfoName">Name <span class="required">*</span>--%>
<%--								</label>--%>
<%--								<div class="col-md-6 col-sm-6 col-xs-12">--%>
<%--									<form:input path="productInfoName" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />--%>
<%--									<div class="has-error">--%>
<%--										<form:errors path="productInfo.name" cssClass="help-block"></form:errors>--%>
<%--									</div>--%>
<%--								</div>--%>
<%--							</div>--%>
<%--							<div class="form-group">--%>
<%--									<label for="supplier.name" class="control-label col-md-3 col-sm-3 col-xs-12">Supplier</label>--%>
<%--								<div class="col-md-6 col-sm-6 col-xs-12">--%>
<%--									<form:textarea path="supplier.name" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />--%>
<%--									<div class="has-error">--%>
<%--										<form:errors path="supplier.name" cssClass="help-block"></form:errors>--%>
<%--									</div>--%>
<%--								</div>--%>
<%--							</div>--%>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="priceOne">Price One <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="priceOne" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />
									<div class="has-error">
										<form:errors path="priceOne" cssClass="help-block"></form:errors>
									</div>
								</div>
							</div>


							<div class="ln_solid"></div>
							<div class="form-group">
								<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button class="btn btn-primary" type="button" onclick="cancel();">Cancel</button>
									<c:if test="${!viewOnly }">
										<button class="btn btn-primary" type="reset">Reset</button>
										<button type="submit" class="btn btn-success">Submit</button>
									</c:if>
								</div>
							</div>

						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				$('#productDone-detaillistId').addClass('current-page').siblings()
						.removeClass('current-page');
				var parent = $('#productDone-detaillistId').parents('li');
				parent.addClass('active').siblings().removeClass('active');
				$('#productDone-detaillistId').parents().show();
			});
	function cancel() {
		window.location.href = '<c:url value="/product-done-list/list"/>'
	}



</script>