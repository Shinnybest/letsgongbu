package com.example.letsgongbu.service.Impl;

import com.example.letsgongbu.domain.Member;
import com.example.letsgongbu.domain.Post;
import com.example.letsgongbu.dto.request.PostForm;
import com.example.letsgongbu.dto.request.PostUpdateForm;
import com.example.letsgongbu.dto.response.PostAndCommentResponseDto;
import com.example.letsgongbu.dto.response.PostChange;
import com.example.letsgongbu.dto.response.PostResponseDto;
import com.example.letsgongbu.elasticsearch.repository.CustomPostSearchRepository;
import com.example.letsgongbu.exception.CustomException;
import com.example.letsgongbu.exception.Error;
import com.example.letsgongbu.repository.BoardRepository;
import com.example.letsgongbu.repository.MemberRepository;
import com.example.letsgongbu.security.UserDetailsImpl;
import com.example.letsgongbu.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.security.core.userdetails.UserDetails;
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
    public Long uploadPost(PostForm postForm, UserDetails userDetails) {
        Member member = existsMember(userDetails);
        Post post = new Post(postForm.getTitle(), postForm.getContent(), postForm.getMainCategory(), postForm.getSubCategory());
//        elasticsearchRestTemplate.save(post, IndexCoordinates.of("post"));
        post.setMember(member);
        Post savedPost = boardRepository.save(post);
        return savedPost.getId();
    }

    @Override
    public PostAndCommentResponseDto findPostAndComments(Long postId, UserDetails userDetails) {
        existsMember(userDetails);
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
    public void updatePost(Long postId, PostForm postForm, UserDetailsImpl userDetails) {
        Post post = matchPostAndMember(postId, userDetails);
//        Post post = existsPost(postId);
        post.updatePost(postForm);
    }

    private Post matchPostAndMember(Long postId, UserDetailsImpl userDetails) {
        return boardRepository.findByIdAndMember(postId, userDetails.getMember())
                .orElseThrow(() -> new CustomException(Error.NO_AUTHORITY_POST));
    }

    @Transactional
    @Override
    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        existsPost(postId);
        Post post = matchPostAndMember(postId, userDetails);
        boardRepository.delete(post);
    }

    @Override
    public PostUpdateForm getUpdatePost(Long postId, UserDetails userDetails) {
        existsMember(userDetails);
        Post post = existsPost(postId);
        return new PostUpdateForm(postId, post.getTitle(), post.getContent(), post.getMainCategory(), post.getSubCategory());
    }

    private Member existsMember(UserDetails userDetails) {
        return memberRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new CustomException(Error.MEMBER_NOT_EXIST));
    }

    private Post existsPost(Long postId) {
        return boardRepository.findById(postId).orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
    }

    @Override
    public List<PostResponseDto> findAllPostsByMe(UserDetails userDetails) {
        Member member = existsMember(userDetails);
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
}
