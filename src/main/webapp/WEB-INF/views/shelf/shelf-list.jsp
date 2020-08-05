<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right_col" role="main">
	<div class="">

		<div class="clearfix"></div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Shelf List</h2>

					<div class="clearfix"></div>
				</div>


				<div class="x_content">
					<a href="<c:url value="/shelfoop/add"/>" class="btn btn-app"><i class="fa fa-plus"></i>Add</a>
					<div class="container" style="padding: 50px;">
						<%--@elvariable id="searchForm" type="inventory.controller.ShelfController"--%>
						<form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/shelfoop/list/1" method="POST">
							<div class="form-group">
								<%--@declare id="description"--%>
									<label for="description" class="control-label col-md-3 col-sm-3 col-xs-12">ID</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="id" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">Name </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="name" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">Description </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="description" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12" for="qtyRest">Qty Rest </label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<form:input path="qtyRest" cssClass="form-control col-md-7 col-xs-12" />
								</div>
							</div>



							<div class="form-group">
								<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button type="submit" class="btn btn-success">Search</button>
									<button class="btn btn-success"><a href="<c:url value="/shelfoop/getAll/1"/>"  style="color: white">Get All</a></button>

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

									<th class="column-title">Name</th>
									<th class="column-title">Description</th>
									<th class="column-title" >Total</th>
									<th class="column-title" >Qty</th>
									<th class="column-title" >Qty Rest</th>
									<th class="column-title no-link last text-center" colspan="3"><span class="nobr">Action</span></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${categories}" var="category" varStatus="loop">

									<c:choose>
										<c:when test="${loop.index%2==0 }">
											<tr class="even pointer">
										</c:when>
										<c:otherwise>
											<tr class="odd pointer">
										</c:otherwise>
									</c:choose>
									<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
									<td class=" ">${category.id }</td>
									<td class=" ">${category.name }</td>
									<td class=" ">${category.description }</td>
									<td class=" " style="color: deepskyblue">${category.total }</td>
									<td class=" " style="color: deepskyblue">${category.qty }</td>
									<td class=" " style="color: mediumvioletred">${category.qtyRest }</td>
									<td class="text-center"><a href="<c:url value="/shelfoop/view/${category.id }"/>" class="btn btn-round btn-default">View</a></td>
									<td class="text-center"><a href="<c:url value="/shelfoop/edit/${category.id }"/>" class="btn btn-round btn-primary">Edit</a></td>
									<td class="text-center"><a href="javascript:void(0);" onclick="confirmDelete(${category.id});" class="btn btn-round btn-danger">Delete</a></td>
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
			 window.location.href = '<c:url value="/shelfoop/delete/"/>'+id;
		 }
	 }
	 function gotoPage(page){
		 $('#searchForm').attr('action','<c:url value="/shelfoop/list/"/>'+page);
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