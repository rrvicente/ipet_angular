'use strict';

angular.module('app').controller('crudAtendimentoController',['$scope', '$http', 'MyService',
				function($scope, $http, MyService) {

					$scope.showCadastro = false;
					$scope.showCadastroItens = false;
					$scope.atendimento = montarObjAtendimento();
					$scope.atendimentoItem = montarObjAtendimentoItem();
					$scope.allAtendimentos = {};
					$scope.allItensAtendimento = {};

					$scope.allClientes = {};
					$scope.listarClientes = function() {
						$http.get('/ipet_angular/rest/cliente/listar').success(
								function(data) {
									$scope.allClientes = data;
								}).error(function() {
							alert("Falha em obter dados de clientes");
						});
					};
					
					$scope.allAnimais = {};
					$scope.listarAnimais = function(cliente) {
						var id_cliente = '?id=' + cliente.id;
						
						$http.get('/ipet_angular/rest/animal/listar'+ id_cliente).success(function(data) {
							$scope.allAnimais = data;
						}).error(function() {
							alert("Falha em obter dados de Pets");
						});
					};
					
					$scope.allProdutos = {};
					$scope.listarProdutos = function() {
						$http.get('/ipet_angular/rest/produto/listar').success(
								function(data) {
									$scope.allProdutos = data;
									for (var i=0; i<$scope.allProdutos.length; i++) {
											$scope.allProdutos[i].show = true;
									}
								}).error(function() {
							alert("Falha em obter dados de produtos");
						});
					};
					
					$scope.abreCadastroAtendimento = function() {
						$scope.sequenciaTabelaAtendimento();
						$scope.atendimento = montarObjAtendimento();
						$scope.allAtendimentos = {};
						$scope.allItensAtendimento = {};
						$scope.listarClientes();
						$scope.allAnimais = {};
						$scope.allProdutos = {};
						$scope.showCadastro = true;
						$scope.showCadastroItens = false;
					}

					$scope.listarAtendimentos = function() {
						var id_animal = '?id='
						if(MyService.data.animal != undefined){
							id_animal += MyService.data.animal.id;
						}
						
						$http.get('/ipet_angular/rest/atendimento/listar'+ id_animal).success(
								function(data) {
									$scope.allAtendimentos = data;
								}).error(function() {
							alert("Falha em obter dados de atendimentos");
						});
					};
					$scope.listarAtendimentos();
					
					$scope.sequenciaTabelaAtendimento = function() {
						var params = '?tabela=tb_atendimento';
						$http.get('/ipet_angular/rest/sequencia_tabela/get_sequencia' + params).success(
								function(data) {
									$scope.atendimento.id = data.id;
								}).error(function() {
							alert("Falha em obter dados de atendimentos");
						});
					};
					
					$scope.cadastrarNovoAtendimento = function() {
						var string = $scope.atendimento.data_atendimento;
						var month = string.substring(0, 2);
						var day = string.substring(2, 4);
						var year = string.substring(4, 8);
						var data_final = month + '/' + day + '/' + year;

						$scope.atendimento.data_atendimento = data_final;
						
						$http.post('/ipet_angular/rest/atendimento/new',
								$scope.atendimento).success(function(data) {
							alert("Cadastro efetuado com sucesso!");
							$scope.showCadastro = false;
							$scope.showCadastroItens = true;
							$scope.listarProdutos();
//							$scope.atendimento = montarObjAtendimento();
						}).error(function() {
							alert("Falha ao cadastrar atendimento!");
						});
					};

					$scope.cancelaCadastro = function() {
						$scope.showCadastro = false;
						$scope.atendimento = montarObjAtendimento();
					};

					$scope.addAnimal = function(animal) {
						$scope.atendimento.id_animal = animal.id;
					};
					
					$scope.addProduto = function(produto) {
						$scope.atendimentoItem.idProduto = produto.id;
					};
					
					$scope.addAtendimentoItem = function() {
						
						var params = '?idAtendimento=' + $scope.atendimento.id + "&idProduto=" + $scope.atendimentoItem.idProduto;
						params += '&quantidade=' + $scope.atendimentoItem.quantidade + '&valor=' + $scope.atendimentoItem.valor; 
						
						$http.get('/ipet_angular/rest/atendimento/new_item' + params,
								$scope.atendimento).success(function(data) {
							alert("Cadastro efetuado com sucesso!");
							$scope.showCadastro = false;
							$scope.listarItensAtendimento();
							$scope.listarProdutos();
						}).error(function() {
							alert("Falha ao cadastrar item para o atendimento!");
						});
					};
					
					$scope.listarItensAtendimento = function() {
						var id_atendimento = '?id=' + $scope.atendimento.id;
						
						$http.get('/ipet_angular/rest/atendimento/listar_itens' + id_atendimento).success(
								function(data) {
									$scope.allItensAtendimento = data;
								}).error(function() {
//							alert("Falha em obter dados de atendimentos");
						});
					};
					
					$scope.abreListarAtendimentoItens = function(atendimento) {
						MyService.data.atendimento = atendimento;
					};
					
					function montarObjAtendimento() {
						return {
							id : -1,
							id_animal : -1,
							valor : 0.0,
							data_atendimento : "",
							animal : montarObjAnimal(),
							cliente : montarObjCliente()
						};
					}
					
					function montarObjAtendimentoItem() {
						return {
							id : -1,
							idAtendimento : -1,
							idProduto : -1,
							valor : 0.0,
							quantidade : 0.0
						};
					}
					
					function montarObjCliente() {
						return {
							id : -1,
							data_cadastro : "",
							nome : "",
							cpf : "",
							telefone : "",
							endereco : "",
							observacao : ""
						};
					}
					
					function montarObjAnimal() {
						return {
							id : -1,
							id_cliente : -1,
							especie : "",
							raca : "",
							nome_pet : "",
							data_nascimento_pet : ""
						};
					}
					
					function montarObjProduto() {
						return {
							id : -1,
							descricao : "",
							valor : 0,
							estoque : -1,
							tipo : -1,
							show : true
						};
					}
					
				} ]);
