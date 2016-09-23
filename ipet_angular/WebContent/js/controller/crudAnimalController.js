'use strict';

angular.module('app').controller('crudAnimalController', ['$scope', '$http', 'MyService',
				function($scope, $http, MyService) {

	$scope.showCadastro = false;
	$scope.animal = montarObjAnimal();
	$scope.allAnimais = {};

	$scope.abreCadastroAnimal = function() {
		$scope.showCadastro = true;
	}

	$scope.listarAnimais = function() {
		var id_cliente = '?id=' + MyService.data.cliente.id;
		
		$http.get('/ipet_angular/rest/animal/listar'+ id_cliente).success(function(data) {
			$scope.allAnimais = data;
		}).error(function() {
			alert("Falha em obter dados de Pets");
		});
	};

	$scope.cadastrarNovoAnimal = function() {
		var string = $scope.animal.data_nascimento_pet;
		var month = string.substring(0, 2);
		var day = string.substring(2, 4);
		var year = string.substring(4, 8);
		var data_final = month + '/' + day + '/' + year;

		$scope.animal.data_nascimento_pet = data_final;

		$http.post('/ipet_angular/rest/animal/new', $scope.animal).success(
				function(data) {
					alert("Cadastro efetuado com sucesso!");
					$scope.showCadastro = false;
					$scope.animal = montarObjAnimal();
					$scope.listarAnimais();
				}).error(function() {
			alert("Falha ao cadastrar pet!");
		});
	};
	$scope.listarAnimais();

	$scope.abreListarAtendimento = function(animal) {
		MyService.data.animal = animal;
	};
	
	$scope.cancelaCadastro = function() {
		$scope.showCadastro = false;
		$scope.cliente = montarObjCliente();
		$scope.listarClientes();
	};
	
	function montarObjAnimal() {
		return {
			id : -1,
			id_cliente : MyService.data.cliente.id,
			especie : "",
			raca : "",
			nome_pet : "",
			data_nascimento_pet : ""
		};
	}
}]);

