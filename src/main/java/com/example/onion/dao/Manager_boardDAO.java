package com.example.onion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.onion.dto.Manager_boardDTO;
import com.example.onion.entity.Manager_board;
import com.example.onion.repository.Manager_boardRepository;

@Repository
public class Manager_boardDAO {
	@Autowired
	Manager_boardRepository manager_boardRepository;

	// 목록
	public List<Manager_board> noticeventList(int startNum, int endNum) {
		return manager_boardRepository.findByStartnumAndEndnum(startNum, endNum);
	}

	public int getTotalA() {
		return (int) manager_boardRepository.count();
	}

	// 글쓰기
	public boolean noticeventWrite(Manager_boardDTO dto) {
		Manager_board manager_board = dto.toEntity();
		return manager_boardRepository.save(manager_board) != null;
	}

	// 전체 데이터 개수
	public int getCount() {
		return (int) manager_boardRepository.count();
	}

	// 조회수 증가시키기
	public Manager_board updateHit(int MBseq) {
		// 1. 기존 데이터 가져오기
		Manager_board manager_board = manager_boardRepository.findById(MBseq).orElse(null);

		if (manager_board != null) {
			// 2. 데이터 수정
			manager_board.setMBhit(manager_board.getMBhit() + 1);
			// 3. 저장
			return manager_boardRepository.save(manager_board);
		} else {
			return null;
		}

	}

	// 상세 보기
	public Manager_board noticeventView(int MBseq) {
		return manager_boardRepository.findById(MBseq).orElse(null);
	}

	// 수정하기
	public boolean updatNoticevent(Manager_boardDTO dto) {
		Manager_board manager_board = manager_boardRepository.findById(dto.getMBseq()).orElse(null);
		boolean result = false;
		if (manager_board != null) {
			manager_board.setMBsub(dto.getMBsub());
			manager_board.setMBcon(dto.getMBcon());
			manager_board.setMBimg(dto.getMBimg());

			Manager_board board_result = manager_boardRepository.save(manager_board);
			if (board_result != null)
				result = true;
		}
		return result;
	}

	// 삭제하기
	public boolean noticeventDelete(int MBseq) {
		boolean result = false;

		// 1. 삭제할 대상 가져오기
		Manager_board manager_board = manager_boardRepository.findById(MBseq).orElse(null);
		System.out.println("DAO : manager_board : " + manager_board);

		if (manager_board != null) {
			// 2. 삭제하기
			manager_boardRepository.deleteById(MBseq);

			System.out.println("DAO : 삭제 되었는지 ? manager_board : " + manager_board);

			if (!manager_boardRepository.existsById(MBseq)) {
				result = true;
			}
		}

		// 3. 삭제확인
		return result;
	}
}