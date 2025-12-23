package com.student.dao;

import com.student.model.DocThongBao;

public interface DocThongBaoDAO {
    boolean markAsRead(int maHS, int maTB);
    boolean isRead(int maHS, int maTB);
    int countUnreadAnnouncements(int maHS);
}
