package com.example.study.ifs;

import com.example.study.model.newtwork.Header;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Header<Req> request); //todo request opject 추가 필요함

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);
}
