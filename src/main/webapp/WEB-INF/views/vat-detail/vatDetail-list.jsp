<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right_col" role="main">
	<div class="">

		<div class="clearfix"></div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Vat Detail List</h2>

					<div class="clearfix"></div>
				</div>


				<div class="x_content">
					<a href="<c:url value="/vat/list"/>" class="btn btn-app"><i class="fa fa-backward"></i>Vat List</a>
					<div class="container" style="padding: 50px;">
						<%--@elvariable id="searchForm" type=""--%>
						<form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/vat-detail/list/1" method="POST">
							<div class="form-group">
								<label for="id" class="control-label col-md-3 col-sm-3 col-xs-12">ID</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="id" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>
<%--							<div class="form-group">--%>

<%--								&lt;%&ndash;@declare id="supplierName"&ndash;%&gt;--%>
<%--								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="supplier.name">Supplier </label>--%>
<%--								<div class="col-md-6 col-sm-6 col-xs-12">--%>
<%--									<form:input path="supplier.name" cssClass="form-control col-md-7 col-xs-12" />--%>
<%--								</div>--%>
<%--							</div>--%>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="vat.code">Vat </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="vat.code" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>
							<div class="form-group">
								<%--@declare id="productinfo.name"--%>
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="productInfo.name">Name </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="productInfo.name" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">

								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="fromPriceOne">From Price One </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="fromPriceOne" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="toPriceOne">To Price One </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="toPriceOne" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="fromPriceTotal">From Price Total </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="fromPriceTotal" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="toPriceTotal">To Price Total </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="toPriceTotal" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>



							<div class="form-group">
								<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button type="submit" class="btn btn-success">Search</button>
								</div>
							</div>

						</form:form>
					</div>

					<div class="table-responsive">
						<table class="table table-striped jambo_table bulk_action">
							<thead>
								<tr class="headings">
									<th class="column-title">#</th>
									<th class="column-title">Id</th>
									<th class="column-title">Vat</th>
									<th class="column-title">Product</th>
									<th class="column-title">Qty</th>
									<th class="column-title">Price One</th>
									<th class="column-title">Price Total</th>

									<th class="column-title no-link last text-center" colspan="3"><span class="nobr">Action</span></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${products}" var="product" varStatus="loop">

									<c:choose>
										<c:when test="${loop.index%2==0 }">
											<tr class="even pointer">
										</c:when>
										<c:otherwise>
											<tr class="odd pointer">
										</c:otherwise>
									</c:choose>
									<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
									<td class=" ">${product.id }</td>
									<td class=" ">${product.vat.code }</td>
									<td class=" ">${product.productInfo.name }</td>
									<td class=" ">${product.qty }</td>
									<td class=" ">${product.priceOne }</td>
									<td class=" ">${product.priceTotal }</td>

									<td class="text-center"><a href="<c:url value="/vat-detail/view/${product.id }"/>" class="btn btn-round btn-default">View</a></td>
									<td class="text-center"><a href="<c:url value="/vat-detail/edit/${product.id }"/>" class="btn btn-round btn-primary">Edit</a></td>
									<td class="text-center"><a href="javascript:void(0);" onclick="confirmDelete(${product.id});" class="btn btn-round btn-danger">Delete</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						<jsp:include page="../layout/paging.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	 function confirmDelete(id){
		 if(confirm('Do you want delete this record?')){
			 window.location.href = '<c:url value="/vat-detail/delete/"/>'+id;
		 }
	 }
	 function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/vat-detail/list/"/>'+page);
		 $('#searchForm').submit();
	 }
	 $(document).ready(function(){
		 processMessage();
	 });
	 function processMessage(){
		 var msgSuccess = '${msgSuccess}';
		 var msgError = '${msgError}';
		 if(msgSuccess){
			 new PNotify({
                 title: ' Success',
                 text: msgSuccess,
                 type: 'success',
                 styling: 'bootstrap3'
             });
		 }
		 if(msgError){
			 new PNotify({
                 title: ' Error',
                 text: msgError,
                 type: 'error',
                 styling: 'bootstrap3'
             });
		 }
	 }
</script>