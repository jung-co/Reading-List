package com.readinglist.controller;

import com.readinglist.domain.Book;
import com.readinglist.domain.Reader;
import com.readinglist.properties.AmazonProperties;
import com.readinglist.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class ReadingListController {
    private ReadingListRepository readingListRepository;
    private AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository,
                                 AmazonProperties amazonProperties){ // AmazonProperties 주입
        this.readingListRepository=readingListRepository;
        this.amazonProperties=amazonProperties;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String readersBooks(Reader reader, Model model){
        Reader reader1 = new Reader();
        if(reader == null){
            reader1.setFullname("Jung Yoon");
            reader = reader1;
        }

        List<Book> readingList=readingListRepository.findByReader(reader);
        if(readingList != null){
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProperties.getAssociateId()); // 제휴 ID를 모델에 추가
        }
        return "readingList";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String addToReadingList(Reader reader, Book book){
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/";
    }
}
