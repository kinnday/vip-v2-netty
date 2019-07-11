package cn.enjoyedu.nettybasic.serializable.msgpack;/**
 * @author Administrator
 * @date 2019-07-11
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @program: vip-v2-netty
 * @description:
 * @author: fu.xianchao
 * @create: 2019-07-11 22:07
 **/
public class MsgPackDecodeFxc extends MessageToMessageDecoder {

    /**
     * Decode from one message to an other. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToMessageDecoder} belongs to
     * @param msg the message to decode to an other one
     * @param out the {@link List} to which decoded messages should be added
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
//        int length
//        fxc-todo
        //服务端必须先解密
//            p1: 当前解码器，报文最大长度
//            p2: 长度字段在报文中的起始位置
//            p3： 报文长度-占用字节数
//            p4： 读取字节的调整值（要读取内容的实际长度值 - 长度字段值）
//            p5： 前面忽略的字节数

    }
}
