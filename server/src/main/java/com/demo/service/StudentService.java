package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService extends IService<Student> {

    List<Student> findByIds(List<Long> ids);

    Student findByonid(Long id);

    List<Student> stuNioPassword(String stuNio, String password);

    String updateStudent(Student student);

    String deleteStudent(Long id);

    String insertStudent(Student student);

    List<Student> findStudent(Student student);

    String insertForExcel(MultipartFile excelFile);
}
