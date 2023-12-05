package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.StuClass;

import java.util.List;

public interface StuClassService extends IService<StuClass> {

    List<StuClass> findByName(String className);

    StuClass findByonid(Long id);

    String updateStuClass(StuClass stuClass);

    String deleteStuClass(Long id);

    String insertStuClass(StuClass stuClass);

    List<StuClass> findStuClass(StuClass stuClass);
}
