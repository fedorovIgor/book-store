package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.entity.GenreEntity;
import com.fedorovigor.bookstore.bookbucket.repository.AuthorRepository;
import com.fedorovigor.bookstore.bookbucket.repository.BookRepository;
import com.fedorovigor.bookstore.bookbucket.repository.GenreRepository;
import com.fedorovigor.bookstore.bookbucket.repository.TitleRepository;
import com.fedorovigor.bookstore.bookbucket.exception.ResourceNotFoundException;
import com.fedorovigor.bookstore.bookbucket.model.entity.AuthorEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.BookEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import com.fedorovigor.bookstore.bookbucket.model.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class BookBucketReadServiceImplement implements BookBucketReadService {
    private static final int PAGE_SIZE = 10;

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final TitleRepository titleRepository;
    private final GenreRepository genreRepository;

    private final BucketMapping mapping;

    @Override
    public PageableResponse getAllTitle(int pageNumber) {

        Page<Integer> titlePageIds = titleRepository.findTitleIds(
                PageRequest.of(pageNumber, this.PAGE_SIZE, Sort.by("name")));

        if (titlePageIds.getContent().isEmpty())
            throw new ResourceNotFoundException("No one book find");

        List<TitleEntity> titleEntity = titleRepository.findTitlesInIds(titlePageIds.getContent());

        var titles = titleEntity.stream()
                .map(this::titleEntityToTitleResponse)
                .collect(Collectors.toList());

        return getPageableResponse(titles, titlePageIds, pageNumber);
    }

    @Override
    public PageableResponse getTitlesByGenre(String genre, int pageNumber) {
        String genreUpper = genre.toUpperCase();
        
        if (!genreRepository.isExistsByName(genreUpper))
            throw new ResourceNotFoundException(String.format(
                    "Genre [%s] not exist",genre));

        Page<Integer> titlePageIds = titleRepository.findTitleIdsInGenre(
                genreUpper,
                    PageRequest.of(pageNumber, this.PAGE_SIZE, Sort.by("name")));

        if (titlePageIds.isEmpty())
            throw new ResourceNotFoundException(String.format(
                    "No one book find with genre [%s]",genre));

        List<TitleEntity> titleEntity = titleRepository.findTitlesInIds(titlePageIds.getContent());

        var titlesInGenre = titleEntity.stream()
                .map(this::titleEntityToTitleResponse)
                .collect(Collectors.toList());

        return getPageableResponse(titlesInGenre, titlePageIds, pageNumber);
    }

    @Override
    public PageableResponse getTitlesByAuthorId(int authorId, int pageNumber) {

        if (!authorRepository.existsById(authorId))
            throw new ResourceNotFoundException(String.format(
                    "Author with id [%i] not exist", authorId));

        Page<Integer> titlePageIds = titleRepository.findTitleIdsInAuthor(
                authorId,
                PageRequest.of(pageNumber, this.PAGE_SIZE, Sort.by("name")));

        if (titlePageIds.isEmpty())
            throw new ResourceNotFoundException(String.format(
                    "No one book find with authorId [%i]",authorId));

        List<TitleEntity> titleEntity = titleRepository.findTitlesInIds(titlePageIds.getContent());

        var titlesInAuthor = titleEntity.stream()
                .map(this::titleEntityToTitleResponse)
                .collect(Collectors.toList());

        return getPageableResponse(titlesInAuthor, titlePageIds, pageNumber);
    }


    @Override
    public Book getBookById(int id) {
        Optional<BookEntity> byId = bookRepository.findById(id);
        var bookEntity = byId.orElseThrow(() -> new ResourceNotFoundException(String.format(
                "Book with id [%i] not exist", id)));

        return bookEntityToBook(bookEntity);
    }

    @Override
    public List<Book> getAllBooksInTitle(int titleId) {

        if (!titleRepository.existsById(titleId))
            throw new ResourceNotFoundException(String.format(
                    "Author with id [%i] not exist", titleId));

        Optional<TitleEntity> titleWithBooksOptional = titleRepository.findTitleWithBooks(titleId);

        var titleWithBooks = titleWithBooksOptional.orElseThrow(() ->
            new ResourceNotFoundException(String.format(
                    "No one book find by title id [%i]",titleId)));

        var result = titleWithBooks.getBooksEntity().stream()
                .map(this::bookEntityToBook)
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        var iterableGenre = genreRepository.findAll();
        var result =  StreamSupport
                .stream(iterableGenre.spliterator(), false)
                .map(mapping::genreEntityToGenre)
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public AuthorResponse getAuthor(int id) {

        var entity = authorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No Authors with id " + id));

        AuthorResponse response = authorEntityToAuthorResponse(entity);

        return response;
    }

    @Override
    public PageableResponse getAllAuthors(int pageNumber) {

        Page<Integer> authorsIdsPage = authorRepository
                .findAuthorsIds(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("lastName")));

        if (authorsIdsPage.getContent().isEmpty())
            throw new ResourceNotFoundException("No one book find");

        System.out.println(authorsIdsPage);

        List<AuthorEntity> authorEntities = authorRepository
                .getAuthorsInIds(authorsIdsPage.getContent());

        var authors = authorEntities.stream()
                .map(this::authorEntityToAuthorResponse)
                .collect(Collectors.toList());

        return getPageableResponse(authors, authorsIdsPage, pageNumber);
    }

    private <T extends ResponsePageable> PageableResponse getPageableResponse(List<T> data,
                                                                              Page<Integer> idsPage,
                                                                              int currentPage){
        PageableResponse<T> response = new PageableResponse<>();
        response.setCurrentPage(currentPage);
        response.setTotalElements(idsPage.getTotalElements());
        response.setTotalPages(idsPage.getTotalPages());
        response.setData(data);

        return response;
    }

    private TitleResponse titleEntityToTitleResponse(TitleEntity entity) {
        TitleResponse titleResponse = new TitleResponse();
        titleResponse.setTitle(entity.getName());
        titleResponse.setDateWriting(entity.getDateWriting());
        titleResponse.setImgUrl(entity.getImageUrl());
        titleResponse.setId(entity.getId());
        titleResponse.setGenres(
                getGenreName(entity.getGenres()));
        titleResponse.setAuthorsNames(
                getAuthorName(entity.getAuthors()));

        return titleResponse;
    }

    private String getAuthorName(Set<AuthorEntity> authors) {
        if (authors.isEmpty())
            return "";

        String authorName = authors.stream()
                .map(a -> a.getFirstName().charAt(0) + "." + a.getLastName())
                .collect(Collectors.joining(","));

        return authorName;
    }

    private String getGenreName(Set<GenreEntity> genres) {
        if (genres.isEmpty())
            return "";

        String genreNames = genres.stream()
                .map(g -> g.getGenreName())
                .collect(Collectors.joining(","));

        return genreNames;
    }

    private AuthorResponse authorEntityToAuthorResponse(AuthorEntity e) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setFirstName(e.getFirstName());
        authorResponse.setLastName(e.getLastName());
        authorResponse.setId(e.getId());
        authorResponse.setWrittenBooks(
                getAuthorTitle(e.getTitles()));

        return authorResponse;
    }

    private String getAuthorTitle(Set<TitleEntity> titleEntities) {
        if (titleEntities.isEmpty())
            return null;

        var result = titleEntities.stream()
                .map(t -> t.getName())
                .collect(Collectors.joining(","));

        return result;
    }

    private Book bookEntityToBook(BookEntity entity) {
        var book = new Book();
        book.setPublisher(entity.getPublisher());
        book.setDatePublication(entity.getDatePublication());
        book.setPrice(entity.getPrice());

        return book;
    }
}
