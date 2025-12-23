package com.student.dao;

import java.util.List;
import com.student.model.ThongBao;

public interface ThongBaoDAO {

    List<ThongBao> findAll(String searchKey, int page, int pageSize);
    ThongBao findById(int maTB);
    int count(String searchKey);
    int countRecentAnnouncements(int days);
    boolean insert(ThongBao tb);
    boolean delete(int maTB);
    boolean update(ThongBao tb);
}
