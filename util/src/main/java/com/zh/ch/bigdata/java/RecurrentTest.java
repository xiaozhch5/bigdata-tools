package com.zh.ch.bigdata.java;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xzc
 * @description 递归测试
 * @date 2021/01/14
 */
public class RecurrentTest {



    public static void main(String[] args) {
        String str = " [\n" +
                "    {\n" +
                "      \"position\": {\n" +
                "        \"x\": 580,\n" +
                "        \"y\": 60\n" +
                "      },\n" +
                "      \"shape\": \"REAL_DATASOURCE\",\n" +
                "      \"data\": {\n" +
                "        \"type\": \"REAL_DATASOURCE\",\n" +
                "        \"name\": \"实时数据源\",\n" +
                "        \"fill\": \"#FFF0F6\",\n" +
                "        \"borderColor\": \"#F759AB\",\n" +
                "        \"border\": 18,\n" +
                "        \"properties\": [],\n" +
                "        \"id\": \"867798e3-aa47-46d6-9ae2-2e9a675eaab1\",\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"id\": \"867798e3-aa47-46d6-9ae2-2e9a675eaab1\",\n" +
                "      \"type\": \"REAL_DATASOURCE\",\n" +
                "      \"name\": \"实时数据源\",\n" +
                "      \"fill\": \"#FFF0F6\",\n" +
                "      \"borderColor\": \"#F759AB\",\n" +
                "      \"border\": 18,\n" +
                "      \"properties\": [],\n" +
                "      \"zIndex\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"position\": {\n" +
                "        \"x\": 580,\n" +
                "        \"y\": 170\n" +
                "      },\n" +
                "      \"shape\": \"SOURCE_TABLE\",\n" +
                "      \"data\": {\n" +
                "        \"name\": \"117goldendb-bssmodal\",\n" +
                "        \"type\": \"SOURCE_TABLE\",\n" +
                "        \"fill\": \"#FFFBE6\",\n" +
                "        \"borderColor\": \"#FAAD14\",\n" +
                "        \"properties\": [\n" +
                "          {\n" +
                "            \"name\": \"ACCT_ID\",\n" +
                "            \"type\": \"double\",\n" +
                "            \"seq\": \"1\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"ACCT_NAME\",\n" +
                "            \"type\": \"string\",\n" +
                "            \"seq\": \"2\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"CUST_ID\",\n" +
                "            \"type\": \"double\",\n" +
                "            \"seq\": \"3\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"ACCT_LOGIN_NAME\",\n" +
                "            \"type\": \"string\",\n" +
                "            \"seq\": \"4\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"LOGIN_PASSWORD\",\n" +
                "            \"type\": \"string\",\n" +
                "            \"seq\": \"5\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"EFF_DATE\",\n" +
                "            \"type\": \"date\",\n" +
                "            \"seq\": \"6\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"EXP_DATE\",\n" +
                "            \"type\": \"date\",\n" +
                "            \"seq\": \"7\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"STATUS_CD\",\n" +
                "            \"type\": \"double\",\n" +
                "            \"seq\": \"8\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"STATUS_DATE\",\n" +
                "            \"type\": \"date\",\n" +
                "            \"seq\": \"9\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"CREATE_STAFF\",\n" +
                "            \"type\": \"double\",\n" +
                "            \"seq\": \"10\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"CREATE_DATE\",\n" +
                "            \"type\": \"date\",\n" +
                "            \"seq\": \"11\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"UPDATE_STAFF\",\n" +
                "            \"type\": \"double\",\n" +
                "            \"seq\": \"12\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"UPDATE_DATE\",\n" +
                "            \"type\": \"date\",\n" +
                "            \"seq\": \"13\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"REMARK\",\n" +
                "            \"type\": \"string\",\n" +
                "            \"seq\": \"14\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"id\": \"e12803fd-f6ab-4864-a4b9-db2c6351d628\",\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"id\": \"e12803fd-f6ab-4864-a4b9-db2c6351d628\",\n" +
                "      \"type\": \"SOURCE_TABLE\",\n" +
                "      \"name\": \"117goldendb-bssmodal\",\n" +
                "      \"fill\": \"#FFFBE6\",\n" +
                "      \"borderColor\": \"#FAAD14\",\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"name\": \"ACCT_ID\",\n" +
                "          \"type\": \"double\",\n" +
                "          \"seq\": \"1\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"ACCT_NAME\",\n" +
                "          \"type\": \"string\",\n" +
                "          \"seq\": \"2\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"CUST_ID\",\n" +
                "          \"type\": \"double\",\n" +
                "          \"seq\": \"3\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"ACCT_LOGIN_NAME\",\n" +
                "          \"type\": \"string\",\n" +
                "          \"seq\": \"4\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"LOGIN_PASSWORD\",\n" +
                "          \"type\": \"string\",\n" +
                "          \"seq\": \"5\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"EFF_DATE\",\n" +
                "          \"type\": \"date\",\n" +
                "          \"seq\": \"6\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"EXP_DATE\",\n" +
                "          \"type\": \"date\",\n" +
                "          \"seq\": \"7\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"STATUS_CD\",\n" +
                "          \"type\": \"double\",\n" +
                "          \"seq\": \"8\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"STATUS_DATE\",\n" +
                "          \"type\": \"date\",\n" +
                "          \"seq\": \"9\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"CREATE_STAFF\",\n" +
                "          \"type\": \"double\",\n" +
                "          \"seq\": \"10\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"CREATE_DATE\",\n" +
                "          \"type\": \"date\",\n" +
                "          \"seq\": \"11\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"UPDATE_STAFF\",\n" +
                "          \"type\": \"double\",\n" +
                "          \"seq\": \"12\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"UPDATE_DATE\",\n" +
                "          \"type\": \"date\",\n" +
                "          \"seq\": \"13\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"REMARK\",\n" +
                "          \"type\": \"string\",\n" +
                "          \"seq\": \"14\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"zIndex\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"position\": {\n" +
                "        \"x\": 580,\n" +
                "        \"y\": 360\n" +
                "      },\n" +
                "      \"shape\": \"PARSE_RULE\",\n" +
                "      \"data\": {\n" +
                "        \"name\": \"解析逻辑\",\n" +
                "        \"type\": \"PARSE_RULE\",\n" +
                "        \"fill\": \"#E6F7FF\",\n" +
                "        \"borderColor\": \"#029DFF\",\n" +
                "        \"properties\": [],\n" +
                "        \"id\": \"4496de84-f010-4f37-9791-bd5cd56870fe\",\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"id\": \"4496de84-f010-4f37-9791-bd5cd56870fe\",\n" +
                "      \"type\": \"PARSE_RULE\",\n" +
                "      \"name\": \"解析逻辑\",\n" +
                "      \"fill\": \"#E6F7FF\",\n" +
                "      \"borderColor\": \"#029DFF\",\n" +
                "      \"properties\": [],\n" +
                "      \"zIndex\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"position\": {\n" +
                "        \"x\": 580,\n" +
                "        \"y\": 453\n" +
                "      },\n" +
                "      \"shape\": \"ENCRYPT_RULE\",\n" +
                "      \"data\": {\n" +
                "        \"name\": \"数据加密\",\n" +
                "        \"type\": \"ENCRYPT_RULE\",\n" +
                "        \"fill\": \"#FFF7E6\",\n" +
                "        \"borderColor\": \"#FFA940\",\n" +
                "        \"properties\": [],\n" +
                "        \"id\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"id\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "      \"type\": \"ENCRYPT_RULE\",\n" +
                "      \"name\": \"数据加密\",\n" +
                "      \"fill\": \"#FFF7E6\",\n" +
                "      \"borderColor\": \"#FFA940\",\n" +
                "      \"properties\": [],\n" +
                "      \"zIndex\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"position\": {\n" +
                "        \"x\": 330,\n" +
                "        \"y\": 530\n" +
                "      },\n" +
                "      \"shape\": \"RESTORE_RULE\",\n" +
                "      \"data\": {\n" +
                "        \"name\": \"还原数库表\",\n" +
                "        \"type\": \"RESTORE_RULE\",\n" +
                "        \"fill\": \"#BAE7FF\",\n" +
                "        \"borderColor\": \"#69C0FF\",\n" +
                "        \"properties\": [],\n" +
                "        \"id\": \"1feb5249-032a-4c93-b499-15685b16773d\",\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"id\": \"1feb5249-032a-4c93-b499-15685b16773d\",\n" +
                "      \"type\": \"RESTORE_RULE\",\n" +
                "      \"name\": \"还原数库表\",\n" +
                "      \"fill\": \"#BAE7FF\",\n" +
                "      \"borderColor\": \"#69C0FF\",\n" +
                "      \"properties\": [],\n" +
                "      \"zIndex\": 5\n" +
                "    },\n" +
                "    {\n" +
                "      \"position\": {\n" +
                "        \"x\": 744,\n" +
                "        \"y\": 530\n" +
                "      },\n" +
                "      \"shape\": \"MERGE_RULE\",\n" +
                "      \"data\": {\n" +
                "        \"name\": \"归并规则\",\n" +
                "        \"type\": \"MERGE_RULE\",\n" +
                "        \"fill\": \"#F6FFED\",\n" +
                "        \"borderColor\": \"#52C41A\",\n" +
                "        \"properties\": [],\n" +
                "        \"id\": \"cc24692b-d1e3-4294-8179-3e5b1b4c093f\",\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"id\": \"cc24692b-d1e3-4294-8179-3e5b1b4c093f\",\n" +
                "      \"type\": \"MERGE_RULE\",\n" +
                "      \"name\": \"归并规则\",\n" +
                "      \"fill\": \"#F6FFED\",\n" +
                "      \"borderColor\": \"#52C41A\",\n" +
                "      \"properties\": [],\n" +
                "      \"zIndex\": 6\n" +
                "    },\n" +
                "    {\n" +
                "      \"position\": {\n" +
                "        \"x\": 650,\n" +
                "        \"y\": 640\n" +
                "      },\n" +
                "      \"shape\": \"TARGET_TABLE\",\n" +
                "      \"data\": {\n" +
                "        \"name\": \"目标据源表\",\n" +
                "        \"type\": \"TARGET_TABLE\",\n" +
                "        \"fill\": \"#FFCCC7\",\n" +
                "        \"borderColor\": \"#FFA39E\",\n" +
                "        \"properties\": [],\n" +
                "        \"id\": \"3e3ad473-4668-4fa4-8f7c-0137ac6b68ae\",\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"id\": \"3e3ad473-4668-4fa4-8f7c-0137ac6b68ae\",\n" +
                "      \"type\": \"TARGET_TABLE\",\n" +
                "      \"name\": \"目标据源表\",\n" +
                "      \"fill\": \"#FFCCC7\",\n" +
                "      \"borderColor\": \"#FFA39E\",\n" +
                "      \"properties\": [],\n" +
                "      \"zIndex\": 7\n" +
                "    },\n" +
                "    {\n" +
                "      \"shape\": \"myedge\",\n" +
                "      \"id\": \"7e57dd62-4c84-4441-a7e5-3ad42942dd3c\",\n" +
                "      \"data\": {\n" +
                "        \"id\": \"7e57dd62-4c84-4441-a7e5-3ad42942dd3c\",\n" +
                "        \"type\": \"myedge\",\n" +
                "        \"source\": {\n" +
                "          \"cell\": \"867798e3-aa47-46d6-9ae2-2e9a675eaab1\",\n" +
                "          \"port\": \"bottom\"\n" +
                "        },\n" +
                "        \"target\": {\n" +
                "          \"cell\": \"e12803fd-f6ab-4864-a4b9-db2c6351d628\",\n" +
                "          \"port\": \"top\"\n" +
                "        },\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"type\": \"myedge\",\n" +
                "      \"source\": {\n" +
                "        \"cell\": \"867798e3-aa47-46d6-9ae2-2e9a675eaab1\",\n" +
                "        \"port\": \"bottom\"\n" +
                "      },\n" +
                "      \"target\": {\n" +
                "        \"cell\": \"e12803fd-f6ab-4864-a4b9-db2c6351d628\",\n" +
                "        \"port\": \"top\"\n" +
                "      },\n" +
                "      \"zIndex\": 8\n" +
                "    },\n" +
                "    {\n" +
                "      \"shape\": \"myedge\",\n" +
                "      \"id\": \"5c0bfd5a-fab2-429c-90e2-d7adf69101c8\",\n" +
                "      \"data\": {\n" +
                "        \"id\": \"5c0bfd5a-fab2-429c-90e2-d7adf69101c8\",\n" +
                "        \"type\": \"myedge\",\n" +
                "        \"source\": {\n" +
                "          \"cell\": \"e12803fd-f6ab-4864-a4b9-db2c6351d628\",\n" +
                "          \"port\": \"bottom\"\n" +
                "        },\n" +
                "        \"target\": {\n" +
                "          \"cell\": \"4496de84-f010-4f37-9791-bd5cd56870fe\",\n" +
                "          \"port\": \"top\"\n" +
                "        },\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"type\": \"myedge\",\n" +
                "      \"source\": {\n" +
                "        \"cell\": \"e12803fd-f6ab-4864-a4b9-db2c6351d628\",\n" +
                "        \"port\": \"bottom\"\n" +
                "      },\n" +
                "      \"target\": {\n" +
                "        \"cell\": \"4496de84-f010-4f37-9791-bd5cd56870fe\",\n" +
                "        \"port\": \"top\"\n" +
                "      },\n" +
                "      \"zIndex\": 9\n" +
                "    },\n" +
                "    {\n" +
                "      \"shape\": \"myedge\",\n" +
                "      \"id\": \"0607b709-3dfe-4e16-995f-0947bc35442c\",\n" +
                "      \"data\": {\n" +
                "        \"id\": \"0607b709-3dfe-4e16-995f-0947bc35442c\",\n" +
                "        \"type\": \"myedge\",\n" +
                "        \"source\": {\n" +
                "          \"cell\": \"4496de84-f010-4f37-9791-bd5cd56870fe\",\n" +
                "          \"port\": \"bottom\"\n" +
                "        },\n" +
                "        \"target\": {\n" +
                "          \"cell\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "          \"port\": \"top\"\n" +
                "        },\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"type\": \"myedge\",\n" +
                "      \"source\": {\n" +
                "        \"cell\": \"4496de84-f010-4f37-9791-bd5cd56870fe\",\n" +
                "        \"port\": \"bottom\"\n" +
                "      },\n" +
                "      \"target\": {\n" +
                "        \"cell\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "        \"port\": \"top\"\n" +
                "      },\n" +
                "      \"zIndex\": 10\n" +
                "    },\n" +
                "    {\n" +
                "      \"shape\": \"myedge\",\n" +
                "      \"id\": \"f6322365-462e-44bc-9da7-6f77e0ffd9d8\",\n" +
                "      \"data\": {\n" +
                "        \"id\": \"f6322365-462e-44bc-9da7-6f77e0ffd9d8\",\n" +
                "        \"type\": \"myedge\",\n" +
                "        \"source\": {\n" +
                "          \"cell\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "          \"port\": \"bottom\"\n" +
                "        },\n" +
                "        \"target\": {\n" +
                "          \"cell\": \"1feb5249-032a-4c93-b499-15685b16773d\",\n" +
                "          \"port\": \"right\"\n" +
                "        },\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"type\": \"myedge\",\n" +
                "      \"source\": {\n" +
                "        \"cell\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "        \"port\": \"bottom\"\n" +
                "      },\n" +
                "      \"target\": {\n" +
                "        \"cell\": \"1feb5249-032a-4c93-b499-15685b16773d\",\n" +
                "        \"port\": \"right\"\n" +
                "      },\n" +
                "      \"zIndex\": 11\n" +
                "    },\n" +
                "    {\n" +
                "      \"shape\": \"myedge\",\n" +
                "      \"id\": \"808bacdb-93dd-48dd-9a27-add594172f44\",\n" +
                "      \"data\": {\n" +
                "        \"id\": \"808bacdb-93dd-48dd-9a27-add594172f44\",\n" +
                "        \"type\": \"myedge\",\n" +
                "        \"source\": {\n" +
                "          \"cell\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "          \"port\": \"right\"\n" +
                "        },\n" +
                "        \"target\": {\n" +
                "          \"cell\": \"cc24692b-d1e3-4294-8179-3e5b1b4c093f\",\n" +
                "          \"port\": \"top\"\n" +
                "        },\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"type\": \"myedge\",\n" +
                "      \"source\": {\n" +
                "        \"cell\": \"72e818eb-2962-491a-a4bb-1b59f55ab379\",\n" +
                "        \"port\": \"right\"\n" +
                "      },\n" +
                "      \"target\": {\n" +
                "        \"cell\": \"cc24692b-d1e3-4294-8179-3e5b1b4c093f\",\n" +
                "        \"port\": \"top\"\n" +
                "      },\n" +
                "      \"zIndex\": 12\n" +
                "    },\n" +
                "    {\n" +
                "      \"shape\": \"myedge\",\n" +
                "      \"id\": \"a968fe77-dfe7-482f-82ec-995863097941\",\n" +
                "      \"data\": {\n" +
                "        \"id\": \"a968fe77-dfe7-482f-82ec-995863097941\",\n" +
                "        \"type\": \"myedge\",\n" +
                "        \"source\": {\n" +
                "          \"cell\": \"cc24692b-d1e3-4294-8179-3e5b1b4c093f\",\n" +
                "          \"port\": \"bottom\"\n" +
                "        },\n" +
                "        \"target\": {\n" +
                "          \"cell\": \"3e3ad473-4668-4fa4-8f7c-0137ac6b68ae\",\n" +
                "          \"port\": \"top\"\n" +
                "        },\n" +
                "        \"x\": 100,\n" +
                "        \"y\": 100\n" +
                "      },\n" +
                "      \"type\": \"myedge\",\n" +
                "      \"source\": {\n" +
                "        \"cell\": \"cc24692b-d1e3-4294-8179-3e5b1b4c093f\",\n" +
                "        \"port\": \"bottom\"\n" +
                "      },\n" +
                "      \"target\": {\n" +
                "        \"cell\": \"3e3ad473-4668-4fa4-8f7c-0137ac6b68ae\",\n" +
                "        \"port\": \"top\"\n" +
                "      },\n" +
                "      \"zIndex\": 13\n" +
                "    }\n" +
                "  ]";

        JSONArray jsonArray = JSON.parseArray(str);
        String targetId = "1feb5249-032a-4c93-b499-15685b16773d";
        List<String> arrayList = new ArrayList<>();
        arrayList.add("867798e3-aa47-46d6-9ae2-2e9a675eaab1");
        arrayList.add("867798e3-aa47-46d6-9ae2-2eda675eaab1");

        String str2 = getSourceId(jsonArray, targetId, arrayList);

        System.out.println(str2);

    }


    /**
     * 递归查询，根据targetId找到sourceId
     * @param jsonArray jsonArray
     * @param targetId targetId
     * @param sourceIdList sourceIdList
     * @return
     */
    public static String getSourceId(JSONArray jsonArray, String targetId, List<String> sourceIdList) {
        String sourceId = null;
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            if ("myedge".equals(jsonObject.getString("shape"))
                    && jsonObject.getJSONObject("target").getString("cell").equals(targetId)
                    && !sourceIdList.contains(jsonObject.getJSONObject("source").getString("cell"))) {
                return getSourceId(jsonArray, jsonObject.getJSONObject("source").getString("cell"), sourceIdList);
            }
            else if ("myedge".equals(jsonObject.getString("shape"))
                    && jsonObject.getJSONObject("target").getString("cell").equals(targetId)
                    && sourceIdList.contains(jsonObject.getJSONObject("source").getString("cell"))) {
                sourceId =  jsonObject.getJSONObject("source").getString("cell");
            }
        }
        return sourceId;
    }
}
