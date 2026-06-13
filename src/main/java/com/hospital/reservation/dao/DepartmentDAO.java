package com.hospital.reservation.dao;

import com.hospital.reservation.vo.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DepartmentDAO {
       List<DepartmentVO> findAll();
       DepartmentVO findById(Long departmentNo);
       int insert(DepartmentVO vo);
       int update(DepartmentVO vo);
       int delete(Long departmentNo);
       
}
