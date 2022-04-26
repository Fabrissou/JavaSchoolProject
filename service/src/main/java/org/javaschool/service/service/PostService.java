package org.javaschool.service.service;

import org.javaschool.data.model.employee.Post;
import org.javaschool.service.service.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto get(Long id);

    List<PostDto> getAll();

    boolean save(PostDto postDto);

    boolean delete(Long id);

    boolean update(PostDto postDto, Long id);

    Post mapperPost(PostDto postDto);

    PostDto mapperPostDto(Post post);
}
