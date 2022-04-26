package org.javaschool.service.service.impl;

import org.javaschool.data.model.employee.Post;
import org.javaschool.data.repository.PostsRepository;
import org.javaschool.service.service.PostService;
import org.javaschool.service.service.dto.PostDto;
import org.javaschool.service.service.dto.TypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public PostDto get(Long id) {
        Post post = null;
        Optional<Post> optionalPost = postsRepository.findById(id);

        if (optionalPost.isPresent()) {
            post = optionalPost.get();
        }

        return mapperPostDto(post);
    }

    @Transactional
    @Override
    public List<PostDto> getAll() {
        List<PostDto> postDtoList = new ArrayList<>();

        postsRepository.findAll().forEach(post -> {
            postDtoList.add(mapperPostDto(post));
        });

        return postDtoList;
    }

    @Override
    public boolean save(PostDto postDto) {
        if (!postsRepository.existsById(postDto.getId())) {
            postsRepository.save(mapperPost(postDto));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (postsRepository.existsById(id)) {
            postsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(PostDto postDto, Long id) {
        if (postsRepository.existsById(id)) {
            postDto.setId(id);
            postsRepository.save(mapperPost(postDto));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Post mapperPost(PostDto postDto) {
        Post post = null;

        if (postDto != null) {
            post = new Post();
            post.setId(postDto.getId());
            post.setEmployeePost(postDto.getPost());
        }

        return post;
    }

    @Override
    public PostDto mapperPostDto(Post post) {
        PostDto postDto = null;

        if (post != null) {
            postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setPost(post.getEmployeePost());
        }

        return postDto;
    }
}
