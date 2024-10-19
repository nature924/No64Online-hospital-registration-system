
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
 * 用户
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/huiyuan")
public class HuiyuanController {
    private static final Logger logger = LoggerFactory.getLogger(HuiyuanController.class);

    @Autowired
    private HuiyuanService huiyuanService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service

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
        PageUtils page = huiyuanService.queryPage(params);

        //字典表数据转换
        List<HuiyuanView> list =(List<HuiyuanView>)page.getList();
        for(HuiyuanView c:list){
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
        HuiyuanEntity huiyuan = huiyuanService.selectById(id);
        if(huiyuan !=null){
            //entity转view
            HuiyuanView view = new HuiyuanView();
            BeanUtils.copyProperties( huiyuan , view );//把实体数据重构到view中

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
    public R save(@RequestBody HuiyuanEntity huiyuan, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,huiyuan:{}",this.getClass().getName(),huiyuan.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<HuiyuanEntity> queryWrapper = new EntityWrapper<HuiyuanEntity>()
            .eq("username", huiyuan.getUsername())
            .or()
            .eq("huiyuan_phone", huiyuan.getHuiyuanPhone())
            .or()
            .eq("huiyuan_id_number", huiyuan.getHuiyuanIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuiyuanEntity huiyuanEntity = huiyuanService.selectOne(queryWrapper);
        if(huiyuanEntity==null){
            huiyuan.setCreateTime(new Date());
            huiyuan.setPassword("123456");
            huiyuanService.insert(huiyuan);
            return R.ok();
        }else {
            return R.error(511,"账户或者用户手机号或者用户身份证号已经被使用");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody HuiyuanEntity huiyuan, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,huiyuan:{}",this.getClass().getName(),huiyuan.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<HuiyuanEntity> queryWrapper = new EntityWrapper<HuiyuanEntity>()
            .notIn("id",huiyuan.getId())
            .andNew()
            .eq("username", huiyuan.getUsername())
            .or()
            .eq("huiyuan_phone", huiyuan.getHuiyuanPhone())
            .or()
            .eq("huiyuan_id_number", huiyuan.getHuiyuanIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuiyuanEntity huiyuanEntity = huiyuanService.selectOne(queryWrapper);
        if("".equals(huiyuan.getHuiyuanPhoto()) || "null".equals(huiyuan.getHuiyuanPhoto())){
                huiyuan.setHuiyuanPhoto(null);
        }
        if(huiyuanEntity==null){
            huiyuanService.updateById(huiyuan);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"账户或者用户手机号或者用户身份证号已经被使用");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        huiyuanService.deleteBatchIds(Arrays.asList(ids));
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
            List<HuiyuanEntity> huiyuanList = new ArrayList<>();//上传的东西
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
                            HuiyuanEntity huiyuanEntity = new HuiyuanEntity();
//                            huiyuanEntity.setUsername(data.get(0));                    //账户 要改的
//                            //huiyuanEntity.setPassword("123456");//密码
//                            huiyuanEntity.setHuiyuanName(data.get(0));                    //用户姓名 要改的
//                            huiyuanEntity.setHuiyuanPhone(data.get(0));                    //用户手机号 要改的
//                            huiyuanEntity.setHuiyuanIdNumber(data.get(0));                    //用户身份证号 要改的
//                            huiyuanEntity.setHuiyuanPhoto("");//详情和图片
//                            huiyuanEntity.setSexTypes(Integer.valueOf(data.get(0)));   //性别 要改的
//                            huiyuanEntity.setHuiyuanEmail(data.get(0));                    //电子邮箱 要改的
//                            huiyuanEntity.setNewMoney(data.get(0));                    //余额 要改的
//                            huiyuanEntity.setHuiyuanContent("");//详情和图片
//                            huiyuanEntity.setCreateTime(date);//时间
                            huiyuanList.add(huiyuanEntity);


                            //把要查询是否重复的字段放入map中
                                //账户
                                if(seachFields.containsKey("username")){
                                    List<String> username = seachFields.get("username");
                                    username.add(data.get(0));//要改的
                                }else{
                                    List<String> username = new ArrayList<>();
                                    username.add(data.get(0));//要改的
                                    seachFields.put("username",username);
                                }
                                //用户手机号
                                if(seachFields.containsKey("huiyuanPhone")){
                                    List<String> huiyuanPhone = seachFields.get("huiyuanPhone");
                                    huiyuanPhone.add(data.get(0));//要改的
                                }else{
                                    List<String> huiyuanPhone = new ArrayList<>();
                                    huiyuanPhone.add(data.get(0));//要改的
                                    seachFields.put("huiyuanPhone",huiyuanPhone);
                                }
                                //用户身份证号
                                if(seachFields.containsKey("huiyuanIdNumber")){
                                    List<String> huiyuanIdNumber = seachFields.get("huiyuanIdNumber");
                                    huiyuanIdNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> huiyuanIdNumber = new ArrayList<>();
                                    huiyuanIdNumber.add(data.get(0));//要改的
                                    seachFields.put("huiyuanIdNumber",huiyuanIdNumber);
                                }
                        }

                        //查询是否重复
                         //账户
                        List<HuiyuanEntity> huiyuanEntities_username = huiyuanService.selectList(new EntityWrapper<HuiyuanEntity>().in("username", seachFields.get("username")));
                        if(huiyuanEntities_username.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(HuiyuanEntity s:huiyuanEntities_username){
                                repeatFields.add(s.getUsername());
                            }
                            return R.error(511,"数据库的该表中的 [账户] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //用户手机号
                        List<HuiyuanEntity> huiyuanEntities_huiyuanPhone = huiyuanService.selectList(new EntityWrapper<HuiyuanEntity>().in("huiyuan_phone", seachFields.get("huiyuanPhone")));
                        if(huiyuanEntities_huiyuanPhone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(HuiyuanEntity s:huiyuanEntities_huiyuanPhone){
                                repeatFields.add(s.getHuiyuanPhone());
                            }
                            return R.error(511,"数据库的该表中的 [用户手机号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //用户身份证号
                        List<HuiyuanEntity> huiyuanEntities_huiyuanIdNumber = huiyuanService.selectList(new EntityWrapper<HuiyuanEntity>().in("huiyuan_id_number", seachFields.get("huiyuanIdNumber")));
                        if(huiyuanEntities_huiyuanIdNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(HuiyuanEntity s:huiyuanEntities_huiyuanIdNumber){
                                repeatFields.add(s.getHuiyuanIdNumber());
                            }
                            return R.error(511,"数据库的该表中的 [用户身份证号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        huiyuanService.insertBatch(huiyuanList);
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
    * 登录
    */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        HuiyuanEntity huiyuan = huiyuanService.selectOne(new EntityWrapper<HuiyuanEntity>().eq("username", username));
        if(huiyuan==null || !huiyuan.getPassword().equals(password))
            return R.error("账号或密码不正确");
        //  // 获取监听器中的字典表
        // ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        // Map<String, Map<Integer, String>> dictionaryMap= (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        // Map<Integer, String> role_types = dictionaryMap.get("role_types");
        // role_types.get(.getRoleTypes());
        String token = tokenService.generateToken(huiyuan.getId(),username, "huiyuan", "用户");
        R r = R.ok();
        r.put("token", token);
        r.put("role","用户");
        r.put("username",huiyuan.getHuiyuanName());
        r.put("tableName","huiyuan");
        r.put("userId",huiyuan.getId());
        return r;
    }

    /**
    * 注册
    */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody HuiyuanEntity huiyuan){
//    	ValidatorUtils.validateEntity(user);
        Wrapper<HuiyuanEntity> queryWrapper = new EntityWrapper<HuiyuanEntity>()
            .eq("username", huiyuan.getUsername())
            .or()
            .eq("huiyuan_phone", huiyuan.getHuiyuanPhone())
            .or()
            .eq("huiyuan_id_number", huiyuan.getHuiyuanIdNumber())
            ;
        HuiyuanEntity huiyuanEntity = huiyuanService.selectOne(queryWrapper);
        if(huiyuanEntity != null)
            return R.error("账户或者用户手机号或者用户身份证号已经被使用");
        huiyuan.setNewMoney(0.0);
        huiyuan.setCreateTime(new Date());
        huiyuanService.insert(huiyuan);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer  id){
        HuiyuanEntity huiyuan = new HuiyuanEntity();
        huiyuan.setPassword("123456");
        huiyuan.setId(id);
        huiyuanService.updateById(huiyuan);
        return R.ok();
    }


    /**
     * 忘记密码
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        HuiyuanEntity huiyuan = huiyuanService.selectOne(new EntityWrapper<HuiyuanEntity>().eq("username", username));
        if(huiyuan!=null){
            huiyuan.setPassword("123456");
            boolean b = huiyuanService.updateById(huiyuan);
            if(!b){
               return R.error();
            }
        }else{
           return R.error("账号不存在");
        }
        return R.ok();
    }


    /**
    * 获取用户的session用户信息
    */
    @RequestMapping("/session")
    public R getCurrHuiyuan(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("userId");
        HuiyuanEntity huiyuan = huiyuanService.selectById(id);
        if(huiyuan !=null){
            //entity转view
            HuiyuanView view = new HuiyuanView();
            BeanUtils.copyProperties( huiyuan , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }


    /**
    * 退出
    */
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
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
        PageUtils page = huiyuanService.queryPage(params);

        //字典表数据转换
        List<HuiyuanView> list =(List<HuiyuanView>)page.getList();
        for(HuiyuanView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        HuiyuanEntity huiyuan = huiyuanService.selectById(id);
            if(huiyuan !=null){


                //entity转view
                HuiyuanView view = new HuiyuanView();
                BeanUtils.copyProperties( huiyuan , view );//把实体数据重构到view中

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
    public R add(@RequestBody HuiyuanEntity huiyuan, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,huiyuan:{}",this.getClass().getName(),huiyuan.toString());
        Wrapper<HuiyuanEntity> queryWrapper = new EntityWrapper<HuiyuanEntity>()
            .eq("username", huiyuan.getUsername())
            .or()
            .eq("huiyuan_phone", huiyuan.getHuiyuanPhone())
            .or()
            .eq("huiyuan_id_number", huiyuan.getHuiyuanIdNumber())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuiyuanEntity huiyuanEntity = huiyuanService.selectOne(queryWrapper);
        if(huiyuanEntity==null){
            huiyuan.setCreateTime(new Date());
        huiyuan.setPassword("123456");
        huiyuanService.insert(huiyuan);
            return R.ok();
        }else {
            return R.error(511,"账户或者用户手机号或者用户身份证号已经被使用");
        }
    }


}
