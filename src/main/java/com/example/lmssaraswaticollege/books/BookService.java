package com.example.lmssaraswaticollege.books;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }

    public boolean addBook(Books book){
        boolean succ = bookRepository.findById(book.getAccNo()).isPresent();

        if(succ)
            return false;
        else{
            bookRepository.insert(book);
            return true;
        }
    }
}
