<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../common/header.jspf"%>
<body class="${dir}">
		<script type="text/javascript">
		window.page_name = 'logout';
		$("[data-link='logout']").addClass('active');
	</script>
	<%@include file="../common/navigation.jspf"%>

	<div class="container mt-2">
		<h1>
			<fmt:message key="main.work_area.Good_bye_Message1" />
		</h1>
		<c:if test="${userid != null && userid != 'guest'}">
			<fmt:message key="main.work_area.Good_bye_Message2" /> ${userLineCount}
		</c:if>
	</div>
	<%
		if (session != null) {
			session.invalidate();
		}
	%>
	<%@ include file="../common/footer.jspf"%>