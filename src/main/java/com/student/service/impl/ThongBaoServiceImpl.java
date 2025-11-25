package com.student.service.impl;
import java.util.List;
import com.student.dao.ThongBaoDAO;
import com.student.dao.impl.ThongBaoDAOImpl;
import com.student.model.ThongBao;
import com.student.service.ThongBaoService;

public class ThongBaoServiceImpl implements ThongBaoService {
    
    private ThongBaoDAO dao = new ThongBaoDAOImpl(); // Gọi DAO

    @Override
    public List<ThongBao> findAll(String searchKey, int page, int pageSize) {
        return dao.findAll(searchKey, page, pageSize);
    }
    @Override
    public int count(String searchKey) { return dao.count(searchKey); }
    @Override
    public boolean insert(ThongBao tb) { return dao.insert(tb); }
    @Override
    public boolean delete(int maTB) { return dao.delete(maTB); }
	@Override
	public boolean update(ThongBao tb) {return dao.update(tb); }
}