<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../common/header.jspf"%>
<body class="${dir}">

	<%@include file="../common/navigation.jspf"%>
	<script type="text/javascript">
		window.page_name = 'conversation';
		$("[data-link='conversation']").addClass('active');
	</script>
	<div class="container scrollable mt-2">
		<div id="disqus_thread"></div>
		<script>
			/**
			 *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
			 *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables*/
			/*
			 var disqus_config = function () {
			 this.page.url = PAGE_URL;  // Replace PAGE_URL with your page's canonical URL variable
			 this.page.identifier = PAGE_IDENTIFIER; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
			 };
			 */
			(function() { // DON'T EDIT BELOW THIS LINE
				var d = document, s = d.createElement('script');
				s.src = 'https://tikkoun-sofrim.disqus.com/embed.js';
				s.setAttribute('data-timestamp', +new Date());
				(d.head || d.body).appendChild(s);
			})();
		</script>
		<noscript>
			Please enable JavaScript to view the <a
				href="https://disqus.com/?ref_noscript">comments powered by
				Disqus.</a>
		</noscript>

	</div>
	<%@ include file="../common/footer.jspf"%>