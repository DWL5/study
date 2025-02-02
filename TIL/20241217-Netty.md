### Netty
- Nerry는 NIO를 기반으로 설계된 추상화된 네트워크 라이브러리.
- NIO의 비동기적이고 이벤트 기반의 모델을 활용하면서, 개발자가 직접 NIO의 낮은 수준의 API를 다루지 않도록 도와줌.
- Netty는 NIO의 강력한 기능을 간단하고 효율적으로 사용할 수 있도록 한 도구.

### Netty의 주요 특징
- 비동기 및 이벤트 기반
  - NIO의 비동기 I/O를 사용하여 높은 동시성을 제공
  - 이벤트 루프(Event Loop)와 핸들러(Handler)를 통해 네트워크 이벤트를 처리
- 높은 성능
  - 성능과 확장성에 최적화되어 있어, 많은 연결을 효율적으로 처리
  - GC 부담을 줄이는 메모리 관리 기능 포함 (예: Pooled Bufferes)
- 다양한 프로토콜 지원
  - HTTP, WebSocket, gRPC, FTP등 다양한 프로토콜 구현 가능
- 단계별 처리 가능
  - 파이프라인 구조로 설계되어 데이터 흐름을 단계별로 처리할 수 있음.
  - 각 단계는 커스텀 핸들러로 구성 가능
- 운영 안정성
  - 다양한 네트워크 환경에서 안정적으로 작동하도록 설계됨
  - 예외 처리 및 재시도 매커니즘을 내장

  ### Netty의 핵심 구성 요소
1. EventLoop
  - 이벤트 처리를 담당하는 스레드 모델
  - I/O 작업, 타이머, 이벤트 핸들링 등을 수행
2. Channel
  - NIO의 Channel을 추상화한 객체로, 클라이언트와 서버 간의 데이터를 읽고 씀
  - 주요 구현체: NioSocketChannel, NioServerSocketChannel
3. Pipeline
  - 데이터 흐름을 처리하는 구조로, 여러 ChannelHandler를 포함
  - 데이터는 Inbound 또는 Outbound로 흐르며, 각각의 핸들러에서 처리
4. ChannelHandler
  - 네트워크 데이터의 변환 및 처리 로직 구현
  - 커스텀 핸들러를 작성하여 데이터 처리 로직을 구현
    
### Netty와 NIO의 차이점
- NIO로 작업 할 때
  - 클라이언트가 요청을 보냄
  - 내가 직접 채널(Channel)을 열고, 데이터 읽기를 처리함
  - 데이터를 버퍼(Buffer)에 저장하고 버퍼를 비우는 작업을 관리
  - 비동기 작업을 위해 여러 이벤트를 직접 관리
- Netty로 작업할 때
  - 클라이언트가 요청을 보냄
  - Netty가 알아서 채널과 버퍼를 생성하고 관리
  - 개발자는 핸들러라는 간단한 코드를 작성해서 "데이터를 받으면 이렇게 처리하세요" 라고 안내
  - Netty가 나머지 모든 복잡한 작업을 알아서 해줌.
 
```
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleNettyServer {
    public static void main(String[] args) throws Exception {
        // 클라이언트 연결을 처리할 EventLoopGroup 생성
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 서버 설정
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) // 비동기 채널 설정
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new SimpleHandler()); // 요청 처리 핸들러 추가
                    }
                });

            // 서버 시작
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            System.out.println("서버가 8080 포트에서 실행 중...");
            future.channel().closeFuture().sync(); // 서버 종료 대기
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    // 클라이언트 요청을 처리하는 핸들러
    static class SimpleHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println("받은 메시지: " + msg);
            ctx.writeAndFlush(msg); // 받은 메시지를 그대로 클라이언트에게 전송
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close(); // 오류 발생 시 채널 닫기
        }
    }
}
```
