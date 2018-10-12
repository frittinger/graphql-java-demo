package com.vi.graphqldemo.repository;

import com.vi.graphqldemo.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}