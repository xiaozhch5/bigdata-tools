package com.zh.ch.bigdata.pulsar;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.Objects;

/**
 *  Pulsar客户端
 * @author xzc
 * @date 2020/09/29
 */
public class MyPulsarClient {

    private String pulsarConfigPropertiesFilePath = null;

    public MyPulsarClient() { }

    public MyPulsarClient(String pulsarConfigPropertiesFilePath) {
        this.pulsarConfigPropertiesFilePath = pulsarConfigPropertiesFilePath;
    }

    /**
     * 获取resources文件路径
     * @param fileName 文件名
     * @return 文件路径
     */
    public String getResource(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).toString().substring(6);
    }

    public PulsarClient build() {
        if (pulsarConfigPropertiesFilePath == null) {
//            getResource()
        }
        return null;

    }


    public static void main(String[] args) throws PulsarClientException {
        String localClusterUrl = "pulsar://10.45.46.116:6650";
        PulsarClient client = PulsarClient.builder().serviceUrl(localClusterUrl).build();
        Producer<byte[]> producer = client.newProducer().topic("my-topic").create();
    }
}
