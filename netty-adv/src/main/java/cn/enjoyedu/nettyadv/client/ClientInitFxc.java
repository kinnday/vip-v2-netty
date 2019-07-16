package cn.enjoyedu.nettyadv.client;

import cn.enjoyedu.nettyadv.kryocodec.KryoDecoder;
import cn.enjoyedu.nettyadv.kryocodec.KryoEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：客户端Handler的初始化
 * fxc-包含6个具体的handler实现，其中两个是自定义的
 */
public class ClientInitFxc extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        /*剥离接收到的消息的长度字段，拿到实际的消息报文的字节数组*/
//        fxc-add-handler-1
        ch.pipeline().addLast("frameDecoder",
//              LengthFieldBasedFrameDecoder 解决不定长消息的 黏包半包问题；
//                保证拿到的是一个完完整整的报文
                new LengthFieldBasedFrameDecoder(65535,
                        0,2,0,
                        2));

        /*给发送出去的消息增加长度字段*/
//        fxc-add-handler-2
        ch.pipeline().addLast("frameEncoder",
                new LengthFieldPrepender(2));

        /*反序列化，将字节数组转换为消息实体*/
//        fxc-add-handler-3
        ch.pipeline().addLast(new KryoDecoder());
        /*序列化，将消息实体转换为字节数组准备进行网络传输*/
//        fxc-add-handler-4
        ch.pipeline().addLast("MessageEncoder",
                new KryoEncoder());

//      这一步 已经获取到MyMessage 对象； 下面都是应用层业务处理
        /*超时检测*/
//        fxc-add-handler-5
        ch.pipeline().addLast("readTimeoutHandler",
                new ReadTimeoutHandler(10));

        /*发出登录请求*/
//        fxc-add-handler-6
        ch.pipeline().addLast("LoginAuthHandler",
                new LoginAuthReqHandler());

        /*发出心跳请求*/
//        fxc-add-handler-7
        ch.pipeline().addLast("HeartBeatHandler",
                new HeartBeatReqHandler());
    }
}
