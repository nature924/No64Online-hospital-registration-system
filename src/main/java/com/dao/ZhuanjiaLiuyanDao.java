package com.dao;

import com.entity.ZhuanjiaLiuyanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.ZhuanjiaLiuyanView;

/**
 * 医生留言 Dao 接口
 *
 * @author 
 */
public interface ZhuanjiaLiuyanDao extends BaseMapper<ZhuanjiaLiuyanEntity> {

   List<ZhuanjiaLiuyanView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
