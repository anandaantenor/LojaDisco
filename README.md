
# Problema
Crie uma aplicação de uma clínica veterinária. A aplicação terá uma listagem de serviços oferecidos pela clínica (banho e tosa, consulta veterinária, exames, vacinação, cirurgias). Esses serviços poderão ser agendados por um usuário autenticado, que deverá fornecer o nome do responsável, nome do animal, espécie e data para agendamento. O responsável pelo animal poderá acompanhar a ficha do animal com as datas agendadas para cada tipo de serviço, onde também poderá cancelar o agendamento.  A aplicação também deve permitir ao usuário admin o cadastro, exclusão, edição e listagem de todos os animais e clientes.  Para cadastrar clientes é necessário fornecer o nome completo e cpf do usuário. Para cadastrar animais, é necessário fornecer nome do animal, espécie e usuário responsável.

## Endpoints:
### Serviços
  - GET p/ listar serviços
### Agendamentos
  - POST p/ agendamentos
  - GET p/ agendamentos
  - PUT p/ agendamentos

### Animais
  - POST, GET, PUT, DELETE p/ cadastro, listagem, edição e exclusão de animais

### Clientes
  - POST, GET, PUT, DELETE p/ cadastro, listagem, edição e exclusão de clientes

## Especificação
**Cliente:**
cpf: String
nome: String

**Animal:**
nome: String
espécie: String
cliente: Cliente()


##Regras
  -  Só o admin pode modificar todos os cadastros
  -  Só o admin pode cadastrar Serviços
  -  Só pode ser alterado um cliente pelo adm ou pelo proprio cliente.
  -  Só pode alterar um Animal o admin e o responsável pelo animal

