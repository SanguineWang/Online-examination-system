package com.sanguinewang.oes.util;


import com.sanguinewang.oes.VO.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Created by Rice
 * 2020年7月6日21:45:04
 * 通用返回VO 构造工具
 */
public class ResultVOUtil {

    public static ResultVO success(Object object,String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null,"success");
    }


    public static void error( HttpStatus status,String msg) {
        throw new ResponseStatusException(status,msg);
    }
}
