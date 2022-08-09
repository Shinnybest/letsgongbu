package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.PostUpdateForm;
import com.example.letsgongbu.dto.response.PostAndCommentResponseDto;
import com.example.letsgongbu.dto.response.PostResponseDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<PostResponseDto> findAllPosts() {
        return boardRepository.findAll().stream()
                .map(post -> new PostResponseDto(post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getMember().getUsername(),
                        post.getCreatedAt(),
                        post.getMainCategory(),
                        post.getSubCategory(),
                        post.getComments().size())).collect(Collectors.toList());
    }

    @Override
    public List<PostResponseDto> findSearchPosts(String word, Pageable p) {
        return searchRepository.searchByWord(word, p);
    }

    @Override
    @Transactional
    public Long uploadPost(PostForm postForm, String username) {
        Member member = existsMember(username);
        Post post = new Post(postForm.getTitle(), postForm.getContent(), postForm.getMainCategory(), postForm.getSubCategory());
        elasticsearchRestTemplate.save(post, IndexCoordinates.of("post"));
        post.setMember(member);
        Post savedPost = boardRepository.save(post);
        return savedPost.getId();
    }

    @Override
    public PostAndCommentResponseDto findPostAndComments(Long postId, String username) {
        existsMember(username);
        Post post = existsPost(postId);
        return new PostAndCommentResponseDto(postId, post.getTitle(),
                post.getContent(),
                post.getMember().getUsername(),
                post.getCreatedAt(),
                post.getMainCategory(),
                post.getSubCategory(),
                post.getComments().size(), post.getComments());
    }

    @Transactional
    @Override
    public void updatePost(Long postId, PostForm postForm, String username) {
        Post post = matchPostAndMember(postId, username);
        post.updatePost(postForm);
    }

    @Transactional
    @Override
    public void deletePost(Long postId, String username) {
        existsPost(postId);
        Post post = matchPostAndMember(postId, username);
        boardRepository.delete(post);
    }

    @Override
    public PostUpdateForm getUpdatePost(Long postId, String username) {
        existsMember(username);
        Post post = existsPost(postId);
        return new PostUpdateForm(postId, post.getTitle(), post.getContent(), post.getMainCategory(), post.getSubCategory());
    }

    @Override
    public List<PostResponseDto> findAllPostsByMe(String username) {
        Member member = existsMember(username);
        List<Post> posts = boardRepository.findAllByMember_Username(member.getUsername());
        return posts.stream()
                .map(post -> new PostResponseDto(post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getMember().getUsername(),
                        post.getCreatedAt(),
                        post.getMainCategory(),
                        post.getSubCategory(),
                        post.getComments().size())).collect(Collectors.toList());
    }

    private Member existsMember(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new CustomException(Error.MEMBER_NOT_EXIST));
    }

    private Post existsPost(Long postId) {
        return boardRepository.findById(postId).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
    }

    private Post matchPostAndMember(Long postId, String username) {
        return boardRepository.findByIdAndMember_Username(postId, username)
                .orElseThrow(() -> new CustomException(Error.NO_AUTHORITY_POST));
    }
}
