function inputCheck() {
   //alert("test");
   var frm = document.form1;

   if (!frm.name.value.trim()) {
      alert("이름을 입력하세요");
      frm.name.focus();
      return false;
   }
   if (!frm.id.value) {
      alert("아이디를 입력하세요");
      frm.id.focus();
      return false;
   }
   if (!frm.pwd.value) {
      alert("비밀번호를 입력하세요");
      frm.pwd.focus();
      return false;
   }
   if (!frm.repwd.value) {
      alert("재확인을 입력하세요");
      frm.repwd.focus();
      return false;
   }
   if (frm.pwd.value != frm.repwd.value) {
      alert("비밀번호가 틀림니다.");
      frm.repwd.value = "";
      frm.repwd.focus();
      return false;
   }
   if (!frm.gender.value) {
      alert("성별을 입력하세요");
      frm.gender.focus();
      return false;
   }
   if (!frm.email1.value) {
      alert("이메일을 입력하세요");
      frm.email1.focus();
      return false;
   }
   if (!frm.email2.value) {
      alert("이메일을 입력하세요");
      frm.email2.focus();
      return false;
   }
   if (!frm.tel1.value) {
      alert("전화번호를 입력하세요");
      frm.tel1.focus();
      return false;
   }
   if (!frm.tel2.value) {
      alert("전화번호를 입력하세요");
      frm.tel2.focus();
      return false;
   }
   if (!frm.tel3.value) {
      alert("전화번호를 입력하세요");
      frm.tel3.focus();
      return false;
   }
   if (!frm.addr.value) {
      alert("주소를 입력하세요");
      frm.addr.focus();
      return false;
   }

   frm.submit();
}

function login() {
   var frm = document.form1;

   if (!frm.id.value) {
      alert("아이디를 입력해주세요");
      frm.name.value = "";
      frm.name.foucs();
      return false;
   }
   if (!frm.pwd.value) {
      alert("비밀번호를 입력해주세요");
      frm.pwd.value = "";
      frm.pwd.foucs();
      return false;
   }
   frm.submit();
}

function checkId() {
    var sId = document.form1.userid.value;  // userid로 수정
    if (sId == "") {
        alert("먼저 아이디를 입력하세요.");
        document.form1.userid.focus();
    } else {
        window.open("/member/checkId?userid=" + sId, "checkIdWindow", "width=350,height=200");  // userid로 변경
    }
}

function checkModify() {
   var frm = document.form1;

   if (!frm.pwd.value.trim()) {
      alert("비밀번호를 입력해주세요.");
      frm.pwd.value = "";
      frm.pwd.focus();
      return false;
   }
   if (!frm.repwd.value.trim()) {
      alert("재확인 비밀번호를 입력해주세요.");
      frm.repwd.value = "";
      frm.repwd.focus();
      return false;
   }
   if (frm.pwd.value != frm.repwd.value) {
      alert("비밀번호가 틀렸습니다. 다시 입력해주세요.");
      frm.repwd.value = "";
      frm.repwd.focus();
      return false;
   }

   if (!frm.gender[0].checked && !frm.gender[1].checked) {
      alert("성별을 선택해 주세요.");
      return false;
   }

   if (!frm.email1.value.trim()) {
      alert("이메일을 입력해주세요.");
      frm.email1.value = "";
      frm.email1.focus();
      return false;
   }
   if (!frm.email2.value.trim()) {
      alert("이메일을 입력해주세요.");
      frm.email2.value = "";
      frm.email2.focus();
      return false;
   }
   if (!frm.tel1.value.trim()) {
      alert("전화번호를 입력해주세요.");
      frm.tel1.value = "";
      frm.tel1.focus();
      return false;
   }
   if (!frm.tel2.value.trim()) {
      alert("전화번호를 입력해주세요.");
      frm.tel2.value = "";
      frm.tel2.focus();
      return false;
   }
   if (!frm.tel3.value.trim()) {
      alert("전화번호를 입력해주세요.");
      frm.tel3.value = "";
      frm.tel3.focus();
      return false;
   }
   if (!frm.addr.value.trim()) {
      alert("주소를 입력해주세요.");
      frm.addr.value = "";
      frm.addr.focus();
      return false;
   }
   frm.submit();
}






