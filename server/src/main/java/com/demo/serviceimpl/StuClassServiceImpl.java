package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.StuClassMapper;
import com.demo.pojo.StuClass;
import com.demo.service.StuClassService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuClassServiceImpl extends ServiceImpl<StuClassMapper,StuClass> implements StuClassService {

    @Autowired
    private StuClassMapper stuClassMapper;

    @Override
    public List<StuClass> findStuClass(StuClass stuClass) {
        QueryWrapper<StuClass> queryWrapper = new QueryWrapper<> ();
        queryWrapper.like ("".equals(stuClass.getClassName())&&stuClass.getClassName().isEmpty(),"class_name",stuClass.getClassName ())
                .eq (null!=stuClass.getId ()&&stuClass.getId ()!=0,"id",stuClass.getId ());
        return stuClassMapper.selectList (queryWrapper);

    }

    @Override
    public List<StuClass> findByName(String className) {
        QueryWrapper<StuClass> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null != className&& "" != className,"class_name",className);
        List<StuClass> stuClasss = stuClassMapper.selectList (queryWrapper);
        return stuClasss;
    }

    @Override
    public StuClass findByonid(Long id) {
        StuClass stuClass = stuClassMapper.selectById (id);
        return stuClass;
    }

    @Override
    public String updateStuClass(StuClass stuClass) {
        int n = stuClassMapper.updateById (stuClass);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertStuClass(StuClass stuClass) {
        int n = stuClassMapper.insert (stuClass);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteStuClass(Long id){
        int n = stuClassMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
