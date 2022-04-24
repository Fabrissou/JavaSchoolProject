package org.javaschool.data.repository;

import org.javaschool.data.model.employee.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostsRepository extends CrudRepository<Post, Long> {

}
