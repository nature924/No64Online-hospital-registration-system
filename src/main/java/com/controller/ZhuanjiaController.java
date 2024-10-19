
package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ZhuanjiaEntity;
import com.entity.view.ZhuanjiaView;
import com.service.DictionaryService;
import com.service.HuiyuanService;
import com.service.TokenService;
import com.service.ZhuanjiaService;
import com.utils.PageUtils;
import com.utils.PoiUtil;
import com.utils.R;
import com.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 医生
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/zhuanjia")
public class ZhuanjiaController {
    private static final Logger logger = LoggerFactory.getLogger(ZhuanjiaController.class);

    @Autowired
    private ZhuanjiaService zhuanjiaService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service

    @Autowired
    private HuiyuanService huiyuanService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户 show2 photoShow".equals(role))
            params.put("huiyuanId",request.getSession().getAttribute("userId"));
        else if("医生".equals(role))
            params.put("zhuanjiaId",request.getSession().getAttribute("userId"));
        params.put("zhuanjiaDeleteStart",1);params.put("zhuanjiaDeleteEnd",1);
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = zhuanjiaService.queryPage(params);

        //字典表数据转换
        List<ZhuanjiaView> list =(List<ZhuanjiaView>)page.getList();
        for(ZhuanjiaView c:list){
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
        ZhuanjiaEntity zhuanjia = zhuanjiaService.selectById(id);
        if(zhuanjia !=null){
            //entity转view
            ZhuanjiaView view = new ZhuanjiaView();
            BeanUtils.copyProperties( zhuanjia , view );//把实体数据重构到view中

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
    public R save(@RequestBody ZhuanjiaEntity zhuanjia, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,zhuanjia:{}",this.getClass().getName(),zhuanjia.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<ZhuanjiaEntity> queryWrapper = new EntityWrapper<ZhuanjiaEntity>()
            .eq("username", zhuanjia.getUsername())
            .or()
            .eq("zhuanjia_phone", zhuanjia.getZhuanjiaPhone())
            .or()
            .eq("zhuanjia_id_number", zhuanjia.getZhuanjiaIdNumber())
            .andNew()
            .eq("zhuanjia_delete", 1)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhuanjiaEntity zhuanjiaEntity = zhuanjiaService.selectOne(queryWrapper);
        if(zhuanjiaEntity==null){
            zhuanjia.setZhuanjiaDelete(1);
            zhuanjia.setInsertTime(new Date());
            zhuanjia.setCreateTime(new Date());
            zhuanjia.setPassword("123456");
            zhuanjiaService.insert(zhuanjia);
            return R.ok();
        }else {
            return R.error(511,"账户或者医生手机号或者医生身份证号已经被使用");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ZhuanjiaEntity zhuanjia, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,zhuanjia:{}",this.getClass().getName(),zhuanjia.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<ZhuanjiaEntity> queryWrapper = new EntityWrapper<ZhuanjiaEntity>()
            .notIn("id",zhuanjia.getId())
            .andNew()
            .eq("username", zhuanjia.getUsername())
            .or()
            .eq("zhuanjia_phone", zhuanjia.getZhuanjiaPhone())
            .or()
            .eq("zhuanjia_id_number", zhuanjia.getZhuanjiaIdNumber())
            .andNew()
            .eq("zhuanjia_delete", 1)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhuanjiaEntity zhuanjiaEntity = zhuanjiaService.selectOne(queryWrapper);
        if("".equals(zhuanjia.getZhuanjiaPhoto()) || "null".equals(zhuanjia.getZhuanjiaPhoto())){
                zhuanjia.setZhuanjiaPhoto(null);
        }
        if(zhuanjiaEntity==null){
            zhuanjiaService.updateById(zhuanjia);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"账户或者医生手机号或者医生身份证号已经被使用");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        ArrayList<ZhuanjiaEntity> list = new ArrayList<>();
        for(Integer id:ids){
            ZhuanjiaEntity zhuanjiaEntity = new ZhuanjiaEntity();
            zhuanjiaEntity.setId(id);
            zhuanjiaEntity.setZhuanjiaDelete(2);
            list.add(zhuanjiaEntity);
        }
        if(list != null && list.size() >0){
            zhuanjiaService.updateBatchById(list);
        }
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save(String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<ZhuanjiaEntity> zhuanjiaList = new ArrayList<>();//上传的东西
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
                            ZhuanjiaEntity zhuanjiaEntity = new ZhuanjiaEntity();
//                            zhuanjiaEntity.setUsername(data.get(0));                    //账户 要改的
//                            //zhuanjiaEntity.setPassword("123456");//密码
//                            zhuanjiaEntity.setZhuanjiaName(data.get(0));                    //医生姓名 要改的
//                            zhuanjiaEntity.setZhuanjiaPhone(data.get(0));                    //医生手机号 要改的
//                            zhuanjiaEntity.setZhuanjiaIdNumber(data.get(0));                    //医生身份证号 要改的
//                            zhuanjiaEntity.setZhuanjiaPhoto("");//详情和图片
//                            zhuanjiaEntity.setSexTypes(Integer.valueOf(data.get(0)));   //性别 要改的
//                            zhuanjiaEntity.setKeshiTypes(Integer.valueOf(data.get(0)));   //科室 要改的
//                            zhuanjiaEntity.setZhiweiTypes(Integer.valueOf(data.get(0)));   //职位 要改的
//                            zhuanjiaEntity.setGuahaoMoney(data.get(0));                    //挂号费 要改的
//                            zhuanjiaEntity.setZhuanjiaEmail(data.get(0));                    //电子邮箱 要改的
//                            zhuanjiaEntity.setZhuanjiaContent("");//详情和图片
//                            zhuanjiaEntity.setZhuanjiaDelete(1);//逻辑删除字段
//                            zhuanjiaEntity.setInsertTime(date);//时间
//                            zhuanjiaEntity.setCreateTime(date);//时间
//                            zhuanjiaEntity.setZhuanjiaPaiban(data.get(0));                    //医生排班 要改的
                            zhuanjiaList.add(zhuanjiaEntity);


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
                                //医生手机号
                                if(seachFields.containsKey("zhuanjiaPhone")){
                                    List<String> zhuanjiaPhone = seachFields.get("zhuanjiaPhone");
                                    zhuanjiaPhone.add(data.get(0));//要改的
                                }else{
                                    List<String> zhuanjiaPhone = new ArrayList<>();
                                    zhuanjiaPhone.add(data.get(0));//要改的
                                    seachFields.put("zhuanjiaPhone",zhuanjiaPhone);
                                }
                                //医生身份证号
                                if(seachFields.containsKey("zhuanjiaIdNumber")){
                                    List<String> zhuanjiaIdNumber = seachFields.get("zhuanjiaIdNumber");
                                    zhuanjiaIdNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> zhuanjiaIdNumber = new ArrayList<>();
                                    zhuanjiaIdNumber.add(data.get(0));//要改的
                                    seachFields.put("zhuanjiaIdNumber",zhuanjiaIdNumber);
                                }
                        }

                        //查询是否重复
                         //账户
                        List<ZhuanjiaEntity> zhuanjiaEntities_username = zhuanjiaService.selectList(new EntityWrapper<ZhuanjiaEntity>().in("username", seachFields.get("username")).eq("zhuanjia_delete", 1));
                        if(zhuanjiaEntities_username.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(ZhuanjiaEntity s:zhuanjiaEntities_username){
                                repeatFields.add(s.getUsername());
                            }
                            return R.error(511,"数据库的该表中的 [账户] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //医生手机号
                        List<ZhuanjiaEntity> zhuanjiaEntities_zhuanjiaPhone = zhuanjiaService.selectList(new EntityWrapper<ZhuanjiaEntity>().in("zhuanjia_phone", seachFields.get("zhuanjiaPhone")).eq("zhuanjia_delete", 1));
                        if(zhuanjiaEntities_zhuanjiaPhone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(ZhuanjiaEntity s:zhuanjiaEntities_zhuanjiaPhone){
                                repeatFields.add(s.getZhuanjiaPhone());
                            }
                            return R.error(511,"数据库的该表中的 [医生手机号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //医生身份证号
                        List<ZhuanjiaEntity> zhuanjiaEntities_zhuanjiaIdNumber = zhuanjiaService.selectList(new EntityWrapper<ZhuanjiaEntity>().in("zhuanjia_id_number", seachFields.get("zhuanjiaIdNumber")).eq("zhuanjia_delete", 1));
                        if(zhuanjiaEntities_zhuanjiaIdNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(ZhuanjiaEntity s:zhuanjiaEntities_zhuanjiaIdNumber){
                                repeatFields.add(s.getZhuanjiaIdNumber());
                            }
                            return R.error(511,"数据库的该表中的 [医生身份证号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        zhuanjiaService.insertBatch(zhuanjiaList);
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
        ZhuanjiaEntity zhuanjia = zhuanjiaService.selectOne(new EntityWrapper<ZhuanjiaEntity>().eq("username", username));
        if(zhuanjia==null || !zhuanjia.getPassword().equals(password))
            return R.error("账号或密码不正确");
        else if(zhuanjia.getZhuanjiaDelete() != 1)
            return R.error("账户已被删除");
        //  // 获取监听器中的字典表
        // ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        // Map<String, Map<Integer, String>> dictionaryMap= (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        // Map<Integer, String> role_types = dictionaryMap.get("role_types");
        // role_types.get(.getRoleTypes());
        String token = tokenService.generateToken(zhuanjia.getId(),username, "zhuanjia", "医生");
        R r = R.ok();
        r.put("token", token);
        r.put("role","医生");
        r.put("username",zhuanjia.getZhuanjiaName());
        r.put("tableName","zhuanjia");
        r.put("userId",zhuanjia.getId());
        return r;
    }

    /**
    * 注册
    */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody ZhuanjiaEntity zhuanjia){
//    	ValidatorUtils.validateEntity(user);
        Wrapper<ZhuanjiaEntity> queryWrapper = new EntityWrapper<ZhuanjiaEntity>()
            .eq("username", zhuanjia.getUsername())
            .or()
            .eq("zhuanjia_phone", zhuanjia.getZhuanjiaPhone())
            .or()
            .eq("zhuanjia_id_number", zhuanjia.getZhuanjiaIdNumber())
            .andNew()
            .eq("zhuanjia_delete", 1)
            ;
        ZhuanjiaEntity zhuanjiaEntity = zhuanjiaService.selectOne(queryWrapper);
        if(zhuanjiaEntity != null)
            return R.error("账户或者医生手机号或者医生身份证号已经被使用");
        zhuanjia.setGuahaoMoney(0.0);
        zhuanjia.setZhuanjiaDelete(1);
        zhuanjia.setInsertTime(new Date());
        zhuanjia.setCreateTime(new Date());
        zhuanjiaService.insert(zhuanjia);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer  id){
        ZhuanjiaEntity zhuanjia = new ZhuanjiaEntity();
        zhuanjia.setPassword("123456");
        zhuanjia.setId(id);
        zhuanjia.setInsertTime(new Date());
        zhuanjiaService.updateById(zhuanjia);
        return R.ok();
    }


    /**
     * 忘记密码
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        ZhuanjiaEntity zhuanjia = zhuanjiaService.selectOne(new EntityWrapper<ZhuanjiaEntity>().eq("username", username));
        if(zhuanjia!=null){
            zhuanjia.setPassword("123456");
            boolean b = zhuanjiaService.updateById(zhuanjia);
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
    public R getCurrZhuanjia(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("userId");
        ZhuanjiaEntity zhuanjia = zhuanjiaService.selectById(id);
        if(zhuanjia !=null){
            //entity转view
            ZhuanjiaView view = new ZhuanjiaView();
            BeanUtils.copyProperties( zhuanjia , view );//把实体数据重构到view中

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
        PageUtils page = zhuanjiaService.queryPage(params);

        //字典表数据转换
        List<ZhuanjiaView> list =(List<ZhuanjiaView>)page.getList();
        for(ZhuanjiaView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ZhuanjiaEntity zhuanjia = zhuanjiaService.selectById(id);
            if(zhuanjia !=null){


                //entity转view
                ZhuanjiaView view = new ZhuanjiaView();
                BeanUtils.copyProperties( zhuanjia , view );//把实体数据重构到view中

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
    public R add(@RequestBody ZhuanjiaEntity zhuanjia, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,zhuanjia:{}",this.getClass().getName(),zhuanjia.toString());
        Wrapper<ZhuanjiaEntity> queryWrapper = new EntityWrapper<ZhuanjiaEntity>()
            .eq("username", zhuanjia.getUsername())
            .or()
            .eq("zhuanjia_phone", zhuanjia.getZhuanjiaPhone())
            .or()
            .eq("zhuanjia_id_number", zhuanjia.getZhuanjiaIdNumber())
            .andNew()
            .eq("zhuanjia_delete", 1)
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhuanjiaEntity zhuanjiaEntity = zhuanjiaService.selectOne(queryWrapper);
        if(zhuanjiaEntity==null){
            zhuanjia.setZhuanjiaDelete(1);
            zhuanjia.setInsertTime(new Date());
            zhuanjia.setCreateTime(new Date());
        zhuanjia.setPassword("123456");
        zhuanjiaService.insert(zhuanjia);
            return R.ok();
        }else {
            return R.error(511,"账户或者医生手机号或者医生身份证号已经被使用");
        }
    }


}
