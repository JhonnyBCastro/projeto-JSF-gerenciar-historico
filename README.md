# Sistema de Gerenciamento de Histórico de Atendimentos
Este projeto é uma aplicação web desenvolvida com JavaServer Faces (JSF) integrada a um banco de dados. O objetivo é proporcionar um sistema robusto para o gerenciamento eficiente de dados.

## Descrição
Este projeto é uma aplicação web desenvolvida com JavaServer Faces (JSF) e PrimeFaces, integrada a um banco de dados MySQL. O sistema é projetado para gerenciar o histórico de atendimentos de pacientes e médicos em uma clínica, proporcionando uma interface interativa e intuitiva para facilitar as operações de registro e consulta.

## Diagrama de Classe
O Modelo e o Mapeamento das classes foram feitos de acordo com o Diagrama de Classes.  
![Image and Preview Themes on the toolbar](digrama-de-classe.PNG)

## Tecnologias Utilizadas
<table>
  <tr>
    <td>
      <img
        align="left"
        src="https://github-readme-stats.vercel.app/api/top-langs/?username=JhonnyBCastro&theme=dracula"
        alt="default"
      />
    </td>
    <td>
      <p align="left">
        <h3>Frontend:</h3>
        <img src="https://img.shields.io/badge/JSF-%2343853D.svg?style=for-the-badge&logo=java&logoColor=white" alt="JSF Badge" />
        <img src="https://img.shields.io/badge/PrimeFaces-%23007ACC.svg?style=for-the-badge&logo=java&logoColor=white" alt="PrimeFaces Badge" />
        <img src="https://img.shields.io/badge/CSS-1572B6.svg?style=for-the-badge&logo=css3&logoColor=white" alt="CSS Badge" />
        <img src="https://img.shields.io/badge/XHTML-%23E44D26.svg?style=for-the-badge&logo=html5&logoColor=white" alt="XHTML Badge" />
        <h3>Backend:</h3>
        <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java Badge" />
        <h3>Banco de Dados:</h3>
        <img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL Badge" />
      </p>
    </td>
    <td>
      <h3>IDE:</h3>
      <img src="https://img.shields.io/badge/Eclipse-FE7A16.svg?style=for-the-badge&logo=Eclipse&logoColor=white" alt="Eclipse Badge" />
      <h3>Frameworks e Bibliotecas:</h3>
      <img src="https://img.shields.io/badge/JBoss-FE7A16.svg?style=for-the-badge&logo=JBoss&logoColor=white" alt="JBoss Badge" />
      <img src="https://img.shields.io/badge/JPA-FE7A16.svg?style=for-the-badge&logo=JPA&logoColor=white" alt="JPA Badge" />
      <img src="https://img.shields.io/badge/WildFly-FE7A16.svg?style=for-the-badge&logo=Wildfly&logoColor=white" alt="WildFly Badge" />
    </td>
  </tr>
</table>
 
## Funcionalidades
### Cadastro de Médicos

- Tela para cadastro de médicos.
- Listar e editar médicos já registrados em ordem alfabética.
- Utiliza o recurso de "Column Filtering" no dataTable para permitir a filtragem dos médicos pelo nome.

### Lançar Atendimento

- Tela de lançamento de atendimentos.
- Solicita o CPF do paciente e verifica se ele já foi atendido.
Caso já tenha sido atendido, os dados pessoais e de endereço devem ser preenchidos automaticamente.
Se não encontrado, exibir mensagem de "Paciente não encontrado" e exigir preenchimento manual dos dados.
- Selecionar os médicos envolvidos e registrar a data e hora do atendimento.

### Relatório de Atendimentos

- Tela de relatório de atendimentos.
- Filtrar atendimentos por data de início e término, médico, ou ambos.
- Listar atendimentos com informações detalhadas e permitir ações de exclusão e finalização.  

## Instalação e Configuração
- Java EE 8 ou superior (JSF 2.3, JPA, EJB); 
- IDE Eclipse for Enterprise Java and Web Developers(Java EE) versão 2020-06-R ou inferior (desde que seja compatível com a extensão do Jboss Tools* Verificar no site);
- Servidor de Aplicações WildFly 20.0.1;
- BD Mysql 8.x (Versões mais recentes também são compatíveis, mas pode ser necessário pequena adaptação ou connector java mais recente);
- Biblioteca de Componentes Ricos para JSF - PrimeFaces 11 ou superior;

## Uso
Acesse a aplicação através do navegador web em http://localhost:8080/SeuProjeto.
Navegue pelas diferentes seções para gerenciar funcionários, filiais e gerar relatórios.

## Contribuidores
Olá, eu sou a Suelen! 👋  
- **[Suelen Medina](https://github.com/suelenmedinape)**

Olá, eu sou o Jhonata! 👋
- **[Jhonata Castro](https://github.com/JhonnyBCastro)**
