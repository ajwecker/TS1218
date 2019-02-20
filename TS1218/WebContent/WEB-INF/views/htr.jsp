<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../common/header.jspf"%>
<body class="${dir}">
  
	<%@include file="../common/navigation.jspf"%>
	<script type="text/javascript">
		window.page_name = 'htr';
		$("[data-link='htr']").addClass('active');
	</script>
	<div class="container scrollable mt-2">
		<jsp:include page="parts/${lang}/main-content/htr.jspf" />

	</div>
	<%@ include file="../common/footer.jspf"%>