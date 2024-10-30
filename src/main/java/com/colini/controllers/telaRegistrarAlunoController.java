package com.colini.controllers;

import java.io.FileWriter;
import java.util.function.UnaryOperator;

import com.colini.App;
import com.colini.models.Aluno;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class telaRegistrarAlunoController {

    @FXML
    private Button btnFechar;
    
    @FXML
    private Button btnRegistrar;

    @FXML
    private Pane paneTelaRegistro;

    @FXML
    private Text txtTextoRegistrarAluno;

    @FXML
    private TextField txtfieldCPF;

    @FXML
    private TextField txtfieldEmail;

    @FXML
    private TextField txtfieldNascimento;

    @FXML
    private TextField txtfieldNome;

    Aluno aluno;
    String caminho = "src/main/java/com/colini/informacoes_alunos.txt";

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() {
        // Configurar o TextField para aceitar somente valores numericos
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Aceita apenas dígitos
                return change;
            }
            return null; // Ignora a mudança
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        txtfieldCPF.setTextFormatter(textFormatter);

        
        // Evento para capturar o clique inicial do mouse
        paneTelaRegistro.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Evento para mover a janela enquanto o mouse é arrastado
        paneTelaRegistro.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) paneTelaRegistro.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        btnFechar.setOnAction(event -> Platform.exit());

    }

    
    public void escreveArquivo(String nome, String nascimento, String cpf, String email) {
        System.out.println("Caminho do arquivo: " + caminho);
        try {
            FileWriter writer = new FileWriter(caminho, true);
            writer.write(nome + ", ");
            writer.write(nascimento + ", ");
            writer.write(cpf + ", ");
            writer.write(email);
            writer.write("\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocorreu um Erro: " + e.getMessage());
        } 

    }

    public void cadastrarDados() {
        
        String nomeAluno = txtfieldNome.getText();
        String nascimentoAluno = txtfieldNascimento.getText();
        String cpfAlunoTexto = txtfieldCPF.getText();
        String emailAluno = txtfieldEmail.getText();

        if (nomeAluno.isEmpty() || nascimentoAluno.isEmpty() || cpfAlunoTexto.isEmpty() || emailAluno.isEmpty()) {
            // Exibindo uma mensagem de alerta ao usuário
            System.out.println("Todos os campos devem ser preenchidos!");
            return; // Sai do método se algum campo estiver vazio
        }
        escreveArquivo(nomeAluno, nascimentoAluno, cpfAlunoTexto, emailAluno);
        telaLista();
    }

    
    public void telaLista() {
        App.mudaTela("./views/telaListaAluno.fxml");
    }
}