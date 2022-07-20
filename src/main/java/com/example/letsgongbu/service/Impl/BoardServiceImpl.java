package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.dto.response.PostTestResp;
import com.example.letsgongbu.elasticsearch.repository.CustomPostSearchRepository;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.BoardRepository;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final CustomPostSearchRepository searchRepository;

    @Override
    public List<Post> findAllPosts() {
        return boardRepository.findAll();
    }

    @Override
    public List<PostTestResp> findSearchPosts(String word, Pageable p) {
        return searchRepository.searchByWord(word, p);
    }

    @Override
    @Transactional
    public void uploadPost(PostForm postForm, UserDetails userDetails) {
        Post post = new Post(postForm.getTitle(), postForm.getContent(), postForm.getMainCategory(), postForm.getSubCategory());
        elasticsearchRestTemplate.save(post, IndexCoordinates.of("post"));
        Member member = memberRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new CustomException(Error.MEMBER_NOT_EXIST));
        post.setMember(member);
        boardRepository.save(post);
    }

    @Override
    public Post findPost(String postsTitle) {
        return boardRepository
                .findByTitle(postsTitle)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
    }

    @Transactional
    @Override
    public void updatePost(String postsTitle, PostForm postForm, HttpServletRequest request) {
        Post post = boardRepository.findByTitle(postsTitle).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        post.updatePost(postForm);
    }

    @Transactional
    @Override
    public void deletePost(String postsTitle, HttpServletRequest request) {
        Post post = boardRepository.findByTitle(postsTitle).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        boardRepository.delete(post);
    }

    @Override
    public PostForm getUpdatePost(String postsTitle) {
        Post post = boardRepository.findByTitle(postsTitle).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
        return new PostForm(post.getTitle(), post.getContent(), post.getMainCategory(), post.getSubCategory());
    }

    @Override
    public List<PostResponseDto.PostList> findAllPostsByMe(String username) {
        List<Post> posts = boardRepository.findAllByMember_Username(username);
        return posts.stream()
                .map(post -> new PostResponseDto.PostList(post.getTitle(), post.getMember().getUsername()))
                .collect(Collectors.toList());
    }
}
