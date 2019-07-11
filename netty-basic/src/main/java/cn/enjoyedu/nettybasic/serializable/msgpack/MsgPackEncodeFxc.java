package cn.enjoyedu.nettybasic.serializable.msgpack;/**
 * @author Administrator
 * @date 2019-07-11
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @program: vip-v2-netty
 * @description: 将实体类序列化
 * @author: fu.xianchao
 * @create: 2019-07-11 21:32
 **/
public class MsgPackEncodeFxc extends MessageToByteEncoder {

    /**
     * Encode a message into a {@link ByteBuf}. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte [] raw = messagePack.write(msg);
        out.writeBytes(raw);

    }
}
