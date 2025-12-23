package com.student.service;
import java.util.List;
import com.student.model.ThongBao;

public interface ThongBaoService {
    List<ThongBao> findAll(String searchKey, int page, int pageSize);
    ThongBao findById(int maTB);
    int count(String searchKey);
    int countRecentAnnouncements(int days);
    boolean insert(ThongBao tb);
    boolean delete(int maTB);
    boolean update(ThongBao tb);
}