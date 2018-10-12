package com.vi.graphqldemo.repository;

import com.vi.graphqldemo.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}

