// Stuff from the page
function makeActive() {
	var workElem = document.getElementById("work");
	if (workElem) {
	  workElem.className += " active";
	}	
}





// When the user clicks on <div>, open the popup
function myPopup() {
  var popup = document.getElementById("myPopup");
  popup.classList.toggle("show");
}
function myInsert(ligature) {
  elem = document.getElementById('trw')
  var caretPos = elem.selectionStart;
  var myString = elem.value;
  pre = myString.substring(0, caretPos);
  post = myString.substring(caretPos, myString.length);
  elem.value = pre + ligature + post;
  elem.focus();
  elem.selectionStart = caretPos + 1;
  elem.selectionEnd = caretPos + 1;

}
function myMark(endligature, startligature) {
  elem = document.getElementById('trw')
  var caretPosStart = elem.selectionStart;
  var caretPosEnd = elem.selectionEnd;
  var myString = elem.value;

  pre = myString.substring(0, caretPosStart);
  middle = startligature + myString.substring(caretPosStart, caretPosEnd) + endligature;
  post = myString.substring(caretPosEnd, myString.length);
  document.getElementById('trw').value = pre + middle + post;
  if (caretPosStart != caretPosEnd) {
    caretPosEnd += 2;
  } else {
    caretPosEnd += 1;
  }
  elem.focus();
  elem.selectionStart = caretPosEnd;
  elem.selectionEnd = caretPosEnd;
}
function myFill() {
  elem = document.getElementById('trw')

  var myString = elem.value;
  elem2 = document.getElementById('filler');
  ligature = elem2.value;
  elem.value = myString + ligature;
  elem.focus();
  elem.selectionStart = myString.length;
  elem.selectionEnd = myString.length;

}
function myResizeOrg() {

  var elemImg = document.getElementById("imgline");

  if (elemImg) {
    var rect = elemImg.getBoundingClientRect();
    var fontsize = (Math.trunc(rect.height * 0.45) - 1) + "px";
    //    		    var elemTRO = document.getElementById("tro");
    //    			elemTRO.style.height = fontsize;
    //    			elemTRO.style.fontSize = fontsize;
    var elemTRW = document.getElementById("trw");
    elemTRW.style.height = fontsize;
    elemTRW.style.fontSize = fontsize;
 // Future version
 //   var elemTest = document.getElementById("test");
 //   elemTest.innerHTML = fontsize;
    console.log("OrgFontsize= " + fontsize);
  }

}
function myResizeOrg2() {

  var elemImg = document.getElementById("map2");
  if (elemImg) {
    var rect = elemImg.getBoundingClientRect();
    var fontsize = (Math.trunc(rect.height * 0.45) - 1) + "px";
    var elemTRW = document.getElementById("trw");
    elemTRW.style.height = fontsize;
    elemTRW.style.fontSize = fontsize;
 // Future version
//    var elemTest = document.getElementById("test");
//    elemTest.innerHTML = fontsize;
    console.log("OrgFontsize= " + fontsize)
  }

}
function myResize(myAdd) {
  var elemTRW = document.getElementById("trw");
  var rect = elemTRW.getBoundingClientRect();
  var fontsize = (Math.trunc(rect.height) + myAdd) + "px";
  elemTRW.style.height = fontsize;
  elemTRW.style.fontSize = fontsize;
//  var elemTest = document.getElementById("test");
//  elemTest.innerHTML = fontsize;
  console.log("Fontsize= " + fontsize)
}

function myReset() {
  var elemTRW = document.getElementById("trw");
  elemTRW.value = "${transcribedline}"
  document.getElementById("NE").disabled = false;
  document.getElementById("NE").setAttribute("style", "background-color: 90CAF9");

  document.getElementById("Skip").disabled = false;
  document.getElementById("Skip").setAttribute("style", "background-color: 90CAF9");
  document.getElementById("AC").disabled = true;
  document.getElementById("AC").setAttribute("style", "background-color: #f0f0f0");
  document.getElementById("PC").disabled = true;
  document.getElementById("PC").setAttribute("style", "background-color: #f0f0f0");
}

function trwChanged() {
  document.getElementById("NE").disabled = true;
  document.getElementById("NE").setAttribute("style", "background-color: #f0f0f0");
  document.getElementById("Skip").disabled = true;
  document.getElementById("Skip").setAttribute("style", "background-color: #f0f0f0");
  document.getElementById("AC").disabled = false;
  document.getElementById("AC").setAttribute("style", "background-color: 90CAF9");
  document.getElementById("PC").disabled = false;
  document.getElementById("AC").setAttribute("style", "background-color: 90CAF9");
}
function resizeImg() {
  var elemImgPage = document.getElementById("imgPage");
  var rect = elemImgPage.getBoundingClientRect();
  var width = Math.trunc(rect.width * 1);
  var height = Math.trunc(width * 1);


  elemImgPage.style.height = height + "px";
  var elemImgMap = document.getElementById("map");
  elemImgMap.style.height = height + "px";
  elemImgMap.style.width = width + "px";

  console.log("imgPageHeight= " + height)
}

function onloadResize() {
  myResizeOrg();
  resizeImg();
}

function openInfo(evt, infoName) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the link that opened the tab
  document.getElementById(infoName).style.display = "block";
  evt.currentTarget.className += " active";
}


