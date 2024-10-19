
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 医生挂号订单
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/zhuanjiaOrder")
public class ZhuanjiaOrderController {
    private static final Logger logger = LoggerFactory.getLogger(ZhuanjiaOrderController.class);

    @Autowired
    private ZhuanjiaOrderService zhuanjiaOrderService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private HuiyuanService huiyuanService;
    @Autowired
    private ZhuanjiaService zhuanjiaService;



    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("huiyuanId",request.getSession().getAttribute("userId"));
        else if("医生".equals(role))
            params.put("zhuanjiaId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = zhuanjiaOrderService.queryPage(params);

        //字典表数据转换
        List<ZhuanjiaOrderView> list =(List<ZhuanjiaOrderView>)page.getList();
        for(ZhuanjiaOrderView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ZhuanjiaOrderEntity zhuanjiaOrder = zhuanjiaOrderService.selectById(id);
        if(zhuanjiaOrder !=null){
            //entity转view
            ZhuanjiaOrderView view = new ZhuanjiaOrderView();
            BeanUtils.copyProperties( zhuanjiaOrder , view );//把实体数据重构到view中

                //级联表
                HuiyuanEntity huiyuan = huiyuanService.selectById(zhuanjiaOrder.getHuiyuanId());
                if(huiyuan != null){
                    BeanUtils.copyProperties( huiyuan , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setHuiyuanId(huiyuan.getId());
                }
                //级联表
                ZhuanjiaEntity zhuanjia = zhuanjiaService.selectById(zhuanjiaOrder.getZhuanjiaId());
                if(zhuanjia != null){
                    BeanUtils.copyProperties( zhuanjia , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setZhuanjiaId(zhuanjia.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ZhuanjiaOrderEntity zhuanjiaOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,zhuanjiaOrder:{}",this.getClass().getName(),zhuanjiaOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("医生".equals(role))
            zhuanjiaOrder.setZhuanjiaId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        else if("用户".equals(role))
            zhuanjiaOrder.setHuiyuanId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        zhuanjiaOrder.setInsertTime(new Date());
        zhuanjiaOrder.setCreateTime(new Date());
        zhuanjiaOrderService.insert(zhuanjiaOrder);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ZhuanjiaOrderEntity zhuanjiaOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,zhuanjiaOrder:{}",this.getClass().getName(),zhuanjiaOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("医生".equals(role))
//            zhuanjiaOrder.setZhuanjiaId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
//        else if("用户".equals(role))
//            zhuanjiaOrder.setHuiyuanId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<ZhuanjiaOrderEntity> queryWrapper = new EntityWrapper<ZhuanjiaOrderEntity>()
            .eq("id",0)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhuanjiaOrderEntity zhuanjiaOrderEntity = zhuanjiaOrderService.selectOne(queryWrapper);
        if(zhuanjiaOrderEntity==null){
            zhuanjiaOrderService.updateById(zhuanjiaOrder);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/shenhe")
    public R shenhe(@RequestBody ZhuanjiaOrderEntity zhuanjiaOrder, HttpServletRequest request){
        logger.debug("shenhe方法:,,Controller:{},,zhuanjiaOrder:{}",this.getClass().getName(),zhuanjiaOrder.toString());

        if(zhuanjiaOrder.getZhuanjiaOrderYesnoTypes() ==3){
            HuiyuanEntity huiyuanEntity = huiyuanService.selectById(zhuanjiaOrder.getHuiyuanId());
            ZhuanjiaEntity zhuanjiaEntity = zhuanjiaService.selectById(zhuanjiaOrder.getZhuanjiaId());
            if(huiyuanEntity != null && zhuanjiaEntity != null){
                huiyuanEntity.setNewMoney(huiyuanEntity.getNewMoney()+zhuanjiaEntity.getGuahaoMoney());
                huiyuanService.updateById(huiyuanEntity);
            }
        }
            zhuanjiaOrderService.updateById(zhuanjiaOrder);//根据id更新
            return R.ok();

    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        zhuanjiaOrderService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<ZhuanjiaOrderEntity> zhuanjiaOrderList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            ZhuanjiaOrderEntity zhuanjiaOrderEntity = new ZhuanjiaOrderEntity();
//                            zhuanjiaOrderEntity.setZhuanjiaOrderUuidNumber(data.get(0));                    //号 要改的
//                            zhuanjiaOrderEntity.setZhuanjiaId(Integer.valueOf(data.get(0)));   //医生 要改的
//                            zhuanjiaOrderEntity.setHuiyuanId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            zhuanjiaOrderEntity.setGuahaoTime(sdf.parse(data.get(0)));          //挂号日期 要改的
//                            zhuanjiaOrderEntity.setShijianduanTypes(Integer.valueOf(data.get(0)));   //时间段 要改的
//                            zhuanjiaOrderEntity.setZhuanjiaOrderYesnoTypes(Integer.valueOf(data.get(0)));   //状态 要改的
//                            zhuanjiaOrderEntity.setZhuanjiaOrderYesnoText(data.get(0));                    //审核意见 要改的
//                            zhuanjiaOrderEntity.setInsertTime(date);//时间
//                            zhuanjiaOrderEntity.setCreateTime(date);//时间
                            zhuanjiaOrderList.add(zhuanjiaOrderEntity);


                            //把要查询是否重复的字段放入map中
                                //号
                                if(seachFields.containsKey("zhuanjiaOrderUuidNumber")){
                                    List<String> zhuanjiaOrderUuidNumber = seachFields.get("zhuanjiaOrderUuidNumber");
                                    zhuanjiaOrderUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> zhuanjiaOrderUuidNumber = new ArrayList<>();
                                    zhuanjiaOrderUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("zhuanjiaOrderUuidNumber",zhuanjiaOrderUuidNumber);
                                }
                        }

                        //查询是否重复
                         //号
                        List<ZhuanjiaOrderEntity> zhuanjiaOrderEntities_zhuanjiaOrderUuidNumber = zhuanjiaOrderService.selectList(new EntityWrapper<ZhuanjiaOrderEntity>().in("zhuanjia_order_uuid_number", seachFields.get("zhuanjiaOrderUuidNumber")));
                        if(zhuanjiaOrderEntities_zhuanjiaOrderUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(ZhuanjiaOrderEntity s:zhuanjiaOrderEntities_zhuanjiaOrderUuidNumber){
                                repeatFields.add(s.getZhuanjiaOrderUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        zhuanjiaOrderService.insertBatch(zhuanjiaOrderList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = zhuanjiaOrderService.queryPage(params);

        //字典表数据转换
        List<ZhuanjiaOrderView> list =(List<ZhuanjiaOrderView>)page.getList();
        for(ZhuanjiaOrderView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ZhuanjiaOrderEntity zhuanjiaOrder = zhuanjiaOrderService.selectById(id);
            if(zhuanjiaOrder !=null){


                //entity转view
                ZhuanjiaOrderView view = new ZhuanjiaOrderView();
                BeanUtils.copyProperties( zhuanjiaOrder , view );//把实体数据重构到view中

                //级联表
                    HuiyuanEntity huiyuan = huiyuanService.selectById(zhuanjiaOrder.getHuiyuanId());
                if(huiyuan != null){
                    BeanUtils.copyProperties( huiyuan , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setHuiyuanId(huiyuan.getId());
                }
                //级联表
                    ZhuanjiaEntity zhuanjia = zhuanjiaService.selectById(zhuanjiaOrder.getZhuanjiaId());
                if(zhuanjia != null){
                    BeanUtils.copyProperties( zhuanjia , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setZhuanjiaId(zhuanjia.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody ZhuanjiaOrderEntity zhuanjiaOrder, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,zhuanjiaOrder:{}",this.getClass().getName(),zhuanjiaOrder.toString());
            ZhuanjiaEntity zhuanjiaEntity = zhuanjiaService.selectById(zhuanjiaOrder.getZhuanjiaId());
            if(zhuanjiaEntity == null){
                return R.error(511,"查不到该医生");
            }
            // Double zhuanjiaNewMoney = zhuanjiaEntity.getZhuanjiaNewMoney();

            if(false){
            }

            //计算所获得积分
            Double buyJifen =0.0;
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            HuiyuanEntity huiyuanEntity = huiyuanService.selectById(userId);
            if(huiyuanEntity == null)
                return R.error(511,"用户不能为空");
            if(huiyuanEntity.getNewMoney() == null)
                return R.error(511,"用户金额不能为空");
            double balance = huiyuanEntity.getNewMoney() - zhuanjiaEntity.getGuahaoMoney();//余额
            if(balance<0)
                return R.error(511,"余额不够支付");
            zhuanjiaOrder.setHuiyuanId(userId); //设置订单支付人id
            zhuanjiaOrder.setZhuanjiaOrderUuidNumber(String.valueOf(new Date().getTime()));
            zhuanjiaOrder.setInsertTime(new Date());
            zhuanjiaOrder.setCreateTime(new Date());
                zhuanjiaOrderService.insert(zhuanjiaOrder);//新增订单
            huiyuanEntity.setNewMoney(balance);//设置金额
            huiyuanService.updateById(huiyuanEntity);
            return R.ok();
    }



}
