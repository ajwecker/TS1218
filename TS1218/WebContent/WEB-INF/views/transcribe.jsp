<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:directive.page contentType="text/html;charset=UTF-8" />

<%@include file="../common/header.jspf"%>

<body onload="onloadResize();" class="${dir}">
	<%@include file="../common/navigation.jspf"%>
	<div class="transcribe-page scrollable mt-2 p-2 row">
		

		<!-- Column Two work area -->
		<div id="transcribe" class="container col-8">
			<div class="flex-fill d-flex flex-column justify-content-around">
				<div class="w-100 p-3">
					<div id="work-page"
						class="d-flex flex-column justify-content-between">
						<div style="float: left" class="header mb-4">
							<div>

								<fmt:message key="main.work_area.intro_line_1" />

							</div>

							<div>
								<fmt:message key="main.work_area.intro_line_2" />
							</div>
						</div>
						<!--             <div id="img-line" style="background-image:url('http://tikkoun-sofrim.haifa.ac.il/cantaloupe/iiif/2/bge-cl0146_007.jpg/511,4684,4155,449/full/0/default.jpg');"> -->
						
					</div>
					<div>
					<img id="imgline"
							alt="Page ${manuscriptPage} of ${manuscriptTotalPages}, line ${manuscriptLine} of ${manuscriptTotalLines}"
							width="100%" src="${transcribedlineimgsrc}"
							data-high-res-src="${transcribedlineimgsrc}"
							class="imageline">
						<script type="text/javascript">
							//Example 3
							$(function() {
								$('.imageline').ImageViewer({
									snapView : false
								});
							});
						</script>
						<form
							action="${pageContext.request.contextPath}/TranscribeServlet"
							method="post">
							<div class="mt-2">
								<input id="trw" type="text" oninput="trwChanged()"
									name="transcribed" value="${transcribedline}"
									style="font-family: Corsiva" class="w-100 p-2 rtl"> <input
									type="hidden" id="trwOrig" value="${transcribedline}" />
							</div>
							<!-- Transcribe toolbar -->
							<div class="btn-toolbar justify-content-between d-flex mt-3 p-10 ltr"
								role="toolbar">
								<!-- 								<div class="mr-2" role="group"> -->
								<!-- 									<select id="filler" class="custom-select" -->
								<!-- 										title="End of line marks" onchange="myFill()"> -->
								<!-- 										<option value="">&nbsp;</option> -->
								<!-- 										<option value="/">/</option> -->
								<!-- 										<option value="//">//</option> -->
								<!-- 										<option value="|">|</option> -->
								<!-- 										<option value="V">V</option> -->
								<!-- 										<option value="'">'</option> -->
								<!-- 									</select> -->
								<!-- 								</div> -->
								<div class="btn-group mr-2 rtl" role="group"
									aria-label="First group">
									
									
									

									
									
									
									<button class="btn btn-secondary" type="button"
										onclick="myMark(']','[')"
										title='<fmt:message key="main.work_area.hovers.over_additions"/>'>
										<span style="font-size: smaller;"><fmt:message
												key="main.work_area.button_2" /></span>
									</button>
									<button class="btn btn-secondary" type="button"
										onclick="myMark(')','(')"
										title='<fmt:message key="main.work_area.hovers.over_deletions"/>'>
										<span style="font-size: smaller;"><fmt:message
												key="main.work_area.button_1" /></span>
									</button>
									<button class="btn btn-secondary" type="button"
										onclick="myMark('>','<')"
										title='<fmt:message key="main.work_area.hovers.over_damaged"/>'>
										<span style="font-size: smaller;"><fmt:message
												key="main.work_area.button_3" /></span>
									</button>
									<button class="btn btn-secondary" type="button"
										onclick="myMark('}','{')"
										title='<fmt:message key="main.work_area.hovers.over_uncertain"/>'>
										<span style="font-size: smaller;"><fmt:message
												key="main.work_area.button_4" /></span>
									</button>
									<button class="btn btn-secondary" type="button"
										onclick="myInsert('˙')"
										title='<fmt:message key="main.work_area.hovers.over_upper"/>'>
										<b>˙</b>
									</button>
									<button class="btn btn-secondary" type="button"
										onclick="myInsert('ﭏ')"
										title='<fmt:message key="main.work_area.hovers.over_ligature"/>'>
										<b>ﭏ</b>
									</button>
									<select id="filler" class="custom-select-sm btn-secondary"
										title="End of line marks" onchange="myFill()">
										<option value="">&nbsp;</option>
										<option value="/">/</option>
										<option value="//">//</option>
										<option value="|">|</option>
										<option value="V">V</option>
										<option value="'">'</option>
									</select>
								</div>

								<!-- right part -->
								<div class="btn-group mr-2 rtl" role="group"
									aria-label="Second group">
									<button class="btn btn-secondary" type="button"
										onclick="myReset()"
										title='<fmt:message key="main.work_area.hovers.over_reset"/>'>
										<span style="font-size: smaller;"><fmt:message key="main.work_area.button_5" /></span>
									</button>
									<button class="btn btn-secondary" type="button"
										onclick="myResize(1)" title="Increase size of text">
										<span style="font-size: larger;"><b>א+</b></span>
									</button>
									<button class="btn btn-secondary" type="button"
										onclick="myResize(-1)" title="Reduce size of text">
										<span style="font-size: smaller;"><b>א-</b></span>
									</button>
									<button class="btn btn-secondary" type="button"
										title="Font size" id="test" disabled="disabled"></button>
									<!-- 									<label id="test">Font size</label> -->
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
									class="mt-2 d-flex justify-content-between">
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
<div id="info-page" class="container col-4 flex-column d-flex">
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

					<li class="nav-item sfont"><a class="nav-link" id="marked-tab"
						data-toggle="tab" role="tab" href="#marked" aria-controls="marked"><fmt:message
								key="main.data_area.editing" /></a></li>

					<li class="nav-item"><a class="nav-link sfont" id="help-tab"
						data-toggle="tab" role="tab" href="#help" aria-controls="help">Help</a>
					</li>
				</ul>
			</div>
			<div class="tab-content">
				<div class="tab-pane fade show active" id="page" role="tabpanel"
					aria-labelledby="page-tab">
					<div id="imgPage">
						<a href="${manuscriptDescLink}" target="_blank"> <label
							style="clear: both; color: blue;" title="${manuscriptShortDesc}">${manuscriptName}</label>
						</a> <label> - Page ${manuscriptPage} /
							${manuscriptTotalPages}, Line ${manuscriptLine} /
							${manuscriptTotalLines} </label>


						<div id="map" class="flex">
							<script type="text/javascript">
								setTimeout(
										function() {
											var map = L.map('map', {
												center : [ 0, 0 ],
												crs : L.CRS.Simple,
												attributionControl : false,
												zoom : 2,
												zoomDelta : 0.25,
												zoomSnap : 0
											});
											L.tileLayer.iiif("${pageimgsrc}")
													.addTo(map);
											var polygon = L
													.polygon(
															[
																	[
																			"${ytop}",
																			"${xleft}" ],
																	[
																			"${ytop}",
																			"${xright}" ],
																	[
																			"${ybottom}",
																			"${xright}" ],
																	[
																			"${ybottom}",
																			"${xleft}" ] ],
															{
																color : 'blue',
																fillColor : '#f03',
																fillOpacity : 0.2
															}).addTo(map);
										}, 1500);
							</script>
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="special" role="tabpanel"
					aria-labelledby="profile-tab">
					<div class="tabcontent">
						<jsp:include page="../views/parts/${lang}/transcribe/special.jspf" />
					</div>
				</div>
				<div class="tab-pane fade" id="ab" role="tabpanel"
					aria-labelledby="ab-tab">
					<div class="tabcontent">
						<jsp:include page="../views/parts/${lang}/transcribe/abc.jspf" />
					</div>
				</div>
				<div class="tab-pane fade" id="marked" role="tabpanel"
					aria-labelledby="marked-tab">
					<div class="tabcontent">
						<jsp:include page="../views/parts/${lang}/transcribe/marked.jspf" />
					</div>
				</div>
				<div class="tab-pane fade" id="help" role="tabpanel"
					aria-labelledby="help-tab">
					<div class="tabcontent">
						<jsp:include page="../views/parts/${lang}/transcribe/help.jspf" />
					</div>
				</div>
			</div>
		</div>
	</div>


	<%@ include file="../common/footer2.jspf"%>