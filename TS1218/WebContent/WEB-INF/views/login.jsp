<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../common/header.jspf"%>
<body class="${dir}">
	<%@include file="../common/navigation.jspf"%>

	<div class="login-page show-login">

		<div>
			<div class="jumbotron jumbotron-fluid tsbackground">
				<div class="container"
					style="background-color: rgba(255, 255, 255, 0.8);">
					<h1 class="display-4">
						<fmt:message key="login.center.welcome" />
					</h1>
					<p class="lead">
						<fmt:message key="login.center.Intro_line_1" />
					<div>
						<fmt:message key="login.center.Intro_line_2" />
					</div>
									</div>
			</div>
			<%@include file="parts/login.jspf"%>
		</div>
		<div class="register-form-wrapper">
			<%@include file="parts/register.jspf"%>
		</div>

	</div>
	<%@ include file="../common/footer.jspf"%>