<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@include file="../common/header.jspf"%>
<body>
	<%@include file="../common/navigation.jspf"%>
	<script type="text/javascript">
		window.page_name = 'team';
		$("[data-link='team']").addClass('active');
	</script>
	<div class="team-page container scrollable">
		<jsp:include page="../views/parts/${lang}/main-content/htr.jspf" />

	</div>

	<%@ include file="../common/footer.jspf"%>