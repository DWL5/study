package hello.springcore.lifecycle;

public class NetworkClient2 {

    private String url;

    public NetworkClient2() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        System.out.println("setUrl url = " + url);
        this.url = url;
    }

    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    public void disconnect() {
        System.out.println("close: " + url);
    }


    public void init() throws Exception {
        System.out.println("afterPropertiesSet called.");
        //의존관계 주입이 끝나면 호출한다.
        connect();
        call("초기화 연결 메시지");
    }


    public void close() throws Exception {
        disconnect();
    }
}
