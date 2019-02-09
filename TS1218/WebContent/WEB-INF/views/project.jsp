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
		<jsp:include page="../views/parts/${lang}/main-content/project.jspf" />

	</div>
	<div>
		For questions, suggestions or any other remark, feel free to <a
			href="mailto:tikkoun.sofrim@gmail.com">contact us</a>
	</div>

	<!-- img src='<c:url value="/images/elijah.png" ></c:url>' /-->
	<%@ include file="../common/footer.jspf"%>