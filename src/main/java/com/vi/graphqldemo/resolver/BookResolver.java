package com.vi.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.vi.graphqldemo.model.Author;
import com.vi.graphqldemo.model.Book;
import com.vi.graphqldemo.repository.AuthorRepository;

public class BookResolver implements GraphQLResolver<Book> {

  private AuthorRepository authorRepository;

  public BookResolver(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public Author getAuthor(Book book) {
    return authorRepository.findById(book.getAuthor().getId()).get();
  }
}
