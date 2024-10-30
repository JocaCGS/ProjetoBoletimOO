# Projeto Boletim

Este projeto visa a implementação de um sistema de gestão de alunos que atende aos seguintes requisitos:

## Requisitos do Sistema

O sistema possui três telas principais:

- **Lista de Alunos**
- **Cadastro de Alunos**
- **Cadastro de Notas**

Os dados dos alunos são salvos em um arquivo `informacoes_alunos.txt`.

## Telas Amigáveis

Todas as telas foram projetadas para serem amigáveis ao usuário, garantindo uma experiência de uso intuitiva e acessível.

## Funcionalidades

### Funcionalidades Implementadas

- **Persistência de Dados**: Os dados dos alunos são armazenados em um arquivo de texto.
  
- **Listagem de Alunos**: Exibe os alunos com informações como nome, data de nascimento, CPF, e-mail, notas (1-4) e a média final calculada como \((n1 + n2 + n3 + n4) / 4\).

- **Cadastro de Novos Alunos**: Tela dedicada para registrar novos alunos no sistema.

- **Cadastro de Notas**: Possibilita registrar notas para um aluno utilizando um ID único (CPF).4

- **Navegação entre Telas**: Alternância entre telas utilizando botões "Voltar".

- **Interação via Botão Direito**: Permite registrar notas de um aluno selecionado com o botão direito do mouse e escolhendo a opção "Adicionar Nota", deletar alunos através do botão direito do mouse e escolhendo "Deletar" no aluno selecionado, editar dados dos alunos através do botão direito do mouse e escolhendo "Editar Dados Aluno" no aluno selecionado ou editar as notas do aluno através do botão direito do mouse e escolhendo "Editar Notas Aluno" no aluno selecionado.

- **Edição de Alunos**: Possibilidade de editar informações do aluno selecionado usando o botão direito do mouse e escolhendo "Editar Aluno".

- **Edição de Notas**: Funcionalidade para editar notas de um aluno selecionado através do botão direito do mouse e escolhendo "Editar Notas".

> **Nota**: Muitas funções ainda não possuem verificação de dados para garantir que as informações estejam sendo inseridas corretamente, pois este não é o foco do projeto. No entanto, há a possibilidade de implementar essas verificações no futuro.
> 
> **Recomendações**: Limpe o arquivo de texto e faça do zero, pois pode dar conflito.
