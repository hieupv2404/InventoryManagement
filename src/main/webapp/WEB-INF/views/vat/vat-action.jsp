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
						<form:form modelAttribute="modelForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/vat/save" method="POST">
							<form:hidden path="id" />
							<form:hidden path="percent" />
							<form:hidden path="price" />
							<form:hidden path="total" />
							<form:hidden path="createDate" />
							<form:hidden path="activeFlag" />
							<div class="form-group">

								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="supplierId">Supplier <span class="required">*</span>
								</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<c:choose>
										<c:when test="${!viewOnly}">
										
											<form:select path="supplierId" cssClass="form-control">
												<form:options items="${mapSupplier}" />
											</form:select>
											<div class="has-error">
												<form:errors path="supplierId" cssClass="help-block"></form:errors>
											</div>
										</c:when>
										<c:otherwise>
											<form:input path="supplier.name" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
										</c:otherwise>
									</c:choose>
								</div>

							</div>

<%--							<div class="form-group">--%>
<%--								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">Code <span class="required">*</span>--%>
<%--								</label>--%>
<%--								<div class="col-md-6 col-sm-6 col-xs-12">--%>
<%--									<form:input path="code" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />--%>
<%--									<div class="has-error">--%>
<%--										<form:errors path="code" cssClass="help-block"></form:errors>--%>
<%--									</div>--%>
<%--								</div>--%>
<%--							</div>--%>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">Code <span class="required">*</span></label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="code" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />
									<div class="has-error">
										<form:errors path="code" cssClass="help-block"></form:errors>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="tax">Tax</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="tax" cssClass="form-control col-md-7 col-xs-12" disabled="${viewOnly}" />
									<div class="has-error">
										<form:errors path="tax" cssClass="help-block"></form:errors>
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
				$('#vatlistId').addClass('current-page').siblings()
						.removeClass('current-page');
				var parent = $('#vatlistId').parents('li');
				parent.addClass('active').siblings().removeClass('active');
				$('#vatlistId').parents().show();
			});
	function cancel() {
		window.location.href = '<c:url value="/vat/list"/>'
	}
</script>