package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.Evaluation;
import com.demo.pojo.Picture;

import java.util.List;

/**
 * @autthor lc1100
 * @create 2022-03-28-16:22
 */
public interface PictureService extends IService<Picture> {
    List<Picture> findPicture(Picture picture);

    List<Picture> findByIds(List<Long> ids);

    Picture findByonid(Long id);

    String updatePicture(Picture picture);

    String insertPicture(Picture picture);

    String deletePicture(Long id);

}
