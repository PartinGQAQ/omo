package com.itheima.utils;

import com.itheima.model.domain.MessageContent;
import com.itheima.model.domain.MessageText;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * 钉钉推送工具类
 */
@Slf4j
@Async
@Component
public class DingTalkPushUtil {

    /**
     * 按照钉钉API处理内容格式
     * @param content
     */
    public void pushText(String content) {
        MessageText message = new MessageText();
        MessageContent messageContent = new MessageContent();
        message.setMsgtype("text");
        messageContent.setContent(content);
        message.setText(messageContent);
        push(message);
    }

    /**
     * 推送消息
     * @param obj
     */
    private void push(Object obj) {
        try {
            //自定义钉钉机器人生成链接  access_token钉钉自动生成
            URL url = new URL("https://oapi.dingtalk.com/robot/send?access_token=7b19749f9f57a05bb24c33ffa76274ab6cb46ec780bbd0ec14c5e1673bd725ed");
            //打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // Post 请求不能使用缓存
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            String jsonMessage = JSONObject.toJSONString(obj);
            byte[] data = jsonMessage.getBytes();
            // 发送请求参数
            out.write(data);
            // flush输出流的缓冲
            out.flush();
            out.close();
            //开始获取数据
            InputStream in = conn.getInputStream();
            byte[] content = new byte[in.available()];
            in.read(content);
            System.out.println("发送成功");
            log.info(">>>>>>>>>钉钉发送成功..........<<<<<<<<<");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
