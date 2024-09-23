document.getElementById('searchForm').addEventListener(
		'submit',
		function(event) {
			event.preventDefault();

			const location = document.getElementById('location').value
					.toLowerCase();
			const minSalary = document.getElementById('minSalary').value;

			const salaryTypes = [];
			document.querySelectorAll('input[name="salaryType"]:checked')
					.forEach(function(checkbox) {
						salaryTypes.push(checkbox.value);
					});

			const workingDays = [];
			document.querySelectorAll('input[name="workingDay"]:checked')
					.forEach(function(checkbox) {
						workingDays.push(checkbox.value);
					});

			const jobItems = document.querySelectorAll('#jobList tr');

			// 모든 리스트를 먼저 보여줌
			jobItems.forEach(function(job) {
				job.style.display = ''; // 기본적으로 모두 표시
			});

			// 검색 조건에 따라 필터링
			jobItems.forEach(function(job) {
				const jobLocation = job.cells[0].innerText.toLowerCase();
				const jobSalary = parseInt(job.cells[3].innerText.replace(
						/[^0-9]/g, ''));
				const jobSalaryText = job.cells[3].innerText;

				// 급여 종류 필터링: 선택된 급여 종류 중 하나라도 포함되면 true
				let matchesSalaryType = false;
				if (salaryTypes.length > 0) {
					salaryTypes.forEach(function(type) {
						if (jobSalaryText.includes(type)) {
							matchesSalaryType = true;
						}
					});
				} else {
					matchesSalaryType = true; // 급여 종류 선택 안 되어 있을 때는 필터링 안 함
				}

				const jobWorkingTime = job.cells[2].innerText;

				// 근무 요일 필터링
				let matchesWorkingDay = false;
				if (workingDays.length > 0) {
					workingDays.forEach(function(day) {
						if (jobWorkingTime.includes(day)) {
							matchesWorkingDay = true;
						}
					});
				} else {
					matchesWorkingDay = true; // 체크박스 선택 안 되어 있을 때는 필터링 안 함
				}

				if ((location && !jobLocation.includes(location))
						|| (minSalary && jobSalary < minSalary)
						|| !matchesSalaryType || !matchesWorkingDay) {
					job.style.display = 'none'; // 조건에 맞지 않으면 숨김
				}
			});
		});