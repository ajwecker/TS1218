<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@include file="../common/header.jspf"%>
<body class="${dir}">

	<%@include file="../common/navigation.jspf"%>
	<script type="text/javascript">
		window.page_name = 'project';
		$("[data-link='project']").addClass('active');
	</script>

	<div class="container scrollable mt-2">
		<jsp:include page="parts/${lang}/main-content/project.jspf" />

	</div>

	<%@ include file="../common/footer.jspf"%>