<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="cuerpo">
	<h3>Welcome to Innovation Strategies Test</h3>
	<h4>
		The total number of the list is :
		<c:out value="${usersTotal}" />
	</h4>

	<table id="datatable"
		class="table table-striped table-hover dt-responsive no-wrap"
		width="100%">
		<thead>
			<tr>
				<th>id</th>
				<th>Name</th>
				<th>Phone</th>
				<th>Company</th>
				<th>Siret</th>
			</tr>
		</thead>
	</table>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var dataTableObject = $('#datatable').dataTable({
			serverSide : true,
			order : [ [ 0, "desc" ] ],
			pageLength : 50,
			ajax : {
				type : "POST",
				url : "${contextPath}/search"
			},
			columns : [ {
				data : 'id',
				visible : false
			}, {
				data : 'name'
			}, {
				data : 'phone'
			}, {
				data : 'company'
			}, {
				data : 'siret'
			} ]
		})
	});
</script>