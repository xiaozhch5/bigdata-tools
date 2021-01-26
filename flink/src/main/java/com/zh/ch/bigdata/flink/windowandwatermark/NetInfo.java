package com.zh.ch.bigdata.flink.windowandwatermark;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class NetInfo {

    private String ip;

    private String netSpeed;

    private Long timeStamp;
}
