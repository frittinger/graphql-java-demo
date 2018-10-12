package com.vi.graphqldemo;

import com.vi.graphqldemo.model.Author;
import com.vi.graphqldemo.model.Book;
import com.vi.graphqldemo.repository.AuthorRepository;
import com.vi.graphqldemo.repository.BookRepository;
import com.vi.graphqldemo.resolver.BookResolver;
import com.vi.graphqldemo.resolver.GraphQLErrorAdapter;
import com.vi.graphqldemo.resolver.Mutation;
import com.vi.graphqldemo.resolver.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GraphqlDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(GraphqlDemoApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
    return (args) -> {
      Author author = new Author("Herbert", "Schildt");
      authorRepository.save(author);

      bookRepository
          .save(new Book("Java: A Beginner's Guide, Sixth Edition", "0071809252", 728, author));
      bookRepository
          .save(new Book("Mickz Mouse", "0071809254", 72, author));

      author = new Author("Frank", "Rittinger");
      authorRepository.save(author);
      bookRepository
          .save(new Book("My funny flyer", "0071809256", 7, author));
    };
  }

  @Bean
  public BookResolver authorResolver(AuthorRepository authorRepository) {
    return new BookResolver(authorRepository);
  }

  @Bean
  public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
    return new Query(authorRepository, bookRepository);
  }

  @Bean
  public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
    return new Mutation(authorRepository, bookRepository);
  }

  @Bean
  public GraphQLErrorHandler errorHandler() {
    return new GraphQLErrorHandler() {
      @Override
      public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        List<GraphQLError> clientErrors = errors.stream()
            .filter(this::isClientError)
            .collect(Collectors.toList());

        List<GraphQLError> serverErrors = errors.stream()
            .filter(e -> !isClientError(e))
            .map(GraphQLErrorAdapter::new)
            .collect(Collectors.toList());

        List<GraphQLError> e = new ArrayList<>();
        e.addAll(clientErrors);
        e.addAll(serverErrors);
        return e;
      }

      protected boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
      }
    };
  }
}
