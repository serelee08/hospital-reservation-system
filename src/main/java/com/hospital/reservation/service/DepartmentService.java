package com.hospital.reservation.service;

import com.hospital.reservation.dao.DepartmentDAO;
import com.hospital.reservation.vo.DepartmentVO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartmentService {
    
       private final DepartmentDAO departmentDAO;
       
       public List<DepartmentVO> getAllDepartments(){
    	   return departmentDAO.findAll();
       }
       public DepartmentVO getDepartmentById(Long departmentNo) {
    	   return departmentDAO.findById(departmentNo);
       }
       public int createDepartment(DepartmentVO department) {
    	   return departmentDAO.insert(department);
       }
       public int updateDepartment(DepartmentVO department) {
    	   return departmentDAO.update(department);
       }
       public int deleteDepartment(Long departmentNo) {
    	   return departmentDAO.delete(departmentNo);
       }


}
