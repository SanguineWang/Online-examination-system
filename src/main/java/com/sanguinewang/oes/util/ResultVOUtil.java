package com.sanguinewang.oes.util;


import com.sanguinewang.oes.VO.ResultVO;

/**
 * Created by Rice
 * 2020年7月6日21:45:04
 * 通用返回VO 构造工具
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg("success");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }


    public static ResultVO error(String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        return resultVO;
    }
}
