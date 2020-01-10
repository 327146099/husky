package com.neusiri.husky.core.util.network;

import com.alibaba.fastjson.JSONObject;
import com.neusiri.husky.core.util.http.HttpUtils;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>获取地址类工具类</p>
 * <p>创建日期：2019-09-04</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@UtilityClass
public class AddressUtils {

    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    private static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    /**
     * 通过ip获取真实的地址
     *
     * @param ip ip地址
     * @return 真实地址
     */
    public static String getRealAddressByIp(String ip) {
        String address = "XX XX";
        if (StringUtils.isEmpty(ip)) {
            return address;
        }
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (true) {
            Map<String, String> params = new HashMap<>(16);
            params.put("ip", ip);
            String rspStr = null;
            try {
                rspStr = HttpUtils.get(IP_URL, params);
            } catch (IOException e) {
                log.error("获取地理位置异常 {}, 错误信息为{}", ip, e.getMessage());
                return address;
            }

            JSONObject obj = JSONObject.parseObject(rspStr);
            Integer code = obj.getInteger("code");
            if (code != null && code == 0) {
                JSONObject data = obj.getObject("data", JSONObject.class);
                String region = data.getString("region");
                String city = data.getString("city");
                address = region + " " + city;
            } else {
                log.error("获取地理位置接口返回值异常, 返回值为 [{}]", code);
            }
        }
        return address;
    }

}
