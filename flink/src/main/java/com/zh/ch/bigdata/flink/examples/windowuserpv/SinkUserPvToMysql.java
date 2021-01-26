package com.zh.ch.bigdata.flink.examples.windowuserpv;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author xzc
 * @description 输出到mysql
 * @date 2021/01/04
 */
public class SinkUserPvToMysql  extends RichSinkFunction<UserPvEntity> {

    private static final long serialVersionUID = -4443175430371919407L;

    PreparedStatement ps;

    private Connection connection;

    /**
     * open() 方法中建立连接，这样不用每次 invoke 的时候都要建立连接和释放连接
     *
     * @param parameters
     * @throws Exception
     */

    @Override

    public void open(Configuration parameters) throws Exception {

        super.open(parameters);

        connection = getConnection();

        String sql = "insert into flink_product_info_test(time, productId, productTotalSales) values(?, ?, ?);";

        ps = this.connection.prepareStatement(sql);

    }

    @Override

    public void close() throws Exception {

        super.close();

        //关闭连接和释放资源

        if (connection != null) {

            connection.close();

        }

        if (ps != null) {

            ps.close();

        }

    }

    /**
     * 每条数据的插入都要调用一次 invoke() 方法
     *
     * @param userPvEntity
     * @param context
     * @throws Exception
     */

    @Override

    public void invoke(UserPvEntity userPvEntity, Context context) throws Exception {

//组装数据，执行插入操作

        ps.setString(1, userPvEntity.getTime());

        ps.setString(2, userPvEntity.getUserId());

        ps.setLong(3, userPvEntity.getPvCount());

        ps.executeUpdate();

    }

    private static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://hadoop1:3306/flink_kafka_test", "root", "m98Edicines#");

        } catch (Exception e) {

            System.out.println("-----------mysql get connection has exception , msg = " + e.getMessage());

        }

        return con;

    }


}
