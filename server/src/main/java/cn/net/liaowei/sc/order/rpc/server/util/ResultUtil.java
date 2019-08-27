package cn.net.liaowei.sc.order.rpc.server.util;


import cn.net.liaowei.sc.order.rpc.server.domain.vo.ResultVO;

/**
 * @author LiaoWei
 */
public class ResultUtil {
    public static <E> ResultVO<E> success(E e) {
        ResultVO<E> resultVO = new ResultVO<>();
        resultVO.setCode("OK");
        resultVO.setMessage("成功");
        resultVO.setData(e);
        return resultVO;
    }

    public static <E> ResultVO<E> success() {
        return success(null);
    }

    public static <E> ResultVO<E> error(String code, String message) {
        ResultVO<E> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }

    public static <E> ResultVO<E> error(String code) {
        return error(code, null);
    }
}
