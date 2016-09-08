'use strict';

angular.module('app').controller('crudClienteController', ['$scope', '$http',
				function($scope, $http) {

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
								}).error(function() {
							alert("Falha em obter dados de clientes");
						});
					};

					$scope.cadastrarNovoCliente = function() {

						// $scope.cliente.animal =
						// encontrarAnimal($scope.animal,
						// $scope.allAnimais);

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
						alert(cliente);
					};
					
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

					function encontrarAnimal(nomeAnimal, allAnimais) {
						for (var int = 0; int < allAnimais.length; int++) {
							if (nomeAnimal == allAnimais[int].raca) {
								return allAnimais[int];
							}
						}
					}
					// }
				} ]);
