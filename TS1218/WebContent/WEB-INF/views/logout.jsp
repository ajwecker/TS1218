<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../common/header.jspf"%>
<body class="flex-column d-flex justify-content-between ${dir}">
		<script type="text/javascript">
		window.page_name = 'logout';
		$("[data-link='logout']").addClass('active');
	</script>
	<%@include file="../common/navigation.jspf"%>

	<%
		if (session != null) {
			session.invalidate();
		}
	%>
	<div>
		<h1>
			<fmt:message key="main.work_area.Good_bye_Message1" />
		</h1>
		<fmt:message key="main.work_area.Good_bye_Message2" />
		${userlinecount}

		<p>Page under Construction
	</div>
	<br>
	<br>


	<br>
	<br>
	<!-- img src='<c:url value="/images/elijah.png" ></c:url>' /-->
	<%@ include file="../common/footer.jspf"%>