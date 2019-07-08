package cn.enjoyedu.nettybasic.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 类说明：自己的业务处理
 */
@ChannelHandler.Sharable
/*不加这个注解那么在增加到childHandler时就必须new出来*/
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

//    无状态的类， 当然是线程安全的。

    /*客户端读到数据以后，就会执行*/
//  1000 数据： 500-buffer: 执行1次；
//   从字节流可以正常转为字符串时才 会被调用一次。
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        System.out.println("Server accept"+in.toString(CharsetUtil.UTF_8));
        ctx.write(in);

        // 手动向下传递， 责任链模式！！
//        ctx.fireChannelRead();
    }

    /*** 服务端读取完成网络数据后的处理*/
    @Override
//  1000 数据： 500-buffer: 执行2次 !!!!
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
//        ctx.fireChannelReadComplete();
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
