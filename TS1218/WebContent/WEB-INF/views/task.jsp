<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@include file="../common/header.jspf"%>
<body class="flex-column d-flex justify-content-between ${dir}">
	<script type="text/javascript">
		document.getElementById("task").className += " active";
	</script>
	<%@include file="../common/navigation.jspf"%>
	<div>
		<h1>Information about the Transcription Task</h1>

		<p>Under construction
	</div>

	<!-- img src='<c:url value="/images/elijah.png" ></c:url>' /-->
	<%@ include file="../common/footer.jspf"%>

</body>

</html>