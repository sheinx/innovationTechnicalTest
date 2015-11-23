<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Technical Test</title>


<script type="text/javascript"
	src="<c:url value="/resources/datatable/jquery.dataTables.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/datatable/jquery.js" />"></script>

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