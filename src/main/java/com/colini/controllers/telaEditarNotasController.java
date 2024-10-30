package com.colini.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.UnaryOperator;

import com.colini.App;
import com.colini.models.Aluno;
import com.colini.models.CompartilharDados;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class telaEditarNotasController {

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnRegistrarEdicaoNotas;

    @FXML
    private Button btnVoltar;

    @FXML
    private Pane paneTelaEditarNotas;

    @FXML
    private Text txtEditarNota1;

    @FXML
    private Text txtEditarNota2;

    @FXML
    private Text txtEditarNota3;

    @FXML
    private Text txtEditarNota4;

    @FXML
    private Text txtEditarNotas;

    @FXML
    private Text txtObservacao1EdicaoNotas;

    @FXML
    private Text txtObservacaoEditarNotas;

    @FXML
    private TextField txtfieldEditarNota1;

    @FXML
    private TextField txtfieldEditarNota2;

    @FXML
    private TextField txtfieldEditarNota3;

    @FXML
    private TextField txtfieldEditarNota4;


    Aluno aluno;
    String caminho = "src/main/java/com/colini/informacoes_alunos.txt";
    String nomeAluno = CompartilharDados.getNome();
    String cpfAluno = CompartilharDados.getCpfAluno();
    String nascimentoAluno = CompartilharDados.getNascimento();
    String emailAluno = CompartilharDados.getEmail();
    String nota1Aluno = CompartilharDados.getNota1();
    String nota2Aluno = CompartilharDados.getNota2();
    String nota3Aluno = CompartilharDados.getNota3();
    String nota4Aluno = CompartilharDados.getNota4();

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() { // codigo que inicializa ao chamar essa controller
        // Configurar o TextField para aceitar somente valores numericos e uma vírgula
        // Configurar o TextField para aceitar somente valores numericos e uma vírgula
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            // Aceita apenas dígitos e uma vírgula
            if (newText.matches("^[\\d]*[\\,]?[\\d]*$")) {
                return change;
            }
            return null; // Ignora a mudança
        };
        
        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);
        TextFormatter<String> textFormatter4 = new TextFormatter<>(filter);
        
        txtfieldEditarNota1.setTextFormatter(textFormatter1);
        txtfieldEditarNota2.setTextFormatter(textFormatter2);
        txtfieldEditarNota3.setTextFormatter(textFormatter3);
        txtfieldEditarNota4.setTextFormatter(textFormatter4);

        // Evento para capturar o clique inicial do mouse
        paneTelaEditarNotas.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Evento para mover a janela enquanto o mouse é arrastado
        paneTelaEditarNotas.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) paneTelaEditarNotas.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        
        btnFechar.setOnAction(event -> Platform.exit());

        String nota1Corrigida = nota1Aluno.replace('.', ',');
        String nota2Corrigida = nota2Aluno.replace('.', ',');
        String nota3Corrigida = nota3Aluno.replace('.', ',');
        String nota4Corrigida = nota4Aluno.replace('.', ',');

        System.out.println("Nota 1" + nota1Corrigida);

        txtfieldEditarNota1.setText(nota1Corrigida);
        txtfieldEditarNota2.setText(nota2Corrigida);
        txtfieldEditarNota3.setText(nota3Corrigida);
        txtfieldEditarNota4.setText(nota4Corrigida);
    }


    public void escreveArquivoNotas(String nomeTexto, String  nascimentoTexto, String  cpfTexto, String  emailTexto, String nota1Texto, String nota2Texto, String nota3Texto, String nota4Texto) {
        try {
            List<String> list = Files.readAllLines(Paths.get(caminho));

            for (int i = 0; i < list.size(); i++) { // Coleta o index
                String linha = list.get(i); // Atribui a linha do index atual à "linha"
                String[] datablock = linha.split(", "); // Separa o conteudo da linha em blocos, que sao atribuidos ao vetor "datablock"
                    if (datablock.length > 2 && datablock[2].equals(cpfAluno)) { // Se o vetor "datablock" tiver tamanho maior que 2, isso significa que o aluno possui um cpf para comparar com "cpfAluno"
                        // Concatena as notas à "linha" uma string "updatedLine"
                        String updatedLine = nomeTexto + ", " + nascimentoTexto + ", " + cpfTexto + ", " + emailTexto + ", " + nota1Texto + ", " + nota2Texto + ", " + nota3Texto + ", " + nota4Texto;
                        list.set(i, updatedLine); // Altera o index antigo para a linha contida em "updatedLine"
                        break   ; // Para de procurar depois de encontrar o aluno
                    }
            }
            // Escreve novamente todas as linhas no arquivo
            Files.write(Paths.get(caminho), list);
            
        } catch (Exception e) {
            System.out.println("Ocorreu um Erro: " + e.getMessage());
        } 
    }

    public void cadastrarDados() {
        String nomeTexto = nomeAluno;
        String nascimentoTexto = nascimentoAluno; 
        String cpfTexto = cpfAluno; 
        String emailTexto = emailAluno; 
        String nota1Texto = txtfieldEditarNota1.getText().replace(',', '.');
        String nota2Texto = txtfieldEditarNota2.getText().replace(',', '.');
        String nota3Texto = txtfieldEditarNota3.getText().replace(',', '.');
        String nota4Texto = txtfieldEditarNota4.getText().replace(',', '.');
        // Check if any of the text fields are empty
        
        // Check if any of the text fields are empty
        if (nota1Texto.isEmpty() || nota2Texto.isEmpty() || nota3Texto.isEmpty() || nota4Texto.isEmpty()) {
            System.out.println("Todos os campos devem ser preenchidos!");
            return;
        }

        double notanum1 = Double.parseDouble(nota1Texto);
        double notanum2 = Double.parseDouble(nota2Texto);
        double notanum3 = Double.parseDouble(nota3Texto);
        double notanum4 = Double.parseDouble(nota4Texto);

        if (notanum1 > 10 || notanum2 > 10 || notanum3 > 10 || notanum4 > 10) {
            System.out.println("Todos os campos devem ser preenchidos & abaixo de 10!");
            return; 
        }

        escreveArquivoNotas(nomeTexto, nascimentoTexto, cpfTexto, emailTexto, nota1Texto, nota2Texto, nota3Texto, nota4Texto);
        telaLista();
    }

    public void telaLista() {
        App.mudaTela("./views/telaListaAluno.fxml");
    }




}
