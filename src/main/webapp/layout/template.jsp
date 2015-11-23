<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Technical Test</title>

<!-- JQuery -->
<script
	src="<c:url value="/resources/js/jquery/jquery-1.11.3.min.js" />"></script>
<!-- Datatable -->
<script type="text/javascript"
	src="<c:url value="/resources/datatable/datatables.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/datatable/dataTables.responsive.min.js" />"></script>

<!-- JQuery UI -->
<script
	src="<c:url value="/resources/jquery-ui-1.11.4.custom/jquery-ui.js" />"></script>
<script
	src="<c:url value="/resources/jquery-ui-1.11.4.custom/datepicker-es.js" />"></script>
<!-- Datatables -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/datatable/datatables.min.css" />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/datatable/dataTables.responsive.min.css" />" />

<!-- JQuery UI -->
<link
	href="<c:url value="/resources/jquery-ui-1.11.4.custom/jquery-ui.css" />"
	rel="stylesheet">
</head>
<body>
	<div id="wrap">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>

		<div id="content">
			<div class="container">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>
	<footer id="footer">
		<tiles:insertAttribute name="footer" />
	</footer>
</body>
</html>