# Autobot
==============


Requisitos de software
---

 - Java JDK 1.8+
 - Maven 3+
 - Eclipse
 

Comando para instalar ferramentas

```javascript
   npm install -g bower
```

Comando para configurar dependencias web

```javascript
   bower install
```

Comando do maven para build do projeto

```javascript
  mvn clean install
```

Comando para iniciar o projeto 

```javascript
   mvn spring-boot:run
```

Requisição listagem de serviços disponíveis:

```javascript
   curl localhost:8080
```

Exemplo para listar serviços de  projetos


```javascript
   curl localhost:8080/projetos
```

Exemplo para gravar Projeto

```javascript
   curl -i -X POST -H "Content-Type:application/json" -d '{  "descricao" : "Teste Project CRUD"}' http://localhost:8080/projetos
```

Exemplo para gravar um novo Plugin

```javascript
  curl -i -X POST -H "Content-Type:application/json" -d '{  "descricao" : "Sikuli", "command" : "java -jar sikuli-script.jar"}' http://localhost:8080/plugins
```

Exemplo para gravar um novo Script

```javascript
   curl -i -X POST -H "Content-Type:application/json" -d '{  "descricao" : "Abrir Browser","filename" : "./abrir_browser.sikuli","idplugin" : 1}' http://localhost:8080/scripts
```

Exemplo para gravar um novo Tarefas

```javascript
   curl -i -X POST -H "Content-Type:application/json" -d '{  "descricao" : "Tarefa 01","idscript" : 1}' http://localhost:8080/tarefas
```

