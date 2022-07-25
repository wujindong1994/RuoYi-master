package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RegexUtil;
import com.ruoyi.system.domain.Project;
import com.ruoyi.system.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PersonBaseInfoMapper;
import com.ruoyi.system.domain.PersonBaseInfo;
import com.ruoyi.system.service.IPersonBaseInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-13
 */
@Service
public class PersonBaseInfoServiceImpl implements IPersonBaseInfoService 
{
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param psId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public PersonBaseInfo selectPersonBaseInfoByPsId(Long psId)
    {
        return personBaseInfoMapper.selectPersonBaseInfoByPsId(psId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param personBaseInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<PersonBaseInfo> selectPersonBaseInfoList(PersonBaseInfo personBaseInfo)
    {
        List<PersonBaseInfo> personBaseInfoLists = null;
        List<PersonBaseInfo> personBaseInfoList=  personBaseInfoMapper.selectPersonBaseInfoList(personBaseInfo);
        personBaseInfoLists = personBaseInfoList;
        if(personBaseInfoList.size()>0) {
            for(int i=0; i < personBaseInfoList.size();i++) {
                PersonBaseInfo personBaseInfo1 = personBaseInfoList.get(i);
                if(personBaseInfo1.getPhoneNum() != null && !"".equals(personBaseInfo1.getPhoneNum())) {
                    Project project = new Project();
                    project.setAboutId(personBaseInfo1.getPhoneNum());
                    List<Project> projectList =  projectMapper.selectProjectList(project);
                    if(projectList.size() >0 ) {
                        project = projectList.get(0);
                        personBaseInfo1.setProjectName(project.getName());
;                    }
                    personBaseInfoLists.remove(i);
                    personBaseInfoLists.add(i,personBaseInfo1);
                }
            }
        }
        return personBaseInfoLists;
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param personBaseInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertPersonBaseInfo(PersonBaseInfo personBaseInfo){

        PersonBaseInfo personBaseInfos = new PersonBaseInfo();
        if(personBaseInfo.getPhoneNum()!=null){
            if(!RegexUtil.regexPhone(personBaseInfo.getPhoneNum())){
                throw new RuntimeException("请确认手机号是否正确!");
            }

            personBaseInfos.setPhoneNum(personBaseInfo.getPhoneNum());
        }
        if(personBaseInfoMapper.selectPersonBaseInfoList(personBaseInfos).size()!=0){
            throw new RuntimeException("不可重复电话号码!");
        }
        personBaseInfo.setCreateTime(DateUtils.getNowDate());
        return personBaseInfoMapper.insertPersonBaseInfo(personBaseInfo);
    }

    /**
     * 修改【人员信息】
     * 
     * @param personBaseInfo 【人员信息】
     * @return 结果
     */
    @Override
    public int updatePersonBaseInfo(PersonBaseInfo personBaseInfo)
    {

        PersonBaseInfo personBaseInfos = new PersonBaseInfo();
        if(!RegexUtil.regexPhone(personBaseInfo.getPhoneNum())){
            throw new RuntimeException("请确认手机号是否正确!");
        }

        personBaseInfos.setPhoneNum(personBaseInfo.getPhoneNum());
        List<PersonBaseInfo> personBaseInfoList = personBaseInfoMapper.selectPersonBaseInfoList(personBaseInfos);
        if(personBaseInfoList.size()>0){
            if(personBaseInfo.getPsId()  != personBaseInfoList.get(0).getPsId()) {
                throw new RuntimeException("不可重复录入电话号码!");
            }
        }

        //同步更新项目信息的人员、手机号
        Project project = new Project();
        PersonBaseInfo personBaseInfoLists = personBaseInfoMapper.selectPersonBaseInfoByPsId(personBaseInfo.getPsId().longValue());
        project.setAboutId(personBaseInfoLists.getPhoneNum());
        List<Project> projectList =  projectMapper.selectProjectList(project);
        if(projectList.size() > 0 ) {
            for(int i = 0; i < projectList.size(); i++) {
                Project projects =  projectList.get(i);
                projects.setPersonName(personBaseInfo.getName());
                projects.setAboutId(personBaseInfo.getPhoneNum());
                projectMapper.updateProject(projects);
            }
        }

        personBaseInfo.setUpdateTime(DateUtils.getNowDate());
        return personBaseInfoMapper.updatePersonBaseInfo(personBaseInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param psIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePersonBaseInfoByPsIds(String psIds)
    {
        return personBaseInfoMapper.deletePersonBaseInfoByPsIds(Convert.toStrArray(psIds));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param psId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deletePersonBaseInfoByPsId(Long psId)
    {
        return personBaseInfoMapper.deletePersonBaseInfoByPsId(psId);
    }
}
