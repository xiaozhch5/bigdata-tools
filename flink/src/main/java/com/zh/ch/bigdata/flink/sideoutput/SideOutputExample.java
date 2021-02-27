package com.zh.ch.bigdata.flink.sideoutput;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * @author xzc
 * @description 测流输出示例
 * @date 2021/02/08
 */
public class SideOutputExample {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> input =
                env.readTextFile(
                        "C:\\Users\\hadoop\\company\\bigdata\\bigdata-2\\flink\\src\\main\\resources\\content.txt");

        OutputTag<String> outputTag = new OutputTag<String>("sideOutput") {};

        SingleOutputStreamOperator<Tuple2<String, Integer>> output =
                input.process(
                        new ProcessFunction<String, Tuple2<String, Integer>>() {
                            @Override
                            public void processElement(
                                    String s,
                                    Context context,
                                    Collector<Tuple2<String, Integer>> collector)
                                    throws Exception {
                                String[] words = s.split("\\W+");
                                for (String word : words) {
                                    if (word.length() < 5) {
                                        collector.collect(new Tuple2<>(word, 1));
                                    } else {
                                        context.output(outputTag, word);
                                    }
                                }
                            }
                        });

        DataStream<String> outsideOutput = output.getSideOutput(outputTag);

        outsideOutput.addSink(new FileSink());

        output.keyBy(0).sum(1).print();

        env.execute();
    }

    public static class FileSink extends RichSinkFunction<String> {
        private File FILE =
                new File(
                        "C:\\Users\\hadoop\\company\\bigdata\\bigdata-2\\flink\\src\\main\\resources\\words.txt");

        boolean flag;
        boolean flag1;
        Writer out;

        @Override
        public void open(Configuration parameters) throws Exception {
            if (FILE.exists()) {
                flag = FILE.delete();
            }
            flag1 = FILE.createNewFile();
            out = new FileWriter(FILE);
        }

        @Override
        public void invoke(String value, Context context) throws Exception {
            out.write(value);
            out.write("\n");
        }

        @Override
        public void close() throws Exception {
            out.close();
        }
    }
}
