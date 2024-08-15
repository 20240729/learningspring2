package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto){
        // RequestDto -> Entity (데이터는 데이터베이스와 소통하는 엔티티에 들어간다.)
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check (중복체크?)
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId); // 이걸로 번호를 넣음
        // 리스트의 크기가 0보다 크면 Long maxId가 키 중에서 제일 큰 것(모든 키의 집합 반환 메서드를
        // 입력된 배열 중에서 가장 큰 요소를 반환하는 메서드에 넣음)에 +1한 값이 됨.
        // 리스트의 크기가 0이면 1이 됨
        // 복습 : 삼항연산자 =>    조건 ? 참이면 실행할것 : 거짓이면 실행할것

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos(){
        // Map to List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }








}
