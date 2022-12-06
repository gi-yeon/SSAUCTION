package com.ssafy.ssauction.service.posts;

import com.ssafy.ssauction.domain.posts.Posts;
import com.ssafy.ssauction.domain.posts.PostsRepository;
import com.ssafy.ssauction.web.dto.posts.PostsResponseDto;
import com.ssafy.ssauction.web.dto.posts.PostsSaveRequestDto;
import com.ssafy.ssauction.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 글이 없습니다."));
        posts.update(requestDto.getTitle(),requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당하는 게시글이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }
}
