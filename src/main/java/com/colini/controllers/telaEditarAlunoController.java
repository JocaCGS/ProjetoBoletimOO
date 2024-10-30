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

public class telaEditarAlunoController {

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnRegistrarEdicao;

    @FXML
    private Button btnVoltar;

    @FXML
    private Pane paneTelaEditarAluno;

    @FXML
    private Text txtEditarAluno;

    @FXML
    private Text txtEditarCpf;

    @FXML
    private Text txtEditarEmail;

    @FXML
    private Text txtEditarNascimento;

    @FXML
    private Text txtEditarNome;

    @FXML
    private Text txtObservacao1Edicao;

    @FXML
    private Text txtObservacaoEditar;

    @FXML
    private TextField txtfieldEditarCpf;

    @FXML
    private TextField txtfieldEditarDataNascimento;

    @FXML
    private TextField txtfieldEditarEmail;

    @FXML
    private TextField txtfieldEditarNome;
    
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
        // Configurar o TextField para aceitar somente valores numericos
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Aceita apenas dígitos
                return change;
            }
            return null; // Ignora a mudança
        };
        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
        txtfieldEditarCpf.setTextFormatter(textFormatter1);

        // Evento para capturar o clique inicial do mouse
        paneTelaEditarAluno.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Evento para mover a janela enquanto o mouse é arrastado
        paneTelaEditarAluno.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) paneTelaEditarAluno.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        
        btnFechar.setOnAction(event -> Platform.exit());


        txtfieldEditarNome.setText(nomeAluno);
        txtfieldEditarCpf.setText(cpfAluno);
        txtfieldEditarDataNascimento.setText(nascimentoAluno);
        txtfieldEditarEmail.setText(emailAluno);
    }


    public void escreveArquivoNotas(String nomeTexto, String  nascimentoTexto, String  cpfTexto, String  emailTexto, String nota1Texto, String nota2Texto, String nota3Texto, String nota4Texto) {
        try {
            List<String> list = Files.readAllLines(Paths.get(caminho));

            for (int i = 0; i < list.size(); i++) { // Coleta o index
                String linha = list.get(i); // Atribui a linha do index atual à "linha"
                String[] datablock = linha.split(", "); // Separa o conteudo da linha em blocos, que sao atribuidos ao vetor "datablock"
                if(datablock.length > 5) {
                    if (datablock.length > 2 && datablock[2].equals(cpfAluno)) { // Se o vetor "datablock" tiver tamanho maior que 2, isso significa que o aluno possui um cpf para comparar com "cpfAluno"
                        // Concatena as notas à "linha" uma string "updatedLine"
                        String updatedLine = nomeTexto + ", " + nascimentoTexto + ", " + cpfTexto + ", " + emailTexto + ", " + nota1Texto + ", " + nota2Texto + ", " + nota3Texto + ", " + nota4Texto;
                        list.set(i, updatedLine); // Altera o index antigo para a linha contida em "updatedLine"
                        break   ; // Para de procurar depois de encontrar o aluno
                    }
                } else if (datablock.length > 2 && datablock[2].equals(cpfAluno)) { // Se o vetor "datablock" tiver tamanho maior que 2, isso significa que o aluno possui um cpf para comparar com "cpfAluno"
                    // Concatena as notas à "linha" uma string "updatedLine"
                    String updatedLine = nomeTexto + ", " + nascimentoTexto + ", " + cpfTexto + ", " + emailTexto;
                    list.set(i, updatedLine); // Altera o index antigo para a linha contida em "updatedLine"
                    break; // Para de procurar depois de encontrar o aluno
                }
            }
            // Escreve novamente todas as linhas no arquivo
            Files.write(Paths.get(caminho), list);
            
        } catch (Exception e) {
            System.out.println("Ocorreu um Erro: " + e.getMessage());
        } 
    }

    public void cadastrarDados() {
        String nomeTexto = txtfieldEditarNome.getText(); // Troca a vírgula pelo ponto olha que fofo essa .replace
        String nascimentoTexto = txtfieldEditarDataNascimento.getText(); // Troca a vírgula pelo ponto olha que fofo essa .replace
        String cpfTexto = txtfieldEditarCpf.getText(); // Troca a vírgula pelo ponto olha que fofo essa .replace
        String emailTexto = txtfieldEditarEmail.getText(); // Troca a vírgula pelo ponto olha que fofo essa .replace
        String nota1Texto = nota1Aluno;
        String nota2Texto = nota2Aluno;
        String nota3Texto = nota3Aluno;
        String nota4Texto = nota4Aluno;
        // Check if any of the text fields are empty
        if (nomeTexto.isEmpty() || nascimentoTexto.isEmpty() || cpfTexto.isEmpty() || emailTexto.isEmpty()) {
            System.out.println("Todos os campos devem ser preenchidos!");
            return;
        }


        escreveArquivoNotas(nomeTexto, nascimentoTexto, cpfTexto, emailTexto, nota1Texto, nota2Texto, nota3Texto, nota4Texto);
        telaLista();
    }

    public void telaLista() {
        App.mudaTela("./views/telaListaAluno.fxml");
    }




}
