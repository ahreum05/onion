function checkcommunity() {
	var frm = document.form1;

	if (!frm.csub.value) {
		alert("글 제목을 입력하세요.");
		frm.csub.focus();
		return false;
	}

	if (!frm.ccon.value) {
		alert("글 내용을 입력하세요.");
		frm.ccon.focus();
		return false;
	}


	frm.submit();
}