package com.example.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateMysql {
    private final static Log logger = LogFactory.getLog(UpdateMysql.class);

    //连接mysql数据库
    public static  Connection getMysqlConnection(String url,String db ,String userName,String userPwd){
        String driverName = "com.mysql.cj.jdbc.Driver"; //加载JDBC驱动
        String dbURL = "jdbc:mysql://"+url+":3306/"+db+"?serverTimezone=GMT%2B8";
        userName = StringUtil.ObjectToString(userName);   //默认用户名
        userPwd = StringUtil.ObjectToString(userPwd);//密码

        Connection dbConn = null;
        try {
            if (!"".equals(userName) && !"".equals(userPwd)) {
                Class.forName(driverName);
                dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
                logger.info("Connection Successful!");   //如果连接成功 控制台输出Connection Successful!
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }

        return dbConn;
    }
    //查询数据库，获取数据集
    public static List excuteSql(String sql,Connection con ) throws SQLException{
        List rtnList = new ArrayList<Object>();
        Statement stat = null;
        ResultSet  set = null;
        try{
            if(null != sql && !"".equals(sql)){
                con.setAutoCommit(false);
                stat = con.createStatement();
                set = stat.executeQuery(sql.toString());
                con.commit();
                if(null != set){
                    ResultSetMetaData md = set.getMetaData();
                    int colCount  = md.getColumnCount();
                    if(colCount > 0){
                        while(set.next()){
                            Object[] tempArray = new Object[colCount];
                            for(int i = 0;i< tempArray.length;i++){
                                tempArray[i] = set.getObject(i+1);
                            }
                            rtnList.add(tempArray);
                        }
                    }

                }
            }
        }catch(Exception e){
            logger.info("执行sql获得结果集失败："+sql);
            logger.info(e.toString());
        }finally{
            if(null != stat){
                stat.close();
                set.close();
            }
        }
        return rtnList;
    }
    //增、删、改
    public static String updateSql(String sql,Connection con ) throws SQLException{
        String rtnStr = "0";
        Statement stat = null;
        try{
            if(null != sql && !"".equals(sql)){
                con.setAutoCommit(false);
                stat = con.createStatement();
                stat.executeUpdate(sql.toString());
                con.commit();
                con.setAutoCommit(true);
                rtnStr = "1";
            }
        }catch(Exception e){
            logger.info(e.toString());
        }finally{
            if(null != stat){
                stat.close();
            }
        }
        return rtnStr;
    }

    @Test
    public void test(){
        Connection connection= getMysqlConnection("localhost","mytest","admin","admin");
        if (connection !=null){
            System.out.println("--------连接成功--------");
            String sql="select * from t_s_user";
            try {
               List list = excuteSql(sql,connection);
                for (Object o:list) {
                    Object [] obj = (Object[])o;
                    System.out.println(obj[0]);
                }

                System.out.println(list.size());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("--------连接失败--------");
        }
    }


}

	