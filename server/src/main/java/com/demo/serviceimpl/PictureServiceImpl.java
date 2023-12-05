package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PictureMapper;
import com.demo.pojo.Picture;
import com.demo.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper,Picture> implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public List<Picture> findPicture(Picture picture) {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=picture.getId (),"id",picture.getId ())
                .eq(null!=picture.getProductionId (),"production_id",picture.getProductionId());
        return  pictureMapper.selectList (queryWrapper);
    }

    @Override
    public List<Picture> findByIds(List<Long> ids) {
        List<Picture> Pictures = pictureMapper.selectBatchIds (ids);
        return Pictures;
    }

    @Override
    public Picture findByonid(Long id) {
        Picture Picture = pictureMapper.selectById (id);
        return Picture;
    }

    @Override
    public String updatePicture(Picture picture) {
        int n = pictureMapper.updateById (picture);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertPicture(Picture picture) {
        int n = pictureMapper.insert (picture);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deletePicture(Long id){
        int n = pictureMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
