<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="//cdnjs.cloudflare.com/ajax/libs/numeral.js/2.0.6/numeral.min.js"></script> 
<style>
.price{
	font-size: 14px;
}
</style>
<div class="right_col" role="main">
	<div class="">

		<div class="clearfix"></div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Goods Receipt</h2>

					<div class="clearfix"></div>
				</div>

				<div class="x_content">
					<a href="<c:url value="/goods-receipt/add"/>" class="btn btn-app"><i class="fa fa-plus"></i>Add</a>
<%--					<form:form  modelAttribute="searchForm" servletRelativeAction="/goods-receipt/export"  method="GET"> <a href="<c:url value="/goods-receipt/export"/>" class="btn btn-app"><i class="fa fa-cloud-download"></i>Export</a> </form:form>--%>
					<a href="<c:url value="/goods-receipt/export"/>" class="btn btn-app"><i class="fa fa-cloud-download"></i>Export</a>
					<div class="container" style="padding: 50px;">
						<%--@elvariable id="searchForm" type=""--%>
						<form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/goods-receipt/list/1"  method="POST">
<%--							<div class="form-group"><a href="<c:url value="/goods-receipt/export"/>" class="btn btn-app"><i class="fa fa-cloud-download"></i>Export</a></div>--%>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">Code </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="code" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="productInfo.name">Name </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="productInfo.name" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="fromDate">From Date</label>
								<div class="col-md-6 col-sm-6 col-xs-12 ">
									<div class="input-group date" id='fromDatePicker'>
										<form:input path="fromDate" class="form-control" />
										<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="toDate">To Date </label>
								<div class="col-md-6 col-sm-6 col-xs-12 ">
									<div class="input-group date" id='toDatePicker'>
										<form:input path="toDate" class="form-control" />
										<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>

								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="shelf.name">Shelf </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="shelf.name" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button type="submit" class="btn btn-success">Search</button>
									<button class="btn btn-success"><a href="<c:url value="/goods-receipt/getAll/1"/>"  style="color: white">Get All</a></button>

								</div>
							</div>

						</form:form>
					</div>

					<div class="table-responsive">
						<table class="table table-striped jambo_table bulk_action">
							<thead>
								<tr class="headings">
									<th class="column-title">#</th>
									<th class="column-title">Code</th>
									<th class="column-title">Qty</th>
									<th class="column-title">Price</th>
									<th class="column-title">Product</th>
									<th class="column-title">Update Date</th>
									<th class="column-title">Supplier</th>
									<th class="column-title">Shelf</th>
									<th class="column-title">User</th>

									<th class="column-title no-link last text-center" colspan="3"><span class="nobr">Action</span></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${invoices}" var="invoice" varStatus="loop">

									<c:choose>
										<c:when test="${loop.index%2==0 }">
											<tr class="even pointer">
										</c:when>
										<c:otherwise>
											<tr class="odd pointer">
										</c:otherwise>
									</c:choose>
									<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
									<td class=" ">${invoice.code }</td>
									<td class=" ">${invoice.qty }</td>
									<td class="price">${invoice.price }</td>
									<td class=" ">${invoice.productInfo.name }</td>
									<td class="date">${invoice.updateDate}</td>
									<td class=" ">${invoice.supplier.name}</td>
									<td class=" ">${invoice.shelf.name}</td>
									<td class=" ">${invoice.user.name}</td>

									<td class="text-center"><a href="<c:url value="/goods-receipt/view/${invoice.id }"/>" class="btn btn-round btn-default">View</a></td>
									<td class="text-center"><a href="<c:url value="/goods-receipt/edit/${invoice.id }"/>" class="btn btn-round btn-primary">Edit</a></td>
									<td class="text-center"><a href="javascript:void(0);" onclick="confirmDelete(${invoice.id});" class="btn btn-round btn-danger">Delete</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>

						<div class="table-responsive">
							<table class="table table-striped jambo_table bulk_action">
								<tbody>
									<td width="35px"></td>
									<td width="71.2px">Total</td>
									<td width="54px">${totalQty}</td>
									<td class="price" width="85px">${totalPrice }</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tbody>
							</table>
						</div>

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
			 window.location.href = '<c:url value="/goods-receipt/delete/"/>'+id;
		 }
	 }
	 function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/goods-receipt/list/"/>'+page);

		 $('#searchForm').submit();
	 }


	 $(document).ready(function(){
		 processMessage();
		 $('#fromDatePicker').datetimepicker({
			 format : 'YYYY-MM-DD HH:mm:ss'
		 });
		 $('#toDatePicker').datetimepicker({
			 format : 'YYYY-MM-DD HH:mm:ss'
		 })
		  $('.price').each(function(){ 
			 $(this).text(numeral($(this).text()).format('0,0'));
		 }) 
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