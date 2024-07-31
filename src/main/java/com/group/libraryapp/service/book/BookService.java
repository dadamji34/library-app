package com.group.libraryapp.service.book;

import com.group.libraryapp.domain.book.BookRepository;
import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.domain.user.userloanhistory.UserLoanHistory;
import com.group.libraryapp.domain.user.userloanhistory.UserLoanHistoryRepository;
import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserLoanHistoryRepository userLoanHistoryRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request){
        // 1. 책 정보 가져오기
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalAccessError::new);

        // 2. 대출기록 정보 확인하여 대출중인지 확인
        // 3. 만약에 대출 중이라면 예외 발생
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)){
            throw new IllegalArgumentException("진작 대출되어 있는 책입니다.");
        }

        // 4. 유저 정보를 가져온다
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        // 5. 유저 정보와 책 정보를 기반으로 UserLoanHistory를 저장
        //userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));
        user.loanBook(book.getName());
    }

    @Transactional
    public void returnBook(BookReturnRequest request){
        // BookReturnRequest.java와 UserLoanHistory.java를 보면, 현재 들어오는 정보(BookReturnRequest의 request)가 유저 이름과 책 이름인데,
        // UserLoanHistory.java에서는 유저 아이디와 책 이름이 필요.
        // 따라서 유저 이름을 가지고 유저를 찾아서 유저의 아이디를 가져와야함
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다: " + request.getUserName()));

        // 유저 ID와 책 이름으로 대출 기록을 찾는다
//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
//                .orElseThrow(() -> new IllegalArgumentException("대출 기록을 찾을 수 없습니다: 유저ID=" + user.getId() + ", 책 이름=" + request.getBookName()));
//        history.doReturn();
        user.returnBook(request.getBookName());

        //userLoanHistoryRepository.save(history);
        // -> transactional annotation을 사용하고 있기 때문에 영속성 컨텍스트가 해당 함수(returnBook)안에 존재하고
        // 영속성 컨텍스트는 변경 감지 기능이 있기 때문에 영속성 컨텍스트 내에서 가져온 엔티티 객체(UserLoanHistory)에 대해서 변경이 일어나면 그걸 감지했다가 자동으로 업데이트 해줌

    }
}
