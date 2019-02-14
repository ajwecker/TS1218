<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../common/header.jspf"%>
<body class="${dir}">
	<%@include file="../common/navigation.jspf"%>
	<div class="container mt-2 login-page show-login">
	
		<div class="login-form-wrapper row">
			<%@include file="parts/login.jspf"%>
		</div>
		<div class="register-form-wrapper">
			<%@include file="parts/register.jspf"%>
		</div>

	</div>
	<%@ include file="../common/footer.jspf"%>