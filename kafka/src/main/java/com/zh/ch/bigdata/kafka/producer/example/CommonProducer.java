package com.zh.ch.bigdata.kafka.producer.example;

import com.zh.ch.bigdata.base.util.java.PropertiesAnalyzeUtil;
import com.zh.ch.bigdata.base.util.java.RandomStringUtil;
import com.zh.ch.bigdata.kafka.util.AbstractKafkaProducer;
import com.zh.ch.bigdata.kafka.util.KafkaClient;
import com.zh.ch.bigdata.kafka.util.KafkaConfigEnv;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Objects;

/**
 * @author xzc
 * @description 生成audit数据并发送到kafka指定分区上
 * @date 2021/01/09
 */
@Setter
@Getter
public class CommonProducer extends AbstractKafkaProducer {

    /**
     * length 发送的总条数
     */
    private Long length;

    /**
     * count length % count == 0 时，产生一条key为audit的数据
     */
    private Long count;

    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toString().substring(6);
    }

    public CommonProducer(Long length, Long count) {
        this.length = length;
        this.count = count;
    }

    public CommonProducer(){

    }

    /**
     * 执行kafka消息发送
     *
     * @throws Exception 异常
     */
    @Override
    public void execute() throws Exception {
        Producer<String, String> producer = KafkaClient.buildProducer(kafkaProducerConfigFilePath);
        String topicName = "allintopic";
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "{\"table\":\"BSS.ACCOUNT\",\"op_type\":\"I\",\"op_ts\":\"2021-01-09 05:42:52.030267\",\"current_ts\":\"2021-01-09T13:42:59.105000\",\"pos\":\"00000000010000007345\",\"primary_keys\":[\"ACCT_ID\"],\"tokens\":{\"tkscn\":\"16631753857499\"},\"after\":{\"ACCT_ID\":\"88888865\",\"ACCT_NAME\":\"安徽的张三\",\"CUST_ID\":\"9999999\",\"ACCT_LOGIN_NAME\":\"安徽的张三\",\"LOGIN_PASSWORD\":null,\"EFF_DATE\":null,\"EXP_DATE\":null,\"STATUS_CD\":\"1000\",\"STATUS_DATE\":\"2021-01-01:00:00:00\",\"CREATE_STAFF\":null}}");


//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "{\"table\":\"BSS.ACCOUNT\",\"op_type\":\"I\",\"op_ts\":\"2021-01-09 05:42:52.030267\",\"current_ts\":\"2021-01-09T13:42:59.105000\",\"pos\":\"00000000010000007345\",\"primary_keys\":[\"ACCT_ID\"],\"tokens\":{\"tkscn\":\"16631753857499\"},\"after\":{\"ACCT_ID\":\"88888865\",\"ACCT_NAME\":\"安徽的张三\",\"CUST_ID\":\"9999999\",\"ACCT_LOGIN_NAME\":\"安徽的张三\",\"LOGIN_PASSWORD\":null,\"EFF_DATE\":null,\"EXP_DATE\":null,\"STATUS_CD\":\"1000\",\"STATUS_DATE\":\"2021-01-01:00:00:00\",\"CREATE_STAFF\":null}}\n");

//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName,  "{\"table\":\"BSS.ACCOUNT\",\"op_type\":\"I\",\"op_ts\":\"2021-01-09 05:42:52.030267\",\"current_ts\":\"2021-01-09T13:42:59.105000\",\"pos\":\"00000000010000007345\",\"primary_keys\":[\"ACCT_ID\"],\"tokens\":{\"tkscn\":\"16631753857599\"},\"after\":{\"ACCT_ID\":\"88888866\",\"ACCT_NAME\":\"广东的李四\",\"CUST_ID\":\"9999999\",\"ACCT_LOGIN_NAME\":\"广东的李四\",\"LOGIN_PASSWORD\":null,\"EFF_DATE\":null,\"EXP_DATE\":null,\"STATUS_CD\":\"1000\",\"STATUS_DATE\":\"2021-01-01:00:00:00\",\"CREATE_STAFF\":null}}");

//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "{\"table\":\"BSS.CUSTOMER\",\"op_type\":\"I\",\"op_ts\":\"2021-01-16 11:27:21.097890\",\"current_ts\":\"2021-01-16T19:27:25.572001\",\"pos\":\"00000000000000435643\",\"primary_keys\":[\"CUST_ID\"],\"tokens\":{\"tkscn\":\"16631758313732\"},\"after\":{\"CUST_ID\":\"500208\",\"CREDIT_ID\":\"11111000060\",\"SERVICE_GRADE_ID\":\"4\",\"CREDIT_LIMIT_ID\":\"148\",\"INDIVIDUAL_ID\":\"100\",\"INDIVIDUAL_ROLE\":\"101\",\"PARTY_ID\":\"0\",\"TAX_PAYER_ID\":\"0\",\"CUST_NAME\":\"name1000060\",\"CUST_NUMBER\":\"CODE1000060\",\"CUST_ADDR\":\"ADDR1000060\",\"REGION_ID\":\"145\",\"CUST_NO\":\"NO1000060\",\"CUST_PWD\":\"PWD145\",\"CUST_SOURCE_ID\":\"109\",\"CUST_AREA_GRADE\":\"178\",\"ENTER_DATE\":\"2015-01-01:00:00:00\",\"IS_REALNAME\":\"1\",\"CUST_DETAIL\":\"CUST_DETAIL\",\"STATUS_CD\":\"11\",\"STATUS_DATE\":\"2021-01-01:00:00:00\",\"CREATE_STAFF\":\"100\",\"CREATE_DATE\":\"2021-01-02:00:00:00\",\"UPDATE_STAFF\":\"100\",\"UPDATE_DATE\":\"2021-01-02:00:00:00\",\"REMARK\":\"note information\"}}\n");

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "{\"table\":\"BSS.ACCOUNT\",\"op_type\":\"I\",\"op_ts\":\"2021-01-09 05:42:52.030267\",\"current_ts\":\"2021-01-09T13:42:59.105000\",\"pos\":\"00000000010000007345\",\"primary_keys\":[\"ACCT_ID\"],\"tokens\":{\"tkscn\":\"16631753857599\"},\"after\":{\"ACCT_ID\":\"100\",\"ACCT_NAME\":\"广东的李四\",\"CUST_ID\":\"100\",\"ACCT_LOGIN_NAME\":\"广东的李四\",\"LOGIN_PASSWORD\":null,\"EFF_DATE\":null,\"EXP_DATE\":null,\"STATUS_CD\":\"1000\",\"STATUS_DATE\":\"2021-01-01:00:00:00\",\"CREATE_STAFF\":null}}\n");
        producer.send(producerRecord);
        System.out.println("成功发送一条kafka数据"+ producer.send(producerRecord).isDone());
    }

    public static void main(String[] args) throws Exception {
        CommonProducer commonProducer = new CommonProducer();
        commonProducer.setKafkaProducerConfigFilePath(commonProducer.getResource("kafka-producer-config.properties"));
        commonProducer.setLength(100L);
        commonProducer.setCount(1L);
        commonProducer.setSleepTime(100L);
        commonProducer.execute();
    }
}
