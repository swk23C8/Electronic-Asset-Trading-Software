package Client;

public class Program {

    public static void main(String[] args) {
        ServerConnector serverConnection = new ServerConnector();
        int maxAttempts = 5;
        for (int count = 0; count < maxAttempts - 1; count++){
            if (serverConnection.getIfConnected()){
                break;
            }
            serverConnection = new ServerConnector();
        }

        //
        if (serverConnection.getIfConnected()){
            LoginForm initialLogin = new LoginForm(serverConnection);
        }
        else {
            System.exit(0);
        }
    }
}
