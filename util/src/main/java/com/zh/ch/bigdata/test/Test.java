package com.zh.ch.bigdata.test;

/**
 * @author xzc
 * @description
 * @date 2021/02/19
 */
public class Test {

    public static void main(String[] args) {
        String s = "{'transparentHugePage': '', 'hostHealth': {'agentTimeStampAtReporting': 1613722930257, 'liveServices': [{'status': 'Unhealthy', 'name': 'ntp or chrony', 'desc': 'ntp: unrecognized service\\nchrony: unrecognized service'}]}, 'reverseLookup': True, 'alternatives': [], 'hasUnlimitedJcePolicy': None, 'umask': '18', 'firewallName': 'ufw', 'stackFoldersAndFiles': [], 'existingUsers': [], 'firewallRunning': False}\n";

        System.out.println(s.replace("'", "\""));


    }

}
