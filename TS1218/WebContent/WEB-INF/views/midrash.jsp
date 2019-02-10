<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@include file="../common/header.jspf"%>
<body class="${dir}">
	<%@include file="../common/navigation.jspf"%>
	<script type="text/javascript">
		window.page_name = 'midrash';
		$("[data-link='midrash']").addClass('active');
	</script>
	<div class="container scrollable mt-2">
		<jsp:include page="../views/parts/${lang}/main-content/midrash.jspf" />
	</div>

	<%@ include file="../common/footer.jspf"%>