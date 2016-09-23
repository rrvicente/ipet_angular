'use strict';

angular.module('app').controller('crudClienteController', ['$scope', '$http', 'MyService',
				function($scope, $http, MyService) {

					$scope.showCadastro = false;
					$scope.cliente = montarObjCliente();
					$scope.allClientes = {};

					$scope.abreCadastroCliente = function() {
						$scope.showCadastro = true;
					}

					$scope.listarClientes = function() {
						$http.get('/ipet_angular/rest/cliente/listar').success(
								function(data) {
									$scope.allClientes = data;
									for (var i=0; i<$scope.allClientes.length; i++) {
										$scope.allClientes[i].show = true;
									}
								}).error(function() {
							alert("Falha em obter dados de clientes");
						});
					};

					$scope.cadastrarNovoCliente = function() {

						$http.post('/ipet_angular/rest/cliente/new',
								$scope.cliente).success(function(data) {
							alert("Cadastro efetuado com sucesso!");
							$scope.showCadastro = false;
							$scope.cliente = montarObjCliente();
							$scope.listarClientes();
						}).error(function() {
							alert("Falha ao cadastrar cliente!");
						});
					};
					$scope.listarClientes();

					$scope.cancelaCadastro = function() {
						$scope.showCadastro = false;
						$scope.cliente = montarObjCliente();
						$scope.listarClientes();
					};

					$scope.abreCadastroPetsCliente = function(cliente) {
						MyService.data.cliente = cliente;
					};
					
					$scope.filter_by = function(filter) {
						var value = "";
						
						for (var i=0; i<$scope.allClientes.length; i++) {
							if("nome" === filter){
								value = $scope.allClientes[i].nome.toLowerCase();
							}else{
								value = $scope.allClientes[i].cpf.toLowerCase();
							}
								
							if (value.indexOf($scope.f.value.toLowerCase()) !== -1) {
								$scope.allClientes[i].show = true;
							}else{
								$scope.allClientes[i].show = false;
							}
				        }
					}
					
					function montarObjCliente() {
						return {
							id : -1,
							data_cadastro : "",
							nome : "",
							cpf : "",
							telefone : "",
							endereco : "",
							email : "",
							observacao : "",
							show : true
						};
					}

				} ]);
