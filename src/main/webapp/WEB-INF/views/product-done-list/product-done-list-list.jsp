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
					<h2>Product Done List</h2>

					<div class="clearfix"></div>
				</div>


				<div class="x_content">
					<a href="<c:url value="/product-done-list/add"/>" class="btn btn-app"><i class="fa fa-plus"></i>Add</a>
					<a href="<c:url value="/product-done-detail/list"/>" class="btn btn-app"><i class="fa fa-forward"></i>Product Done Item</a>
					<div class="container" style="padding: 50px;">
						<%--@elvariable id="searchForm" type=""--%>
						<form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/product-done-list/list/1" method="POST">
							<div class="form-group">
								<label for="id" class="control-label col-md-3 col-sm-3 col-xs-12">ID</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="id" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="code">Code </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="code" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="supplier.name">Supplier </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="supplier.name" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="user.name">User </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="user.name" cssClass="form-control col-md-7 col-xs-12" />
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
									<th class="column-title">Code</th>
									<th class="column-title">Supplier</th>
									<th class="column-title">Price</th>
									<th class="column-title">User</th>
									<th class="column-title">Update Date</th>
									<th class="column-title no-link last text-center" colspan="4"><span class="nobr">Action</span></th>
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
									<td class=" ">${product.code }</td>
									<td class=" ">${product.supplier.name }</td>

									<td class="price">${product.price }</td>
									<td class=" ">${product.user.name }</td>
									<td class=" ">${product.updateDate }</td>

									<td class="text-center"><a href="<c:url value="/product-done-list/view/${product.id }"/>" class="btn btn-round btn-default">View</a></td>
									<td class="text-center"><a href="<c:url value="/product-done-list/edit/${product.id }"/>" class="btn btn-round btn-primary">Edit</a></td>
									<td class="text-center"><a href="javascript:void(0);" onclick="confirmDelete(${product.id});" class="btn btn-round btn-danger">Delete</a></td>
									<td class="text-center"><a href="<c:url value="/product-done-detail/${product.id }/add"/>" class="btn btn-round btn-info">Add Item</a></td>

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
			 window.location.href = '<c:url value="/product-done-list/delete/"/>'+id;
		 }
	 }
	 function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/product-done-list/list/"/>'+page);
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