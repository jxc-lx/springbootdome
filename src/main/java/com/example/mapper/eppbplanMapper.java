package com.example.mapper;

import com.example.entity.eppbplan;

public interface eppbplanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ep_pb_plan
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ep_pb_plan
     *
     * @mbggenerated
     */
    int insert(eppbplan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ep_pb_plan
     *
     * @mbggenerated
     */
    int insertSelective(eppbplan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ep_pb_plan
     *
     * @mbggenerated
     */
    eppbplan selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ep_pb_plan
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(eppbplan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ep_pb_plan
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(eppbplan record);
}