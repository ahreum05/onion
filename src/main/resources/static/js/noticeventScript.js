function checkWrite() {
	var frm = document.form1;

	if (!frm.MBsub.value) {
		alert("제목을 입력하세요.");
		frm.MBsub.focus();
		return false;
	}
	if (!frm.MBcon.value) {
		alert("내용을 입력하세요.");
		frm.MBcon.focus();
		return false;
	}

	if (!frm.img.value) {
		alert("파일을 선택하세요.");
		frm.img.focus();
		return false;
	}

	frm.submit();
}