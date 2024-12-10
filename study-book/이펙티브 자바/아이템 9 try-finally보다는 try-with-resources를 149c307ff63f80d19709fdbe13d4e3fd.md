# 아이템 9: try-finally보다는 try-with-resources를 사용하라

### 요약

- 리소스를 다룰때는 `try-with-resources` 를 사용하라 이는 코드가 간결하고 안전하며, 예외가 발생해도 리소스가 확실히 해제되도록 보장한다, try-finally는 복잡하고 오류가 발생하기 쉽다

### 핵심 내용

- 리소스 관리란?
    - 파일, 소켓, 데이터베이스 연결 등과 같은 리소스는 명시적으로 해제해야 한다
        - InputStream, OutputStream, java.sql.Connection
    - 리소스가 해제되지 않으면 메모리 누수나 시스템 리소스 고갈 문제가 발생할 수 있다
- try-finally의 문제점
    - 코드 가독성 저하
        - 리소스가 많아질수록 finally 블록이 중첩되거나 길어져 코드가 복잡해짐
    - 예외가 누락될 가능성
        - try와 finally에서 각각 예외가 발생하면, finally에서 발생한 예외가 try 블록의 원래 예외를 덮어씀
    - 관리의 어려움
        - 여러 리소스를 순서대로 해제해야 . 할경우 코드 유지보수가 어려워짐

```java
import java.io.*;

public class NestedTryFinallyExample {
    public static void main(String[] args) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // InputStream 초기화
            inputStream = new FileInputStream("input.txt");

            try {
                // OutputStream 초기화
                outputStream = new FileOutputStream("output.txt");

                // 데이터를 읽고 쓰는 작업
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.err.println("Error during writing to output: " + e.getMessage());
            } finally {
                // OutputStream 해제
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        System.err.println("Failed to close outputStream: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error during reading from input: " + e.getMessage());
        } finally {
            // InputStream 해제
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("Failed to close inputStream: " + e.getMessage());
                }
            }
        }
    }
}
```

- try-with-resources의 장점
    - 자동 리소스 관리
        - 리소스가 AutoCloseable의 인터페이스를 구현하면, TWR을 통해 자동으로 close() 메서드가 호출됨
    - 간결한 코드
        - 리소스를 여러 개 사용하더라도 TWR 구문을 중첩 없이 한 번에 처리 가능
    - 예외 처리 안정성
        - try 블록에서 발생한 예외와 close()에서 발생한 예외 모두를 기록하며, 원래 예외를 우선 노출

```java
import java.io.*;

public class TryWithResourcesExample {
    public static void main(String[] args) {
        try (
            InputStream inputStream = new FileInputStream("input.txt");
            OutputStream outputStream = new FileOutputStream("output.txt")
        ) {
            // 데이터를 읽고 쓰는 작업
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("Error during file processing: " + e.getMessage());
        }
    }
}
```

### 요약

- try-with-resources는 리소스를 안전하고 간결하게 관리할 수 있는 방법이다. AutoCloseable을 구현한 리소스를 사용할 때는 항상 TWR을 활용하라. try-finally는 복잡하고 안전하지 않으므로 피하라.