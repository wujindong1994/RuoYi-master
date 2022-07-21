package com.ruoyi.web.controller.system;

import java.util.List;

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
import com.ruoyi.system.domain.Project;
import com.ruoyi.system.service.IProjectService;
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
@RequestMapping("/system/project")
public class ProjectController extends BaseController
{
    private String prefix = "system/project";

    @Autowired
    private IProjectService projectService;

    @RequiresPermissions("system:project:view")
    @GetMapping()
    public String project()
    {
        return prefix + "/project";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:project:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Project project)
    {
        startPage();
        //判断登录用户的省份
        SysUser sysUser = getSysUser();
        SysDept dept = sysUser.getDept();
        if(!"总部".equals(dept.getDeptName()) && dept.getDeptName() != null) {
            System.out.println("用户："+sysUser.getLoginName()+"的省份："+dept.getDeptName());
            project.setProvince(dept.getDeptName());
        }

        List<Project> list = projectService.selectProjectList(project);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:project:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Project project)
    {
        //判断登录用户的省份
        SysUser sysUser = getSysUser();
        SysDept dept = sysUser.getDept();
        if(!"总部".equals(dept.getDeptName()) && dept.getDeptName() != null) {
            System.out.println("用户："+sysUser.getLoginName()+"的省份："+dept.getDeptName());
            project.setProvince(dept.getDeptName());
        }
        List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:project:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Project project)
    {
        return toAjax(projectService.insertProject(project));
    }

    /**
     * 修改【请填写功能名称】
     */
    @RequiresPermissions("system:project:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Project project = projectService.selectProjectById(id);
        mmap.put("project", project);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:project:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Project project)
    {
        return toAjax(projectService.updateProject(project));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:project:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(projectService.deleteProjectByIds(ids));
    }

    /**
     * 导入【请填写功能名称】
     */
    @RequiresPermissions("system:project:importData")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean  updateSupport) throws Exception {
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        List<Project> infos = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        Long i = infos.stream().map(Project::getAboutId).distinct().count();

        if(i!=infos.size()){
            throw new RuntimeException("电话号码不可重复!");
        }
        logger.info("导入人"+operName);
        for (Project info: infos) {
            if(!RegexUtil.regexPhone(info.getAboutId())) {
                throw new RuntimeException(info.getName()+"的电话号码格式不正确!");
            }
            if(StringUtils.isEmpty(info.getAboutId())){
                throw new RuntimeException(info.getName()+"的电话号码不可为空!");
            }

            info.setCreateTime(DateUtils.getNowDate());
            projectService.insertProject(info);
        }

        return AjaxResult.success("导入成功!");
    }
    /**
     * 导入【请填写功能名称】
     */
    @RequiresPermissions("system:project:importTemplate")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @GetMapping( "/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        return util.importTemplateExcel("项目模板！");
    }
}
