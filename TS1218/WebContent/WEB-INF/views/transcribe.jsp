<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:directive.page contentType="text/html;charset=UTF-8" />

<%@include file="../common/header.jspf"%>

<body onload="onloadResize();" class="${dir}">
	<%@include file="../common/navigation.jspf"%>

	<div class="transcribe-page container-fluid mt-2">
		<div class="row h-100">
			<!-- Column Two work area -->
			<div id="transcribe" class="container col-md-8">
				<div class="flex-fill d-flex flex-column justify-content-around">
					<div class="w-100 p-3">
						<div id="work-page"
							class="d-flex flex-column justify-content-between">
							<div class="header mb-4 row">
								<div class="col-8">

									<fmt:message key="main.work_area.intro_line_1" />
									<fmt:message key="main.work_area.intro_line_2" />
								</div>

								<div class="video_tut col-4"
									>

									<a href='<fmt:message key="main.work_area.video"/>'
										target="_blank"
										title='<fmt:message key="main.work_area.video_hover"/>'> <img
										src="../images/video_thumb.png"/>
										<fmt:message key="main.work_area.video_hover"/>
									</a>
								</div>
							</div>
						</div>
						<div>
							<img id="imgline"
								alt="Page ${manuscriptPage} of ${manuscriptTotalPages}, line ${manuscriptLine} of ${manuscriptTotalLines}"
								width="100%" src="${transcribedlineimgsrc}" class="imageline">
							<%-- <img id="imgline"
								alt="Page ${manuscriptPage} of ${manuscriptTotalPages}, line ${manuscriptLine} of ${manuscriptTotalLines}"
								width="100%" src="${transcribedlineimgsrc}"
								data-high-res-src="${transcribedlineimgsrc}" class="imageline">
							<script type="text/javascript">
								//Example 3
								$(function() {
									$('.imageline').ImageViewer({
										snapView : false
									});
								});
							</script> --%>
							<form
								action="${pageContext.request.contextPath}/TranscribeServlet"
								method="post">
								<div class="mt-2">
									<input id="trw" type="text" name="transcribed"
										autocomplete="off" value="${transcribedline}"
										style="font-family: Corsiva" class="w-100 p-2 rtl"> <input
										type="hidden" id="trwOrig" value="${transcribedline}" />
								</div>
								<!-- Transcribe toolbar -->
								<div
									class="btn-toolbar justify-content-between d-flex mt-3 p-10"
									role="toolbar" dir="ltr">
									<div class="btn-group" role="group" aria-label="First group"
										dir="rtl">
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myMark(']','[')"
											title='<fmt:message key="main.work_area.hovers.over_additions"/>'>
											<span style="font-size: smaller;"><fmt:message
													key="main.work_area.button_2" /></span>
										</button>
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myMark(')','(')"
											title='<fmt:message key="main.work_area.hovers.over_deletions"/>'>
											<span style="font-size: smaller;"><fmt:message
													key="main.work_area.button_1" /></span>
										</button>
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myMark('>','<')"
											title='<fmt:message key="main.work_area.hovers.over_damaged"/>'>
											<span style="font-size: smaller;"><fmt:message
													key="main.work_area.button_3" /></span>
										</button>
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myMark('}','{')"
											title='<fmt:message key="main.work_area.hovers.over_uncertain"/>'>
											<span style="font-size: smaller;"><fmt:message
													key="main.work_area.button_4" /></span>
										</button>



										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myInsert('˙')"
											title='<fmt:message key="main.work_area.hovers.over_upper"/>'>
											<b>˙</b>
										</button>
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myInsert('ﭏ')"
											title='<fmt:message key="main.work_area.hovers.over_ligature"/>'>
											<b>ﭏ</b>
										</button>
										<select id="filler" class="custom-select-sm btn-secondary"
											title='<fmt:message key="main.work_area.hovers.over_line_fillers"/>'
											onchange="myFill()">
											<option value="">&nbsp;</option>
											<option value="/">/</option>
											<option value="//">//</option>
											<option value="|">|</option>
											<option value="V">V</option>
											<option value="'">'</option>
										</select>
									</div>
									<!-- right part -->
									<div class="btn-group" role="group" aria-label="Second group"
										dir="rtl">
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myReset()"
											title='<fmt:message key="main.work_area.hovers.over_reset"/>'>
											<span style="font-size: smaller;"><fmt:message
													key="main.work_area.button_5" /></span>
										</button>
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myResize(1)"
											title='<fmt:message key="main.work_area.hovers.over_alef_plus"/>'>
											<span style="font-size: larger;"><b>א+</b></span>
										</button>
										<button class="rounded-0 btn btn-secondary" type="button"
											onclick="myResize(-1)"
											title='<fmt:message key="main.work_area.hovers.over_alef_minus"/>'>
											<span style="font-size: smaller;"><b>א-</b></span>
										</button>
									</div>
								</div>

								<div id="activity" class="mt-2 align-self-center w-60">

									<div>
										<fmt:message key="main.work_area.finish_line_1" />
									</div>
									<div>
										<fmt:message key="main.work_area.finish_line_2" />
									</div>

									<div id="activity-buttons"
										class="mt-2 d-flex justify-content-between" dir="rtl">


										<button type="submit" class="btn btn-warning" name="status"
											value="Skip">
											<fmt:message key="main.work_area.finish_button_2" />
										</button>
										<button type="submit" class="btn btn-success" name="status"
											value="Done">
											<fmt:message key="main.work_area.finish_button_1" />
										</button>

									</div>
								</div>
							</form>
						</div>
					</div>
				</div>

			</div>
			<div class="info-page container col-md-4">
				<div>
					<ul class="nav nav-tabs flex-nowrap" role="tablist">
						<li class="nav-item"><a class="nav-link active sfont"
							id="page-tab" data-toggle="tab" role="tab" href="#page"
							aria-controls="page" aria-selected="true"><fmt:message
									key="main.data_area.page" /></a></li>

						<li class="nav-item"><a class="nav-link sfont"
							id="special-tab" data-toggle="tab" role="tab" href="#special"
							aria-controls="special"><fmt:message
									key="main.data_area.Issues" /></a></li>

						<li class="nav-item"><a class="nav-link sfont" id="ab-tab"
							data-toggle="tab" role="tab" href="#ab" aria-controls="ab"><fmt:message
									key="main.data_area.Alphabet" /></a></li>

						<li class="nav-item sfont"><a class="nav-link"
							id="marked-tab" data-toggle="tab" role="tab" href="#marked"
							aria-controls="marked"><fmt:message
									key="main.data_area.editing" /></a></li>

						<li class="nav-item"><a class="nav-link sfont" id="help-tab"
							data-toggle="tab" role="tab" href="#help" aria-controls="help"><strong><fmt:message
										key="main.data_area.help" /></strong></a></li>

					</ul>
				</div>
				<div class="tab-content">
					<div
						class="tab-pane fade show active flex-fill d-flex justify-content-between flex-column"
						id="page" role="tabpanel" aria-labelledby="page-tab">
						<div id="imgPage">
							<a href="${manuscriptDescLink}" target="_blank"> <label
								style="clear: both; color: blue;" title="${manuscriptShortDesc}">${manuscriptName}</label>
							</a> <label> - Page ${manuscriptPage} /
								${manuscriptTotalPages}, Line ${manuscriptLine} /
								${manuscriptTotalLines} </label>
						</div>

						<div id="map"></div>

					</div>
					<div class="tab-pane fade" id="special" role="tabpanel"
						aria-labelledby="profile-tab">
						<div class="tabcontent">
							<jsp:include
								page="parts/${lang}/transcribe/special.jspf" />
						</div>
					</div>
					<div class="tab-pane fade" id="ab" role="tabpanel"
						aria-labelledby="ab-tab">
						<div class="tabcontent">
							<jsp:include page="parts/${lang}/transcribe/abc.jspf" />
						</div>
					</div>
					<div class="tab-pane fade" id="marked" role="tabpanel"
						aria-labelledby="marked-tab">
						<div class="tabcontent">
							<jsp:include page="parts/${lang}/transcribe/marked.jspf" />
						</div>
					</div>
					<div class="tab-pane fade" id="help" role="tabpanel"
						aria-labelledby="help-tab">
						<div class="tabcontent">
							<jsp:include page="parts/${lang}/transcribe/help.jspf" />
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var map = L.map('map', {
			center : [ 0, 0 ],
			minZoom : 0,
			zoom : 0,
			zoomSnap : 0,
			zoomDelta : 0.25,
			crs : L.CRS.Simple,
			dragging: !L.Browser.mobile
		});

		var pageTileLayer = L.tileLayer.iiif('${pageimgsrc}');
		var imageLayer = pageTileLayer.addTo(map);

		var linePolygonLayer = L.polygon([ [ "${ytop}", "${xleft}" ],
				[ "${ytop}", "${xright}" ], [ "${ybottom}", "${xright}" ],
				[ "${ybottom}", "${xleft}" ] ], {
			color : 'blue',
			fillColor : '#f03',
			fillOpacity : 0.1,
			weight : 1
		});

		linePolygonLayer.addTo(map);

		map.attributionControl.setPrefix("");
		map.attributionControl.addAttribution("${manuscriptAttribution}");

		/* setTimeout(function() {
			map.fitBounds(map.getBounds(imageLayer))
		}, 1500) */
	</script>
	<%@include file="../common/footer2.jspf"%>