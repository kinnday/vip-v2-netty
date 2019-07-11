package cn.enjoyedu.nettyhttp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.ssl.SslContext;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public ServerHandlerInit(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        //处理http服务的关键handler
        //TODO
        ph.addLast("encoder",new HttpResponseDecoder());
        ph.addLast("decoder",new HttpRequestDecoder());
        ph.addLast("aggregator",new HttpObjectAggregator(10*1024*1024));

        ph.addLast("compressor",new HttpContentCompressor()); // 内容压缩
        ph.addLast("handler", new BusiHandler());// 服务端业务逻辑
    }
}
