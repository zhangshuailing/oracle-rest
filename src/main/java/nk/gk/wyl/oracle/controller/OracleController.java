package nk.gk.wyl.oracle.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nk.gk.wyl.oracle.api.OracleActService;
import nk.gk.wyl.oracle.api.OracleGroupService;
import nk.gk.wyl.oracle.api.OracleSelectService;
import nk.gk.wyl.oracle.entity.result.Response;
import nk.gk.wyl.oracle.util.QueryUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * oracle  控制层
 */
@RestController
@RequestMapping("/rest/v1.0")
@Api(tags = "oracle 通用接口")
public class OracleController {

    @Autowired
    private OracleSelectService oracleSelectService;

    @Autowired
    private OracleActService oracleActService;

    @Autowired
    private OracleGroupService oracleGroupService;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增或编辑
     * @param table 表名
     * @param body 查询参数
     * @return
     */
    @PostMapping("{table}/saveOrUpdate")
    @ApiOperation(value = "新增或编辑")
    public @ResponseBody Response saveOrUpdate(@PathVariable("table") String table,@RequestBody Map<String,Object> body){
        try {
            if(!body.containsKey("saveOrUpdate")){
                return new Response().error("缺少【saveOrUpdate】参数");
            }
            Map<String,Object> saveOrUpdate = QueryUtil.checkMap(body,"saveOrUpdate");
            if(saveOrUpdate.containsKey("id")){
                return new Response().success(oracleActService.update(saveOrUpdate,sqlSessionTemplate,saveOrUpdate.get("id"),table,""));
            }else{
                return new Response().success(oracleActService.save(saveOrUpdate,sqlSessionTemplate,0,table,""));
            }
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 根据编号获取数据
     * @param table 表名
     * @param id 编号值
     * @param body 查询参数
     * @return
     */
    @PostMapping("{table}/{id}")
    @ApiOperation(value = "根据编号获取数据")
    public @ResponseBody Response findDataById(@PathVariable("table") String table,
                                               @PathVariable("id") Object id,
                                               @RequestBody Map<String,Object> body){
        try {
            String[] fields = QueryUtil.getMapArray(body,"fields");
            return new Response().success(oracleSelectService.findDataById(sqlSessionTemplate,
                    table,
                    id,
                    fields,
                    false,
                    "",
                    ""));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 根据编号获取数据
     * @param table 表名
     * @param id 编号值
     * @return
     */
    @GetMapping("{table}/{id}")
    @ApiOperation(value = "根据编号获取数据")
    public @ResponseBody Response findDataByIdGet(@PathVariable("table") String table,
                                               @PathVariable("id") Object id){
        try {
            return new Response().success(oracleSelectService.findDataById(sqlSessionTemplate,
                    table,
                    id,
                    null,
                    false,
                    "",
                    ""));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 列表查询
     * @param table 表名
     * @param body 查询参数
     * @return
     */
    @PostMapping("{table}/list")
    @ApiOperation(value = "列表接口")
    public @ResponseBody Response findList(@PathVariable("table") String table,@RequestBody Map<String,Object> body){
        try {
            String[] fields = QueryUtil.getMapArray(body,"fields");
            int size = QueryUtil.getIntValue(body,"size");
            return new Response().success(oracleSelectService.findList(body,sqlSessionTemplate,table,fields,size));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 分页列表
     * @param table 表名
     * @param body 查询参数
     * @return
     * @throws Exception
     */
    @PostMapping("{table}/page")
    @ApiOperation(value = "分页列表的接口")
    public @ResponseBody Response page(@PathVariable("table") String table,
                                    @RequestBody Map<String, Object> body) throws Exception {
        try {
            String[] fields = QueryUtil.getMapArray(body,"fields");
            return new Response().success(oracleSelectService.page(sqlSessionTemplate,table, body,fields));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 分组列表的接口
     * @param table 表名
     * @param body 查询参数
     * @return
     * @throws Exception
     */
    @PostMapping("{table}/group")
    @ApiOperation(value = "分组统计列表的接口")
    public @ResponseBody Response group(@PathVariable("table") String table,
                                       @RequestBody Map<String, Object> body) throws Exception {
        try {
            String[] fields = QueryUtil.checkArray(body,"keys");
            int size = QueryUtil.getIntValue(body,"size");
            return new Response().success(oracleGroupService.group(sqlSessionTemplate,body,table,fields,size));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 分组统计分页列表的接口
     * @param table 表名
     * @param body 查询参数
     * @return
     * @throws Exception
     */
    @PostMapping("{table}/groupPage")
    @ApiOperation(value = "分组统计列表的接口")
    public @ResponseBody Response groupPage(@PathVariable("table") String table,
                                        @RequestBody Map<String, Object> body) throws Exception {
        try {
            String[] fields = QueryUtil.checkArray(body,"keys");
            return new Response().success(oracleGroupService.groupPage(sqlSessionTemplate,body,table,fields));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 分组列表的接口
     * @param table 表名
     * @param body 查询参数
     * @return
     * @throws Exception
     */
    @PostMapping("{table}/groupSum")
    @ApiOperation(value = "分组求和统计列表的接口")
    public @ResponseBody Response groupSum(@PathVariable("table") String table,
                                        @RequestBody Map<String, Object> body) throws Exception {
        try {
            String[] fields = QueryUtil.checkArray(body,"keys");
            int size = QueryUtil.getIntValue(body,"size");
            String field = QueryUtil.checkValue(body,"sum_key");
            return new Response().success(oracleGroupService.groupSum(sqlSessionTemplate,body,table,fields,field,size));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 分组求和统计分页列表的接口
     * @param table 表名
     * @param body 查询参数
     * @return
     * @throws Exception
     */
    @PostMapping("{table}/groupPageSum")
    @ApiOperation(value = "分组求和统计列表的接口")
    public @ResponseBody Response groupPageSum(@PathVariable("table") String table,
                                            @RequestBody Map<String, Object> body) throws Exception {
        try {
            String[] fields = QueryUtil.checkArray(body,"keys");
            String field = QueryUtil.checkValue(body,"sum_key");
            return new Response().success(oracleGroupService.groupSumPage(sqlSessionTemplate,body,table,fields,field));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 获取表注释信息接口
     * @param table 表名
     * @return
     * @throws Exception
     */
    @GetMapping("{table}/comments")
    @ApiOperation(value = "获取表注释信息接口")
    public @ResponseBody Response comments(@PathVariable("table") String table) throws Exception {
        try {
            return new Response().success(oracleSelectService.getCommentsByTableName(sqlSessionTemplate,table));
        } catch (Exception e) {
            return new Response().error(e.getMessage());
        }
    }

}
