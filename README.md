# SIGTA
Sistema de Gerenciamento de Tarefas que simula uma ToDo List pessoal.

### A - Aplicação Java Web utilizando JavaServer Faces (JSF).

### B - Banco PostgreSQL para persistência.

### C - Utilização da JPA com a implementação Hibernate.

### D - Alguns teste de unidades foram feitos com JUnit5 principalmente nos models.

### E - Está "deployado" em um ambiente cloud Heroku. (https://sigta-66aa2573bd69.herokuapp.com)

### F
- `Autenticação`: Utilizando sessões e a tabela de Usuario.
- `Observações`: Toda tarefa tem uma lista de observações relacionadas a ela.
- `Arquivamento`: Uma tarefa pode ser arquivada, sendo impossibilitada de edições (apenas exclusão).
- `Progresso`: uma barra de progresso em relação a finalização das tarefas não arquivadas.


## Ambiente local utilizado no desenvolvimento
- IDE Eclipse.
- JDK 8
- Servidor Apache Tomcat 7.0
- PostgreSQL (última versão)
- Maven como ferramenta de dependências e build
- Configuração projeto Maven com JSF 2.2 e Hibernate 5.2.6

## Passos de Instalação local
1. Baixe o zip e descompacte ou clone pelo github;
2. Dar um `Maven update`
2. Certfique-se ter todas essa configurações na properties do projeto estão ok:
	- `Java Compiler`: está setado para 1.8;
	- `Build Path`: está incluindo as Maven Dependencies, JUnit5 e Server Runtime;
	- `Deployment Assembly`: está incluindo as Maven Dependencies e o JUnit5;
3. Configurar `persistence.xml`  no src\main\resources\META-INF\ para apontar para a propriedades certas do banco, local ou o que está em produção (já vem no projeto);
4. Adicionar no servidor rodando no eclipse;
4. Start no servidor.
