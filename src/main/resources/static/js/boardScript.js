function inputCheck() {
	var frm = document.form1;

	if (!frm.subject.value.trim()) {
		alert("제목을 입력하세요.");
		frm.subject.value = "";
		frm.subject.focus();
		return false;
	}

	if (!frm.content.value.trim()) {
		alert("내용을 입력하세요.");
		frm.content.value = "";
		frm.content.focus();
		return false;
	}

	frm.submit();
}

function checkBoardModify() {
	var frm = document.form1;

	if (!frm.subject.value.trim()) {
		alert("제목을 입력하세요.");
		frm.subject.value = "";
		frm.subject.focus();
		return false;
	}
	if (!frm.content.value.trim()) {
		alert("내용을 입력하세요.");
		frm.content.value = "";
		frm.content.focus();
		return false;
	}
	
	frm.submit();
}

function reset1() {	
	var frm = document.form1;
	frm.subject.value = "";
	frm.content.value = "";
}


