package com.colini.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.UnaryOperator;

import com.colini.App;
import com.colini.models.Aluno;
import com.colini.models.CompartilharDados;
import com.colini.models.Notas;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class telaRegistrarNotasController {

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnRegistrarNotas;

    @FXML
    private Button btnVoltar;

    @FXML
    private Pane paneTelaRegistrarNotas;

    @FXML
    private Text txtRegistrarNotas;

    @FXML
    private TextField txtfieldNota1;

    @FXML
    private TextField txtfieldNota2;

    @FXML
    private TextField txtfieldNota3;

    @FXML
    private TextField txtfieldNota4;

    Notas notas;
    Aluno aluno;
    String caminho = "src/main/java/com/colini/informacoes_alunos.txt";
    String cpfAluno = CompartilharDados.getCpfAluno();
    

    
    private double xOffset = 0;
    private double yOffset = 0;

    
    public void initialize() {
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
        
        txtfieldNota1.setTextFormatter(textFormatter1);
        txtfieldNota2.setTextFormatter(textFormatter2);
        txtfieldNota3.setTextFormatter(textFormatter3);
        txtfieldNota4.setTextFormatter(textFormatter4);

        // Evento para capturar o clique inicial do mouse
        paneTelaRegistrarNotas.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Evento para mover a janela enquanto o mouse é arrastado
        paneTelaRegistrarNotas.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) paneTelaRegistrarNotas.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        
        btnFechar.setOnAction(event -> Platform.exit());

    }
    
    public void escreveArquivoNotas(String nota1Texto, String  nota2Texto, String  nota3Texto, String  nota4Texto) {
        try {
            // Le todas as linhas do arquivo e toda vez que quebra linha adiciona como index na lista
            // Mesma coisa do fileReader, so que ao em vez de declarar tudo, ele so puxa um: arquivo do caminho tal, e atribui a lista.
        List<String> list = Files.readAllLines(Paths.get(caminho));

        // Cara desenvolver a logica eh mole fazer que eh brabo (eu conversei com o professsor de fazer isso abaixo mas n tinha ideia do que fazer e olha o que o gpt me faz)
        
        // Encontra a linha correspondente ao CPF
        for (int i = 0; i < list.size(); i++) { // Coleta o index
            String linha = list.get(i); // Atribui a linha do index atual à "linha"
            String[] datablock = linha.split(", "); // Separa o conteudo da linha em blocos, que sao atribuidos ao vetor "datablock"
            if (datablock.length > 2 && datablock[2].equals(cpfAluno)) { // Se o vetor "datablock" tiver tamanho maior que 2, isso significa que o aluno possui um cpf para comparar com "cpfAluno"
                // Concatena as notas à "linha" uma string "updatedLine"
                String updatedLine = linha + ", " + nota1Texto + ", " + nota2Texto + ", " + nota3Texto + ", " + nota4Texto;
                list.set(i, updatedLine); // Altera o index antigo para a linha contida em "updatedLine"
                break; // Para de procurar depois de encontrar o aluno
            }
        }
        //slk programando em hieroglifos

        
        // Escreve novamente todas as linhas no arquivo
        Files.write(Paths.get(caminho), list);
        
        } catch (Exception e) {
            System.out.println("Ocorreu um Erro: " + e.getMessage());
        } 
    }

    public void cadastrarDados() {
        String nota1Texto = txtfieldNota1.getText().replace(',', '.'); // Troca a vírgula pelo ponto olha que fofo essa .replace
        String nota2Texto = txtfieldNota2.getText().replace(',', '.');
        String nota3Texto = txtfieldNota3.getText().replace(',', '.');
        String nota4Texto = txtfieldNota4.getText().replace(',', '.');

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

        escreveArquivoNotas(nota1Texto, nota2Texto, nota3Texto, nota4Texto);
        telaLista();
    }
    
    public void telaLista() {
        App.mudaTela("./views/telaListaAluno.fxml");
    }
}