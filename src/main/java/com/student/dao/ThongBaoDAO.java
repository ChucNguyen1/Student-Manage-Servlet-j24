package com.student.dao;

import java.util.List;
import com.student.model.ThongBao;

public interface ThongBaoDAO {

    List<ThongBao> findAll(String searchKey, int page, int pageSize);
    int count(String searchKey);
    boolean insert(ThongBao tb);
    boolean delete(int maTB);
    boolean update(ThongBao tb);
}
