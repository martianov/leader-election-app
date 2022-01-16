package com.github.martianov.le;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class LeaderElectionApp {
    public void startLeader(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new MessageDecoder(), new MessageEncoder(), new MessageHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void start(String leaderHost, int leaderPort) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageDecoder(), new MessageEncoder(), new PingHandler());
                    }
                });

            ChannelFuture f = b.connect(leaderHost, leaderPort).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static int DEFAULT_PORT = 8092;
    public static String DEFAULT_LEADER_HOST = "localhost";

    private static int getPort() {
        String portString = System.getenv("PORT");
        if (portString == null) {
            return DEFAULT_PORT;
        }
        return Integer.parseInt(portString, 10);
    }

    private static Integer getLeaderPort() {
        String portString = System.getenv("LEADER_PORT");
        if (portString == null) {
            return null;
        }
        return Integer.parseInt(portString, 10);
    }

    private static String getLeaderHost() {
        String host = System.getenv("LEADER_HOST");
        if (host == null) {
            return DEFAULT_LEADER_HOST;
        }
        return host;
    }

    public static void main(String[] args) throws Exception {
        int portString = getPort();
        Integer leaderPort = getLeaderPort();
        if (leaderPort == null) {
            new LeaderElectionApp().startLeader(portString);
        } else {
            new LeaderElectionApp().start(getLeaderHost(), leaderPort);
        }
    }
}
