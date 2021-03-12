package com.example.study.model.newtwork;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

  //api 통신시간
//  @JsonProperty("transaction_tie") // snake case로 넘겨주기 위해서 이렇게 설정할 수 있음.

  // String으로 시간을 표현하는 이유는? ISO, yyyy-mm-dd HH:MM:SS ㅡemd
  private LocalDateTime transactionTime;

  //api 응답 코드
  private String resultCode;

  //api 부가설명
  private String description;

  private T data;

  private Pagination pagination;


  //정상적일 때는 OK, 비정상적일때는 Error data가 있을 때는 data OK 출력해주는 method
  public static<T> Header<T> OK(){
    return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("OK")
            .description("OK")
            .build();
  }

  public static<T> Header<T> OK(T data){
    return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("OK")
            .description("OK")
            .data(data)
            .build();
  }

  public static<T> Header<T> OK(T data, Pagination pagination){
    return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("OK")
            .description("OK")
            .data(data)
            .pagination(pagination)
            .build();
  }

  public static<T> Header<T> ERROR(String description){
    return (Header<T>)Header.builder()
            .transactionTime(LocalDateTime.now())
            .resultCode("ERROR")
            .description(description)
            .build();
  }
}
