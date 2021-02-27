package com.zh.ch.bigdata.kafka.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import kafka.admin.ConsumerGroupCommand;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzc
 * @description kafka命令行、java工具，用于获取kafka的offset
 * @date 2021/02/05
 */
public class KafkaCommandUtil {

    public static void main(String[] args) {
        Long a = KafkaCommandUtil.getKafkaTopicSumOffsetWithConsumerGroup("10.45.46.116:9592", "un_account_topic", "zj_un_account_group");
        System.out.println(a);
    }

    public static List<PartitionAssignmentState> getCollectGroupOffsets(String brokerServer, String group) {
        return getCollectGroupOffsets(brokerServer, null, group);
    }

    public static List<PartitionAssignmentState> getCollectGroupOffsets(String brokerServer, String topic, String group) {
        List<PartitionAssignmentState> result = null;
//        String[] agrs = {"--describe", "--bootstrap-server", brokerServer, "--group", group, "--topic", topic};
        List<String> agrList = new ArrayList<>();
        agrList.add("--describe");
        agrList.add("--bootstrap-server");
        agrList.add(brokerServer);
        if (group != null) {
            agrList.add("--group");
            agrList.add(group);
        }
        if (topic != null) {
            agrList.add("--topic");
            agrList.add(topic);
        }
        String[] agrs = agrList.toArray(new String[]{});
        ConsumerGroupCommand.ConsumerGroupCommandOptions options =
                new ConsumerGroupCommand.ConsumerGroupCommandOptions(agrs);

        ConsumerGroupCommand.ConsumerGroupService consumerGroupService = new ConsumerGroupCommand.ConsumerGroupService(options);

        ObjectMapper mapper = new ObjectMapper();
        //1. 使用jackson-module-scala_2.12
        mapper.registerModule(new DefaultScalaModule());
        //2. 反序列化时忽略对象不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        //3. 将Scala对象序列化成JSON字符串
        String source;
        try {
            source = mapper.writeValueAsString(consumerGroupService.collectGroupOffsets()._2.get());
            //4. 将JSON字符串反序列化成Java对象
            result = mapper.readValue(source,
                    getCollectionType(mapper,List.class,PartitionAssignmentState.class));
        } catch (Exception e) {
        }
        return result;
    }
    public static Long getKafkaTopicSumOffsetWithConsumerGroup(String brokerServer, String topic, String group) {
        long result = 0L;
        List<PartitionAssignmentState> collectGroupOffsets = getCollectGroupOffsets(brokerServer, topic, group);
        if (CollectionUtils.isNotEmpty(collectGroupOffsets)) {
            for (PartitionAssignmentState collectGroupOffset : collectGroupOffsets) {
                result += collectGroupOffset.getOffset();
            }
        }
        return result;
    }

    public static Long getKafkaTopicSumLagWithConsumerGroup(String brokerServer, String topic, String group) {
        long result = 0L;
        List<PartitionAssignmentState> collectGroupOffsets = getCollectGroupOffsets(brokerServer, topic, group);
        if (CollectionUtils.isNotEmpty(collectGroupOffsets)) {
            for (PartitionAssignmentState collectGroupOffset : collectGroupOffsets) {
                result += collectGroupOffset.getLag();
            }
        }
        return result;
    }
    public static JavaType getCollectionType(ObjectMapper mapper,
                                             Class<?> collectionClass,
                                             Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
