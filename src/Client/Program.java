package Client;

public class Program {

    public static void main(String[] args) {
        ServerConnector serverConnection = new ServerConnector();
        LoginForm initialLogin = new LoginForm(serverConnection);
    }
}
