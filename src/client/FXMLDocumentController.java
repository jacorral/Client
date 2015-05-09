/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author angel
 */
public class FXMLDocumentController implements Initializable {

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
    final String host = "localhost";
    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea messagesTextArea;
    @FXML
    private TextField messageTextField;

    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        messageTextField.setOnAction(e -> {
            try {
                toServer.writeUTF(nameTextField.getText().trim());
                toServer.writeUTF(messageTextField.getText().trim());
                toServer.flush();

                String input = fromServer.readUTF();
                String message = fromServer.readUTF();

                messagesTextArea.appendText(input + '\n');
                messagesTextArea.appendText(message + '\n');

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        try {
            Socket socket = new Socket(host, 8001);

            fromServer = new DataInputStream(socket.getInputStream());

            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex1) {
            messagesTextArea.appendText(ex1.toString() + '\n');

        }

    }
}
