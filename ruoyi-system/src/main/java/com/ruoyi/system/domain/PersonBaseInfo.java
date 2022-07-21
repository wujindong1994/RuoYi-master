package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 person_base_info
 * 
 * @author ruoyi
 * @date 2022-07-13
 */
public class PersonBaseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long psId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 电话 */
    @Excel(name = "电话")
    private String phoneNum;

    /** 岗位类型 */
    @Excel(name = "岗位类型")
    private String jobType;

    /** 学历 */
    @Excel(name = "学历")
    private String education;

    /** 公司 */
    @Excel(name = "所属公司")
    private String company;

    /** 项目 */
    @Excel(name = "所属项目")
    private String projectName;

    /** 毕业时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "毕业时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date universityTime;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date upTime;

    public void setPsId(Long psId) 
    {
        this.psId = psId;
    }

    public Long getPsId() 
    {
        return psId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }
    public void setJobType(String jobType) 
    {
        this.jobType = jobType;
    }

    public String getJobType() 
    {
        return jobType;
    }
    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }
    public void setUniversityTime(Date universityTime) 
    {
        this.universityTime = universityTime;
    }

    public Date getUniversityTime() 
    {
        return universityTime;
    }
    public void setUpTime(Date upTime) 
    {
        this.upTime = upTime;
    }

    public Date getUpTime() 
    {
        return upTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("psId", getPsId())
            .append("name", getName())
            .append("sex", getSex())
            .append("phoneNum", getPhoneNum())
            .append("jobType", getJobType())
            .append("education", getEducation())
            .append("universityTime", getUniversityTime())
            .append("createTime", getCreateTime())
            .append("upTime", getUpTime())
                .append("company", getCompany())
                .append("projectName", getProjectName())
                .append("province", getProvince())
            .toString();
    }
}
