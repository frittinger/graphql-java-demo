package com.vi.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vi.graphqldemo.model.Author;
import com.vi.graphqldemo.model.Book;
import com.vi.graphqldemo.repository.AuthorRepository;
import com.vi.graphqldemo.repository.BookRepository;

public class Query implements GraphQLQueryResolver {

  private BookRepository bookRepository;
  private AuthorRepository authorRepository;

  public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
    this.authorRepository = authorRepository;
    this.bookRepository = bookRepository;
  }

  public Iterable<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public Iterable<Author> findAllAuthors() {
    return authorRepository.findAll();
  }

  public long countBooks() {
    return bookRepository.count();
  }

  public long countAuthors() {
    return authorRepository.count();
  }
}
