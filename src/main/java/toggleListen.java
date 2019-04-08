
public class toggleListen {
    public static void main(String[] args) {
        tellstickRequestHandler tellHandler = new tellstickRequestHandler();
        String input =
                        "POST /toggle HTTP/1.1\n" +
                        "Host: www.example.com\n" +
                        "Content-Type: application/json\n" +
                        "Content-Length: 49\n" +
                        "\n" +
                        "{\n" +
                        "    \"deviceID\": \"2\",\n" +
                        "    \"operation\": \"1\",\n" +
                        "}";

        tellHandler.parseRequest(input);
    }
}
