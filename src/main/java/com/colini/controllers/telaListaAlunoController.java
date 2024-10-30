package com.colini.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import com.colini.App;
import com.colini.models.Aluno;
import com.colini.models.CompartilharDados;
import com.colini.models.Notas;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class telaListaAlunoController {


    @FXML
    private Button btnFechar;

    @FXML
    private Button btnRegistrarAluno;

    @FXML
    private Button btnRegistrarNotaAluno;

    @FXML
    private Pane paneTelaTabela;

    @FXML
    private TableView<Aluno> tbvTabelaAlunos;

    @FXML
    private TableColumn<Aluno, String> tbvcolCPF;

    @FXML
    private TableColumn<Aluno, String> tbvcolDataNascimento;

    @FXML
    private TableColumn<Aluno, String> tbvcolEmail;

    @FXML
    private TableColumn<Aluno, String> tbvcolMedia;

    @FXML
    private TableColumn<Aluno, String> tbvcolNome;

    @FXML
    private TableColumn<Aluno, String> tbvcolNota1;

    @FXML
    private TableColumn<Aluno, String> tbvcolNota2;

    @FXML
    private TableColumn<Aluno, String> tbvcolNota3;

    @FXML
    private TableColumn<Aluno, String> tbvcolNota4;

    @FXML
    private TextField txtfieldCPFAluno;

    Aluno aluno;
    String caminho = "src/main/java/com/colini/informacoes_alunos.txt";
    ObservableList<Aluno> listaDeAlunos = FXCollections.observableArrayList();

    public String cpfAluno;
    public String cpfAlunoClicado = null;
    public String nomeAlunoClicado = null;
    public String nascimentoAlunoClicado = null;
    public String emailAlunoClicado = null;
    public String nota1AlunoClicado = null;
    public String nota2AlunoClicado = null;
    public String nota3AlunoClicado = null;
    public String nota4AlunoClicado = null;
    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize() {
        //coisa de javafx nao me pergunta, um dia eu soube mas agora eh historia
        tbvcolNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbvcolDataNascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
        tbvcolCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tbvcolEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbvcolNota1.setCellValueFactory(new PropertyValueFactory<>("nota1"));
        tbvcolNota2.setCellValueFactory(new PropertyValueFactory<>("nota2"));
        tbvcolNota3.setCellValueFactory(new PropertyValueFactory<>("nota3"));
        tbvcolNota4.setCellValueFactory(new PropertyValueFactory<>("nota4"));
        tbvcolMedia.setCellValueFactory(new PropertyValueFactory<>("media"));
        cadastrarDadosLista();

        
        // Configurar o TextField para aceitar somente valores numericos
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Accept only digits
                return change;
            }
            return null; // Ignore changes that are not digits
        };

        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
        txtfieldCPFAluno.setTextFormatter(textFormatter1);
        
        
        ContextMenu contextMenu = new ContextMenu();


        MenuItem menuItemAddNota = new MenuItem("Adicionar Nota");
        MenuItem menuItemDeletar = new MenuItem("Deletar");
        MenuItem menuItemEditarAluno = new MenuItem("Editar Dados Aluno");
        MenuItem menuItemEditarNotas = new MenuItem("Editar Notas Aluno");
        
        contextMenu.getItems().addAll(menuItemAddNota, menuItemDeletar, menuItemEditarAluno, menuItemEditarNotas);
        
        tbvTabelaAlunos.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) { // Clique com o botão direito
                // Verifique se um item foi selecionado
                if (tbvTabelaAlunos.getSelectionModel().getSelectedItem() != null) {
                    cpfAlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getCpf();
                    nomeAlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getNome();
                    nascimentoAlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getNascimento();
                    emailAlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getEmail();
                    nota1AlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getNota1();
                    nota2AlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getNota2();
                    nota3AlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getNota3();
                    nota4AlunoClicado = tbvTabelaAlunos.getSelectionModel().getSelectedItem().getNota4();
                    System.out.println("Nome selecionado: " + nomeAlunoClicado);
                    System.out.println("CPF selecionado: " + cpfAlunoClicado);
                    System.out.println("Data de Nascimento selecionada: " + nascimentoAlunoClicado);
                    System.out.println("Email selecionado: " + emailAlunoClicado);
                    System.out.println("Nota1 selecionada: " + nota1AlunoClicado);
                    System.out.println("Nota2 selecionada: " + nota2AlunoClicado);
                    System.out.println("Nota3 selecionada: " + nota3AlunoClicado);
                    System.out.println("Nota4 selecionada: " + nota4AlunoClicado);
                    contextMenu.show(tbvTabelaAlunos, event.getScreenX(), event.getScreenY());
                }
            }
        });

        menuItemAddNota.setOnAction(e -> telaNotasContextMenu());
        menuItemDeletar.setOnAction(e -> deletarAluno());
        menuItemEditarAluno.setOnAction(e -> telaEditarAlunoContextMenu());
        menuItemEditarNotas.setOnAction(e -> telaEditarNotasContextMenu());

        
        // Evento para capturar o clique inicial do mouse
        paneTelaTabela.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Evento para mover a janela enquanto o mouse é arrastado
        paneTelaTabela.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) paneTelaTabela.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        btnFechar.setOnAction(event -> Platform.exit());
    }

    
    public void lerArquivo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(caminho));
            String line;
            listaDeAlunos.clear();

            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(","); // Coleta blocos de data separados por virgula (como no arquivo de texto)

                if (dados.length >= 4) { // Verifica se tem pelo menos 4 blocos de data para aluno (nome, nascimento, CPF, email)
                    String nome = dados[0].trim(); // (Ate hoje nao sei o que esse trim faz, mas acho que eh tipo uma formatacao de string sem espaco)
                    String nascimento = dados[1].trim();
                    String cpf = dados[2].trim();
                    String email = dados[3].trim();
                    
                    // Garante que alguma coisa vai pro objeto aluno, nem que seja null
                    Notas notas = null;
                    String media = null;
                    if (dados.length == 8) { // se os dados sem nota tem 4 blocos (nome, nascimento, cpf e email), entao verifica para 8 blocos, agora considerando as notas (n1, n2, n3, n4);
                        String nota1 = dados[4].trim();
                        String nota2 = dados[5].trim();
                        String nota3 = dados[6].trim();
                        String nota4 = dados[7].trim();
                        notas = new Notas(nota1, nota2, nota3, nota4);
                        media = calcularMedia(notas);
                    }
                    
                    aluno = new Aluno(nome, nascimento, cpf, email, notas, media);
                    listaDeAlunos.add(aluno);
                } else {
                    System.err.println("Formato incorreto na linha: " + line);
                }
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    
    private String calcularMedia(Notas notas) {
        // Converte as notas para double, considerando zero se não houver nota registrada <- issae que o GPT falou meu mano
        double nota1 = notas.getNota1().isEmpty() ? 0 : Double.parseDouble(notas.getNota1()); // (nao tenho ideia do que isso faz na pratica)
        double nota2 = notas.getNota2().isEmpty() ? 0 : Double.parseDouble(notas.getNota2());
        double nota3 = notas.getNota3().isEmpty() ? 0 : Double.parseDouble(notas.getNota3());
        double nota4 = notas.getNota4().isEmpty() ? 0 : Double.parseDouble(notas.getNota4());

        double media = (nota1 + nota2 + nota3 + nota4) / 4;
        return String.format("%.2f", media);  // Formata a média com duas casas decimais
    }


    public void cadastrarDadosLista() {
        lerArquivo();
        tbvTabelaAlunos.setItems(listaDeAlunos);
    }


    public void deletarAluno() {
        Aluno alunoSelecionado = tbvTabelaAlunos.getSelectionModel().getSelectedItem();
    
        if (alunoSelecionado != null) {
            // Remove o aluno da lista e da TableView
            listaDeAlunos.remove(alunoSelecionado);
            tbvTabelaAlunos.getItems().remove(alunoSelecionado);
            
            // Atualiza o arquivo de texto
            atualizarArquivo();
        }
    }

    public void telaEditarAlunoContextMenu(){
            CompartilharDados.setCpfAluno(cpfAlunoClicado);
            CompartilharDados.setNome(nomeAlunoClicado);
            CompartilharDados.setNascimento(nascimentoAlunoClicado);
            CompartilharDados.setEmail(emailAlunoClicado);
            CompartilharDados.setNota1(nota1AlunoClicado);
            CompartilharDados.setNota2(nota2AlunoClicado);
            CompartilharDados.setNota3(nota3AlunoClicado);
            CompartilharDados.setNota4(nota4AlunoClicado);
            App.mudaTela("./views/telaEditarAluno.fxml");
    }

    public void telaEditarNotasContextMenu(){
        
        try {
            listaDeAlunos.clear();
            List<String> list = Files.readAllLines(Paths.get(caminho));

            // Verificar se ja existem notas ligadas aquele cpf coletado no clique do botao direito.
            
            for (int i = 0; i < list.size(); i++) { 
                String linha = list.get(i); 
                String[] datablock = linha.split(", ");
                if (cpfAlunoClicado != null && !cpfAlunoClicado.isEmpty()) {
                    if(datablock.length < 5 && datablock[2].equals(cpfAlunoClicado)){
                        System.out.println(datablock[2]);
                        System.out.println(cpfAlunoClicado);
                        System.out.println("chega aqui?");
                        return;
                    } 
                }
            }
            
            CompartilharDados.setCpfAluno(cpfAlunoClicado);
            CompartilharDados.setNome(nomeAlunoClicado);
            CompartilharDados.setNascimento(nascimentoAlunoClicado);
            CompartilharDados.setEmail(emailAlunoClicado);
            CompartilharDados.setNota1(nota1AlunoClicado);
            CompartilharDados.setNota2(nota2AlunoClicado);
            CompartilharDados.setNota3(nota3AlunoClicado);
            CompartilharDados.setNota4(nota4AlunoClicado);
            App.mudaTela("./views/telaEditarNotas.fxml");
        } catch (IOException e) {
            System.err.println("Erro em tela edita notas contextmenu" + e.getMessage());
        }

    }
    

    public void atualizarArquivo() {
        try {
            List<String> linhasAtualizadas = new ArrayList<>();
            String linha;
            for (Aluno aluno : listaDeAlunos) {
                if(aluno.getNotas() == null){
                    linha = aluno.getNome() + ", " + aluno.getNascimento() + ", " + aluno.getCpf() + ", " + aluno.getEmail();
                } else {
                    linha = aluno.getNome() + ", " + aluno.getNascimento() + ", " + aluno.getCpf() + ", " + aluno.getEmail() + ", " + aluno.getNota1() + ", " + aluno.getNota2() + ", " + aluno.getNota3() + ", " + aluno.getNota4();
                }
                
                linhasAtualizadas.add(linha);
            }
            Files.write(Paths.get(caminho), linhasAtualizadas); // Sobrescreve o arquivo com as linhas atualizadas
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo: " + e.getMessage());
        }
    }


    public void telaRegistro() {
        App.mudaTela("./views/telaRegistrarAluno.fxml");
    }
    

    public void telaNotasTextField() {
        try {
            listaDeAlunos.clear();
            List<String> list = Files.readAllLines(Paths.get(caminho));
            String cpfAlunoNotas = txtfieldCPFAluno.getText();

            // Verificar se ja existem notas ligadas aquele cpf ligado ao TextField.

            for (int i = 0; i < list.size(); i++) {
                String linha = list.get(i);
                String[] datablock = linha.split(", ");
                if (datablock.length > 4 && datablock[2].equals(cpfAlunoNotas)) {
                    cadastrarDadosLista();
                    return;
                }
            }

            CompartilharDados.setCpfAluno(cpfAlunoNotas);
            App.mudaTela("./views/telaRegistrarNotas.fxml");
        } catch (IOException e) {
            System.err.println("Erro em tela notas textfield" + e.getMessage());
        }
    }


    public void telaNotasContextMenu(){
        try {
            listaDeAlunos.clear();
            List<String> list = Files.readAllLines(Paths.get(caminho));

            // Verificar se ja existem notas ligadas aquele cpf coletado no clique do botao direito.

            for (int i = 0; i < list.size(); i++) { 
                String linha = list.get(i); 
                String[] datablock = linha.split(", ");
                if (cpfAlunoClicado != null && !cpfAlunoClicado.isEmpty()) {
                    if(datablock.length > 4 && datablock[2].equals(cpfAlunoClicado)){
                        cadastrarDadosLista();
                        return;
                    } 
                }
            }

            CompartilharDados.setCpfAluno(cpfAlunoClicado);
            App.mudaTela("./views/telaRegistrarNotas.fxml");
        } catch (IOException e) {
            System.err.println("Erro em tela notas contextmenu" + e.getMessage());
        }
    }
}