package com.student.service;
import java.util.List;
import com.student.model.ThongBao;

public interface ThongBaoService {
    List<ThongBao> findAll(String searchKey, int page, int pageSize);
    int count(String searchKey);
    boolean insert(ThongBao tb);
    boolean delete(int maTB);
    boolean update(ThongBao tb);
}