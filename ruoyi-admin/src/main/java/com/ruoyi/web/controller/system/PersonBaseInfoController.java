package com.ruoyi.web.controller.system;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RegexUtil;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PersonBaseInfo;
import com.ruoyi.system.service.IPersonBaseInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-07-13
 */
@Controller
@RequestMapping("/system/info")
public class PersonBaseInfoController extends BaseController
{
    private String prefix = "system/info";

    @Autowired
    private IPersonBaseInfoService personBaseInfoService;

    @RequiresPermissions("system:info:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PersonBaseInfo personBaseInfo)
    {
        startPage();
        //判断登录用户的省份
        SysUser sysUser = getSysUser();
        SysDept dept = sysUser.getDept();
        if(!"总部".equals(dept.getDeptName()) && dept.getDeptName() != null) {
            System.out.println("用户："+sysUser.getLoginName()+"的省份："+dept.getDeptName());
            personBaseInfo.setProvince(dept.getDeptName());
        }
        List<PersonBaseInfo> list = personBaseInfoService.selectPersonBaseInfoList(personBaseInfo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:info:export")
    @Log(title = "【人员信息】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PersonBaseInfo personBaseInfo)
    {
        //判断登录用户的省份
        SysUser sysUser = getSysUser();
        SysDept dept = sysUser.getDept();
        if(!"总部".equals(dept.getDeptName()) && dept.getDeptName() != null) {
            System.out.println("用户："+sysUser.getLoginName()+"的省份："+dept.getDeptName());
            personBaseInfo.setProvince(dept.getDeptName());
        }
        List<PersonBaseInfo> list = personBaseInfoService.selectPersonBaseInfoList(personBaseInfo);
        ExcelUtil<PersonBaseInfo> util = new ExcelUtil<PersonBaseInfo>(PersonBaseInfo.class);
        return util.exportExcel(list, "【人员信息】数据");
    }

    /**
     * 新增【人员信息】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【人员信息】
     */
    @RequiresPermissions("system:info:add")
    @Log(title = "【人员信息】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PersonBaseInfo personBaseInfo)
    {
        //新增人员信息
        return toAjax(personBaseInfoService.insertPersonBaseInfo(personBaseInfo));
    }

    /**
     * 修改【人员信息】
     */
    @RequiresPermissions("system:info:edit")
    @GetMapping("/edit/{psId}")
    public String edit(@PathVariable("psId") Long psId, ModelMap mmap)
    {
        PersonBaseInfo personBaseInfo = personBaseInfoService.selectPersonBaseInfoByPsId(psId);
        System.out.println(JSONObject.toJSON(personBaseInfo));
        mmap.put("personBaseInfo", personBaseInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存【人员信息】
     */
    @RequiresPermissions("system:info:edit")
    @Log(title = "【人员信息】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PersonBaseInfo personBaseInfo)
    {
        return toAjax(personBaseInfoService.updatePersonBaseInfo(personBaseInfo));
    }

    /**
     * 删除【人员信息】
     */
    @RequiresPermissions("system:info:remove")
    @Log(title = "【人员信息】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(personBaseInfoService.deletePersonBaseInfoByPsIds(ids));
    }


    /**
     * 导入【请填写功能名称】
     */
    @RequiresPermissions("system:info:importData")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean  updateSupport) throws Exception {
        ExcelUtil<PersonBaseInfo> util = new ExcelUtil<PersonBaseInfo>(PersonBaseInfo.class);
        List<PersonBaseInfo> infos = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        Long i = infos.stream().map(PersonBaseInfo::getPhoneNum).distinct().count();
        if(i!=infos.size()){
            throw new RuntimeException("电话号码不可重复!");
        }
        logger.info("导入人"+operName);
        for (PersonBaseInfo info: infos) {
            if(!RegexUtil.regexPhone(info.getPhoneNum())) {
                throw new RuntimeException(info.getName()+"的电话号码格式不正确!");
            }
            if(StringUtils.isEmpty(info.getPhoneNum())){
                throw new RuntimeException(info.getName()+"的电话号码不可为空!");
            }
            info.setCreateTime(DateUtils.getNowDate());
            personBaseInfoService.insertPersonBaseInfo(info);
        }

        return AjaxResult.success("导入成功!");
    }
    /**
     * 导入【请填写功能名称】
     */
    @RequiresPermissions("system:info:importTemplate")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @GetMapping( "/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<PersonBaseInfo> util = new ExcelUtil<PersonBaseInfo>(PersonBaseInfo.class);
        return util.importTemplateExcel("人员模板");
    }

}
