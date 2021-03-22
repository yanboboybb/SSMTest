package com.test.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Base64;

/**
 * @author: yanbobo
 * @DATE: 2021/2/7 17:37
 * @description:
 */
public class RabbitMqTopicSendUtil {
   // private final static String QUEUE_NAME = "wz_oa_notice";//hanweb-queue-push-oa-info
    private final static String QUEUE_NAME = "hanweb-queue-push-oa-info";//hanweb-queue-push-oa-info
    private final static String EXCHANGE_NAME = "com.hanweb.cms.oa";
    private final static String ROUTING_KEY = "hanweb-route-oa-sync";
    private final static String HOST = "127.0.0.1";
    private final static String username = "admin";
    private final static String password = "hanweb";
    private final static Integer PORT = 5672;

    private final static String CHAR_SET = "UTF-8";

    public static boolean send(String message, boolean base64) {
        ConnectionFactory factory = null;
        Connection connection = null;
        Channel channel = null;
        try {

            if (base64) {
                message = Base64.getEncoder().encodeToString(message.getBytes(CHAR_SET));
            }
            factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername(username);
            factory.setPassword(password);
            connection = factory.newConnection();
            channel = connection.createChannel();
            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.exchangeDeclare(EXCHANGE_NAME,"topic",true);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes(CHAR_SET));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void main(String[] args) {
        //(办公厅，信息中心，信息中心三处)
        //RabbitMqSendUtil.send("eyJvcGVyYXRpb25fdHlwZSI6InB1Ymxpc2giLCJkYXRhIjoie1wiZ3VpZFwiOlwiOGE3ZDdkMzlmNmVjNDEwYWFhZmM2MjNlNmM2NDVmMWZcIixcImJ0XCI6XCJkc2J2ZmprYnZkamtmdmJqa2RidmR2YmRmalwiLFwicHVibGlzaF9kZXB0X2lkXCI6XCIzN2M3YTliOTRlNWI0NTVjYWQ3N2I4MmUzNjNhYzk0MFwiLFwicHVibGlzaF9kZXB0X3RleHRcIjpcIuWMl+S6rOWkp+axieWunuaWveS6uuWRmOacuuaehOWtkOe6p1wiLFwicHVibGlzaF90aW1lXCI6XCIyMDIxLTAzLTEwIDE2OjEzOjExXCIsXCJwdXNoX3RpbWVcIjpcIjIwMjEtMDMtMTAgMTY6MTM6MTJcIixcInJlY2VpdmVfZGVwdF9pZFwiOlwiMDAwMDAwMDAwMDI1LDAwZmQwMTU0MDMyMThhZWYwMGU1LDAwMDAwMDAwMDAwOFwiLFwicmVjZWl2ZV9kZXB0X3RleHRcIjpcIuS/oeaBr+S4reW/gyzkv6Hmga/kuK3lv4PkuInlpIQs5Yqe5YWs5Y6FXCIsXCJyZWNlaXZlX3VzZXJfdHlwZVwiOm51bGwsXCJyZWNlaXZlX3VzZXJ0eXBlX3RleHRcIjpudWxsLFwicmVjZWl2ZV91bml0X3JhbmtcIjpudWxsLFwicmVjZWl2ZV91bml0cmFua190ZXh0XCI6bnVsbCxcInJlY2VpdmVfY2FyZWVyX3JhbmtcIjpudWxsLFwicmVjZWl2ZV9jYXJlZXJyYW5rX3RleHRcIjpudWxsLFwiY29udGVudFwiOlwiPHA+ZHNidmZqa2J2ZGprZnZiamtkYnZkdmJkZmo8L3A+XCIsXCJjb250ZW50X3RleHRcIjpcImRzYnZmamtidmRqa2Z2YmprZGJ2ZHZiZGZqXCIsXCJvc190eXBlXCI6XCLkuK3lpK7nuqrlp5Tlm73lrrbnm5Hlp5TlhoXpg6jlt6XkvZznvZFcIixcInJvd3N0YXRlXCI6XCIxXCIsXCJwdXNoX3N0YXRlc1wiOlwiMVwiLFwidXJsXCI6XCJodHRwOi8vMTI3LjAuMC4xOjgwODEvRHJlYW1XZWIv572R56uZ6YCa55+l5YWs5ZGK5pWw5o2u5o6l5Y+j5pa55qGILeacgOe7iOeJiC5kb2N4LGh0dHA6Ly8xMjcuMC4wLjE6ODA4MS9EcmVhbVdlYi/nvZHnq5npgJrnn6XlhazlkYrmlbDmja7mjqXlj6PmlrnmoYgt5pyA57uI54mILmRvY3gsaHR0cDovLzEyNy4wLjAuMTo4MDgxL0RyZWFtV2ViL+e9keermemAmuefpeWFrOWRiuaVsOaNruaOpeWPo+aWueahiC3mnIDnu4jniYguZG9jeFwiLFwiaXNfdG9wXCI6XCIwXCIsXCJ0b3BfdmFsaWRfdGltZVwiOm51bGwsXCJtalwiOlwiMVwiLFwibWpfdGV4dFwiOlwi6Z2e5a+GXCIsXCJzaG93b3JkZXJcIjpcIjM3NDY3XCIsXCJsYXN0X3VwZGF0ZV90aW1lXCI6XCIyMDIxLTAzLTEwIDE2OjEzOjEyXCIsXCJkZXBsb3lfdGltZVwiOlwiMjAyMS0wMy0xMCAxNjoxMzoxMVwiLFwiY3JlYXRlX3VzZXJpZFwiOlwiMzdkZWY4MjdmZjQ3NGJjMTliMjEzODQ0YWNmMzZhOTVcIixcImNyZWF0ZV91bmFtZVwiOlwi6LW15LuB5bOwXCJ9Iiwib3NfdHlwZSI6IuS4reWkrue6quWnlOWbveWutuebkeWnlOWGhemDqOW3peS9nOe9kSIsImRhdGFfdHlwZSI6Im5vdGljZSIsImd1aWQiOiI4YTdkN2QzOWY2ZWM0MTBhYWFmYzYyM2U2YzY0NWYxZiJ9",false);
        //中央本级
       // RabbitMqSendUtil.send("eyJvcGVyYXRpb25fdHlwZSI6InB1Ymxpc2giLCJkYXRhIjoie1wiZ3VpZFwiOlwiOGE3ZDdkMzlmNmVjNDEwYWFhZmM2MjNlNmM2NDVmMWZcIixcImJ0XCI6XCJkc2J2ZmprYnZkamtmdmJqa2RidmR2YmRmalwiLFwicHVibGlzaF9kZXB0X2lkXCI6XCIzN2M3YTliOTRlNWI0NTVjYWQ3N2I4MmUzNjNhYzk0MFwiLFwicHVibGlzaF9kZXB0X3RleHRcIjpcIuWMl+S6rOWkp+axieWunuaWveS6uuWRmOacuuaehOWtkOe6p1wiLFwicHVibGlzaF90aW1lXCI6XCIyMDIxLTAzLTEwIDE2OjEzOjExXCIsXCJwdXNoX3RpbWVcIjpcIjIwMjEtMDMtMTAgMTY6MTM6MTJcIixcInJlY2VpdmVfZGVwdF9pZFwiOlwiOWMzYWUwN2YwOGEzNGQ4ZmI1YTdmY2ZjMGI0NWE2NjlcIixcInJlY2VpdmVfZGVwdF90ZXh0XCI6XCLkuK3lpK7mnKznuqdcIixcInJlY2VpdmVfdXNlcl90eXBlXCI6bnVsbCxcInJlY2VpdmVfdXNlcnR5cGVfdGV4dFwiOm51bGwsXCJyZWNlaXZlX3VuaXRfcmFua1wiOm51bGwsXCJyZWNlaXZlX3VuaXRyYW5rX3RleHRcIjpudWxsLFwicmVjZWl2ZV9jYXJlZXJfcmFua1wiOm51bGwsXCJyZWNlaXZlX2NhcmVlcnJhbmtfdGV4dFwiOm51bGwsXCJjb250ZW50XCI6XCI8cD5kc2J2ZmprYnZkamtmdmJqa2RidmR2YmRmajwvcD5cIixcImNvbnRlbnRfdGV4dFwiOlwiZHNidmZqa2J2ZGprZnZiamtkYnZkdmJkZmpcIixcIm9zX3R5cGVcIjpcIuS4reWkrue6quWnlOWbveWutuebkeWnlOWGhemDqOW3peS9nOe9kVwiLFwicm93c3RhdGVcIjpcIjFcIixcInB1c2hfc3RhdGVzXCI6XCIxXCIsXCJ1cmxcIjpcImh0dHA6Ly8xMjcuMC4wLjE6ODA4MS9EcmVhbVdlYi/nvZHnq5npgJrnn6XlhazlkYrmlbDmja7mjqXlj6PmlrnmoYgt5pyA57uI54mILmRvY3gsaHR0cDovLzEyNy4wLjAuMTo4MDgxL0RyZWFtV2ViL+e9keermemAmuefpeWFrOWRiuaVsOaNruaOpeWPo+aWueahiC3mnIDnu4jniYguZG9jeCxodHRwOi8vMTI3LjAuMC4xOjgwODEvRHJlYW1XZWIv572R56uZ6YCa55+l5YWs5ZGK5pWw5o2u5o6l5Y+j5pa55qGILeacgOe7iOeJiC5kb2N4XCIsXCJpc190b3BcIjpcIjBcIixcInRvcF92YWxpZF90aW1lXCI6bnVsbCxcIm1qXCI6XCIxXCIsXCJtal90ZXh0XCI6XCLpnZ7lr4ZcIixcInNob3dvcmRlclwiOlwiMzc0NjdcIixcImxhc3RfdXBkYXRlX3RpbWVcIjpcIjIwMjEtMDMtMTAgMTY6MTM6MTJcIixcImRlcGxveV90aW1lXCI6XCIyMDIxLTAzLTEwIDE2OjEzOjExXCIsXCJjcmVhdGVfdXNlcmlkXCI6XCIzN2RlZjgyN2ZmNDc0YmMxOWIyMTM4NDRhY2YzNmE5NVwiLFwiY3JlYXRlX3VuYW1lXCI6XCLotbXku4Hls7BcIn0iLCJvc190eXBlIjoi5Lit5aSu57qq5aeU5Zu95a6255uR5aeU5YaF6YOo5bel5L2c572RIiwiZGF0YV90eXBlIjoibm90aWNlIiwiZ3VpZCI6IjhhN2Q3ZDM5ZjZlYzQxMGFhYWZjNjIzZTZjNjQ1ZjFmIn0=",false);
        //办公厅
        //RabbitMqSendUtil.send("eyJvcGVyYXRpb25fdHlwZSI6InB1Ymxpc2giLCJkYXRhIjoie1wiZ3VpZFwiOlwiOGE3ZDdkMzlmNmVjNDEwYWFhZmM2MjNlNmM2NDVmMWZcIixcImJ0XCI6XCJkc2J2ZmprYnZkamtmdmJqa2RidmR2YmRmalwiLFwicHVibGlzaF9kZXB0X2lkXCI6XCIzN2M3YTliOTRlNWI0NTVjYWQ3N2I4MmUzNjNhYzk0MFwiLFwicHVibGlzaF9kZXB0X3RleHRcIjpcIuWMl+S6rOWkp+axieWunuaWveS6uuWRmOacuuaehOWtkOe6p1wiLFwicHVibGlzaF90aW1lXCI6XCIyMDIxLTAzLTEwIDE2OjEzOjExXCIsXCJwdXNoX3RpbWVcIjpcIjIwMjEtMDMtMTAgMTY6MTM6MTJcIixcInJlY2VpdmVfZGVwdF9pZFwiOlwiMDAwMDAwMDAwMDA4XCIsXCJyZWNlaXZlX2RlcHRfdGV4dFwiOlwi5Yqe5YWs5Y6FXCIsXCJyZWNlaXZlX3VzZXJfdHlwZVwiOm51bGwsXCJyZWNlaXZlX3VzZXJ0eXBlX3RleHRcIjpudWxsLFwicmVjZWl2ZV91bml0X3JhbmtcIjpudWxsLFwicmVjZWl2ZV91bml0cmFua190ZXh0XCI6bnVsbCxcInJlY2VpdmVfY2FyZWVyX3JhbmtcIjpudWxsLFwicmVjZWl2ZV9jYXJlZXJyYW5rX3RleHRcIjpudWxsLFwiY29udGVudFwiOlwiPHA+ZHNidmZqa2J2ZGprZnZiamtkYnZkdmJkZmo8L3A+XCIsXCJjb250ZW50X3RleHRcIjpcImRzYnZmamtidmRqa2Z2YmprZGJ2ZHZiZGZqXCIsXCJvc190eXBlXCI6XCLkuK3lpK7nuqrlp5Tlm73lrrbnm5Hlp5TlhoXpg6jlt6XkvZznvZFcIixcInJvd3N0YXRlXCI6XCIxXCIsXCJwdXNoX3N0YXRlc1wiOlwiMVwiLFwidXJsXCI6XCJodHRwOi8vMTI3LjAuMC4xOjgwODEvRHJlYW1XZWIv572R56uZ6YCa55+l5YWs5ZGK5pWw5o2u5o6l5Y+j5pa55qGILeacgOe7iOeJiC5kb2N4LGh0dHA6Ly8xMjcuMC4wLjE6ODA4MS9EcmVhbVdlYi/nvZHnq5npgJrnn6XlhazlkYrmlbDmja7mjqXlj6PmlrnmoYgt5pyA57uI54mILmRvY3gsaHR0cDovLzEyNy4wLjAuMTo4MDgxL0RyZWFtV2ViL+e9keermemAmuefpeWFrOWRiuaVsOaNruaOpeWPo+aWueahiC3mnIDnu4jniYguZG9jeFwiLFwiaXNfdG9wXCI6XCIwXCIsXCJ0b3BfdmFsaWRfdGltZVwiOm51bGwsXCJtalwiOlwiMVwiLFwibWpfdGV4dFwiOlwi6Z2e5a+GXCIsXCJzaG93b3JkZXJcIjpcIjM3NDY3XCIsXCJsYXN0X3VwZGF0ZV90aW1lXCI6XCIyMDIxLTAzLTEwIDE2OjEzOjEyXCIsXCJkZXBsb3lfdGltZVwiOlwiMjAyMS0wMy0xMCAxNjoxMzoxMVwiLFwiY3JlYXRlX3VzZXJpZFwiOlwiMzdkZWY4MjdmZjQ3NGJjMTliMjEzODQ0YWNmMzZhOTVcIixcImNyZWF0ZV91bmFtZVwiOlwi6LW15LuB5bOwXCJ9Iiwib3NfdHlwZSI6IuS4reWkrue6quWnlOWbveWutuebkeWnlOWGhemDqOW3peS9nOe9kSIsImRhdGFfdHlwZSI6Im5vdGljZSIsImd1aWQiOiI4YTdkN2QzOWY2ZWM0MTBhYWFmYzYyM2U2YzY0NWYxZiJ9",false);
      // RabbitMqSendUtil.send("eyJndWlkIjoiMTIzNDU2Nzg5MHp4YyIsIm9zX3R5cGUiOiJ3enNqZ2IiLCJkYXRhX3R5cGUiOiJub3RpY2UiLCJvcGVyYXRpb25fdHlwZSI6ImRlbGV0ZSIsImRhdGEiOnsiZ3VpZCI6IjEyMzQ1Njc4OTB6eGMifX0=",false);
      RabbitMqTopicSendUtil.send("eyJndWlkIjoiMTIzNDU2Nzg5MHp4YyIsIm9zX3R5cGUiOiLkuK3lpK7nuqrlp5Tlm73lrrbnm5Hlp5TlhoXpg6jlt6XkvZznvZEiLCJkYXRhX3R5cGUiOiJub3RpY2UiLCJvcGVyYXRpb25fdHlwZSI6InB1Ymxpc2giLCJkYXRhIjp7Imd1aWQiOiIxMjM0NTY3ODkwenhjIiwiYnQiOiLnvZHnq5npgJrnn6XlhazlkYrmtYvor5UiLCJwdWJsaXNoX2RlcHRfaWQiOiIwMGZkMDE1NDAzMjEzZWQ1MDBkZCIsInB1Ymxpc2hfZGVwdF90ZXh0Ijoi5L+h5oGv5Lit5b+D5LiA5aSEIiwiY3JlYXRlX3VzZXJpZCI6ImJiNTQ0YWYwNjk1YjQ3MzdiYjUzNjQ4OGI2N2YxMTRmIiwiY3JlYXRlX3VuYW1lIjoi5pSA6JSa5pGIIiwicHVzaF90aW1lIjoiMjAyMS0zLTAzIDAwOjAwOjAwIiwicmVjZWl2ZV9kZXB0X2lkIjoiIiwicmVjZWl2ZV9kZXB0X3RleHQiOiIiLCJyZWNlaXZlX3VzZXJfdHlwZSI6IjEiLCJyZWNlaXZlX3VzZXJ0eXBlX3RleHQiOiLlnKjnvJbvvIjov5TogZjkurrlkZjvvIkiLCJyZWNlaXZlX3VuaXRfcmFuayI6IjEiLCJyZWNlaXZlX3VuaXRyYW5rX3RleHQiOiLlm73lrrbnuqfmraPogYwiLCJyZWNlaXZlX2NhcmVlcl9yYW5rIjoiMTIiLCJyZWNlaXZlX2NhcmVlcnJhbmtfdGV4dCI6IuWFtuS7liIsImNvbnRlbnQiOiLpgJrnn6XmtYvor5UiLCJjb250ZW50X3RleHQiOiLpgJrnn6XmtYvor5UiLCJ1cmwiOiJodHRwOi8vMTI3LjAuMC4xOjgwODEvRHJlYW1XZWIv572R56uZ6YCa55+l5YWs5ZGK5pWw5o2u5o6l5Y+j5pa55qGILeacgOe7iOeJiC5kb2N4LGh0dHA6Ly8xMjcuMC4wLjE6ODA4MS9EcmVhbVdlYi/nvZHnq5npgJrnn6XlhazlkYrmlbDmja7mjqXlj6PmlrnmoYgt5pyA57uI54mILmRvY3gsaHR0cDovLzEyNy4wLjAuMTo4MDgxL0RyZWFtV2ViL+e9keermemAmuefpeWFrOWRiuaVsOaNruaOpeWPo+aWueahiC3mnIDnu4jniYguZG9jeCIsIm9zX3R5cGUiOiLkuK3lpK7nuqrlp5Tlm73lrrbnm5Hlp5TlhoXpg6jlt6XkvZznvZEiLCJyb3dzdGF0ZSI6IjEiLCJwdXNoX3N0YXRlcyI6IjEiLCJpc190b3AiOiIwIiwidG9wX3ZhbGlkX3RpbWUiOiIiLCJtaiI6IjEiLCJtal90ZXh0Ijoi6Z2e5a+GIiwic2hvd29yZGVyIjoiMSIsImRlcGxveV90aW1lIjoiMjAyMS0zLTAzIDAwOjAwOjAwIiwicHVibGlzaF90aW1lIjoiMjAyMS0zLTAzIDAwOjAwOjAwIn19",false);


      //删除
     // RabbitMqSendUtil.send("{\"guid\":\"8a7d7d39f6ec410aaafc623e6c645f1f\",\"os_type\":\"中央纪委国家监委内部工作网\",\"data_type\":\"notice\",\"operation_type\":\"delete\",\"data\":{\"guid\":\"8a7d7d39f6ec410aaafc623e6c645f1f\"}}",true);




//        String encode = null;
//        encode = URLEncoder.encode("http://127.0.0.1:8081/DreamWeb/网站通知公告数据接口方案-最终版.docx");
//        System.out.println(encode);

//        String[] a = "vCkxW3yL1qWCVzyN/1CEjTTFrBEoykIRH3r6cpsUgToD34qX+RhJJvW7IUZPmCZlGJ7MJT661GXM9b5jYvxN8ZS6e9gFXPmoGDAghkH8WK1Gc23c9gg4HCebsFPz4mPsmLBOCGAukZSiC6GCXDoXQtM9L4TFSXv9hHsKkSq1XsNwn3ATMxKkO8w/HBCX38w+O5+godGSC73TJAPHysiA5ARFFhu1kHzuk9FrZMT8bPnw9lkVqYqpicczlDaQYvMOzi+kXU0ZhTeICqY2wi0+/5I/8dMFbDNNFCW0vt/ZFHbwEs3j4d7d1SH7tP8+NlqQVImdoBUua9VrkK2EZUDjRBXIjeutikgFl8oewQD5U7My2TJOi4cFjMbseBTPnIxP2Csv81b2hu86THKZ2x4TuDRce0AoEyxRvyjqjSGhETQTkO5P58VnLsg8LlwMk4+kj6uaeqsJA/FfvE/xk+T+VLDOs8m2ieXNh8hweTle8DkcdDd8rwVKORVDXdB93nvRnoa/PPphnHELxSvLPvFqs9FkMqIw52JpceHSHGvLyJP/+Uerf6AWH3nBEnBjumLH5i1g94/3Z2K3Ec7jj1EtJ7UED3RAFTbSeQ2ufulnpH2Oz8u3Kp0gMFckZjTLX7Mh".split("/");
//        for (String b : a){
//            System.out.println(b);
//        }
    }


}
