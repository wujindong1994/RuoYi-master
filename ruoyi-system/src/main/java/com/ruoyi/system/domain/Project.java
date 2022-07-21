package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 project
 * 
 * @author ruoyi
 * @date 2022-07-13
 */
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 立项时间 */
    @Excel(name = "立项时间")
    private String  itemTime;

    /** 入场时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入场时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String admissionTime;

    /** 离场时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离场时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String leavTime;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String aboutId;

    @Excel(name = "人员姓名")
    private String personName;

    /** 从事组 */
    @Excel(name = "从事组")
    private String groupType;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date upTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAdmissionTime(String admissionTime)
    {
        this.admissionTime = admissionTime;
    }

    public String getAdmissionTime()
    {
        return admissionTime;
    }
    public void setLeavTime(String leavTime)
    {
        this.leavTime = leavTime;
    }

    public String getLeavTime()
    {
        return leavTime;
    }
    public void setAboutId(String aboutId)
    {
        this.aboutId = aboutId;
    }

    public String getAboutId()
    {
        return aboutId;
    }
    public void setGroupType(String groupType) 
    {
        this.groupType = groupType;
    }

    public String getGroupType() 
    {
        return groupType;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setUpTime(Date upTime) 
    {
        this.upTime = upTime;
    }

    public Date getUpTime() 
    {
        return upTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("admissionTime", getAdmissionTime())
            .append("leavTime", getLeavTime())
            .append("aboutId", getAboutId())
            .append("groupType", getGroupType())
            .append("province", getProvince())
            .append("createTime", getCreateTime())
            .append("upTime", getUpTime())
                .append("personName", getPersonName())
                .append("itemTime", getItemTime())
            .toString();
    }
}
