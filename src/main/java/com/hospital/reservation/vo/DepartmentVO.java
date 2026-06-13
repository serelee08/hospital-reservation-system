package com.hospital.reservation.vo;

import com.hospital.reservation.dao.DepartmentDAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVO {
       private Long departmentNo;
       private String departmentName;
       private String describtion;
       private String createdAt;
}
