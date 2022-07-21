package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PersonBaseInfo;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-13
 */
public interface PersonBaseInfoMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param psId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public PersonBaseInfo selectPersonBaseInfoByPsId(Long psId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param personBaseInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<PersonBaseInfo> selectPersonBaseInfoList(PersonBaseInfo personBaseInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param personBaseInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertPersonBaseInfo(PersonBaseInfo personBaseInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param personBaseInfo 【请填写功能名称】
     * @return 结果
     */
    public int updatePersonBaseInfo(PersonBaseInfo personBaseInfo);

    /**
     * 删除【请填写功能名称】
     * 
     * @param psId 【请填写功能名称】主键
     * @return 结果
     */
    public int deletePersonBaseInfoByPsId(Long psId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param psIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonBaseInfoByPsIds(String[] psIds);
}
