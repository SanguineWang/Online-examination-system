package com.sanguinewang.oes.VO;

import lombok.Data;

/**
 * Description: oes
 * Created by Rice on 2020/7/6 21:42
 * 通用返回VO
 */
@Data
public class ResultVO<T> {

    private String msg;

    private T data;

}
