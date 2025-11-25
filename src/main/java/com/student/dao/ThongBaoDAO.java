package com.student.dao;

import java.util.List;
import com.student.model.ThongBao;

public interface ThongBaoDAO {
    // Khai báo các hành động cần làm
    List<ThongBao> findAll(String searchKey, int page, int pageSize);
    int count(String searchKey);
    boolean insert(ThongBao tb);
    boolean delete(int maTB);
    boolean update(ThongBao tb);
}
