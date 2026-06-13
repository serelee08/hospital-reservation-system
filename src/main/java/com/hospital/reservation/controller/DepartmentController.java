package com.hospital.reservation.controller;

import com.hospital.reservation.service.DepartmentService;
import com.hospital.reservation.vo.DepartmentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentVO>> getAll() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{departmentNo}")
    public ResponseEntity<DepartmentVO> getById(@PathVariable Long departmentNo) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentNo));
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody DepartmentVO department) {
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }

    @PutMapping("/{departmentNo}")
    public ResponseEntity<Integer> update(@PathVariable Long departmentNo,
                                           @RequestBody DepartmentVO department) {
        department.setDepartmentNo(departmentNo);
        return ResponseEntity.ok(departmentService.updateDepartment(department));
    }

    @DeleteMapping("/{departmentNo}")
    public ResponseEntity<Integer> delete(@PathVariable Long departmentNo) {
        return ResponseEntity.ok(departmentService.deleteDepartment(departmentNo));
    }
}