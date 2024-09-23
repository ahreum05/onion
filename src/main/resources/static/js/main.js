let slideIndex = 0; // 현재 슬라이드의 인덱스를 저장하는 변수
let slideInterval; // 자동 슬라이드 쇼를 위한 인터벌 변수

// 슬라이드를 화면에 보여주는 함수
function showSlides() {
    const slides = document.querySelector('.slides'); // 슬라이드를 감싸는 요소를 선택
    const slideCount = document.querySelectorAll('.slide').length; // 슬라이드의 개수를 확인
    slides.style.transform = `translateX(-${slideIndex * 100}%)`; // 현재 슬라이드 인덱스에 따라 슬라이드를 이동
}

// 슬라이드를 이동시키는 함수
function moveSlide(n) {
    const slides = document.querySelectorAll('.slide'); // 모든 슬라이드 요소를 선택
    slideIndex = (slideIndex + n + slides.length) % slides.length; // 슬라이드 인덱스를 업데이트
    showSlides(); // 업데이트된 인덱스에 따라 슬라이드를 보여줌
}

// 슬라이드 쇼를 자동으로 시작하는 함수
function startSlideShow() {
    slideInterval = setInterval(() => {
        moveSlide(1); // 2.5초마다 다음 슬라이드로 이동
    }, 2500); // 2500ms = 2.5초
}

// 슬라이드 쇼를 멈추는 함수
function stopSlideShow() {
    clearInterval(slideInterval); // 설정된 인터벌을 멈춤
}

// DOM 콘텐츠가 모두 로드되었을 때 실행되는 초기화 코드
document.addEventListener('DOMContentLoaded', () => {
    showSlides(); // 첫 번째 슬라이드를 화면에 표시
    startSlideShow(); // 슬라이드 쇼 자동 시작

    // 슬라이더에 마우스를 올렸을 때 자동 슬라이드를 멈추는 이벤트 리스너
    document.querySelector('.slider').addEventListener('mouseenter', stopSlideShow);

    // 슬라이더에서 마우스를 내렸을 때 자동 슬라이드를 재시작하는 이벤트 리스너
    document.querySelector('.slider').addEventListener('mouseleave', startSlideShow);
});


// 다크모드
document.addEventListener("DOMContentLoaded", function() {
    const darkModeToggle = document.createElement('button');
    darkModeToggle.textContent = '다크 모드';
    darkModeToggle.classList.add('dark-mode-toggle');
    document.body.appendChild(darkModeToggle);

    // 다크 모드 상태를 로컬 스토리지에서 가져옵니다.
    const darkMode = localStorage.getItem('darkMode') === 'true';
    if (darkMode) {
        document.body.classList.add('dark-mode');
    }

    darkModeToggle.addEventListener('click', function() {
        const isDarkMode = document.body.classList.toggle('dark-mode');
        localStorage.setItem('darkMode', isDarkMode);
        darkModeToggle.textContent = isDarkMode ? '라이트 모드' : '다크 모드';
    });
});