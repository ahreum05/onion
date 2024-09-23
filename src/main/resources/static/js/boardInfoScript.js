function checkInput() {
	var frm = document.form1;

	if (!frm.subject.value.trim()) {
		alert("제목을 입력하세요");
		frm.subject.focus();
	} else if (!frm.content.value.trim()) {
		alert("내용을 입력하세요");
		frm.content.focus();
	} else {
		frm.submit();
	}
}

function likePost1() {
	if (confirm('좋아요를 클릭하시겠습니까?')) {		
		location.href = '/boardinfo/boardLike?seq=$[[{dto.Bseq}]]&pg=${pg}';
	}
}
// boardInfoScipt.js

document.addEventListener('DOMContentLoaded', function() {
	const imageUploadInput = document.getElementById('imageUpload');
	const imagePreviewsContainer = document.getElementById('imagePreviews');

	// Function to handle image file selection and display
	imageUploadInput.addEventListener('change', function(event) {
		const files = event.target.files;
		if (files.length > 0) {
			for (let i = 0; i < files.length; i++) {
				const file = files[i];
				if (file && file.type.startsWith('image/')) {
					const reader = new FileReader();
					reader.onload = function(e) {
						// Create a new image element
						const img = document.createElement('img');
						img.src = e.target.result;

						// Create a container div for the image
						const div = document.createElement('div');
						div.className = 'image-preview';
						div.appendChild(img);

						// Append the new image container to the previews container
						imagePreviewsContainer.appendChild(div);
					};
					reader.readAsDataURL(file);
				}
			}
		}
	});
});

