'use strict';

angular.module('app').controller('crudNoticiaController', ['$scope', '$http',
				function($scope, $http) {

	$scope.showCadastro = false;
	$scope.noticia = montarObjNoticia();
	$scope.allNoticias = {};

	$scope.abreCadastroNoticia = function() {
		$scope.showCadastro = true;
	}

	$scope.listarNoticias = function(){
        $http.get('/ipet_angular/rest/noticia/listar')
            .success(function(data){
                $scope.allNoticias = data;
                for (var i=0; i<$scope.allNoticias.length; i++) {
                	$scope.allNoticias[i].status_desc = $scope.allNoticias[i].status == 1 ? "Enviar" : "Enviada";
					$scope.allNoticias[i].show = true;
					$scope.allNoticias[i].showDelete = $scope.allNoticias[i].status == 1 ? true : false;
					$("#btn-liberar").addClass("btn btn-primary");
                }
            })
            .error(function(){
                alert("Falha em obter as notícias");
            });
    };
	
	$scope.cadastrarNovaNoticia = function() {
//		var string = $scope.noticia.data;
//		var month = string.substring(0,2);
//		var day = string.substring(2,4);
//		var year = string.substring(4,8);
//		var data_final = month + '/' + day + '/' + year;
		
//		$scope.noticia.data = data_final;
		
		$http.post('/ipet_angular/rest/noticia/new', $scope.noticia)
			.success(function(data) {
				alert("Cadastro efetuado com sucesso!");
				$scope.showCadastro = false;
				$scope.noticia = montarObjNoticia();
				$scope.listarNoticias();
			}).error(function() {
				alert("Falha ao cadastrar notícia!");
			});
	};
	$scope.listarNoticias();
	
	$scope.liberarNoticia = function(noticia) {
		var params = '?id=' + noticia.id;
		$http.post('/ipet_angular/rest/noticia/liberar'+ params).success(function(data) {
			$scope.listarNoticias();
		}).error(function() {
			alert("Falha ao enviar noticia!");
		});
	};
	
	$scope.excluirNoticia = function(noticia) {
		var params = '?id=' + noticia.id;
		$http.post('/ipet_angular/rest/noticia/delete'+ params).success(function(data) {
			$scope.listarNoticias();
		}).error(function() {
			alert("Falha ao excluir noticia!");
		});
	};
	
	$scope.cancelaCadastro = function() {
		$scope.showCadastro = false;
		$scope.noticia = montarObjNoticia();
		$scope.listarNoticias();
	};

	$scope.allAnimais = {};
	$scope.listarAnimais = function(cliente) {
		$http.get('/ipet_angular/rest/animal/listar').success(function(data) {
			$scope.allAnimais = data;
		}).error(function() {
			alert("Falha em obter dados de Pets");
		});
	};
	$scope.listarAnimais();
	
	$scope.addAnimal = function(animal) {
		$scope.noticia.id_animal = animal.id;
	};
	
	$scope.f = {};

	$scope.filter_by = function() {
		for (var i=0; i<$scope.allNoticias.length; i++) {
			if ($scope.allNoticias[i].titulo.toLowerCase().indexOf($scope.f.value.toLowerCase()) !== -1) {
				$scope.allNoticias[i].show = true;
			}else{
				$scope.allNoticias[i].show = false;
			}
        }
	}
	
	function montarObjNoticia() {
		return {
			id : -1,
			id_animal : -1,
			titulo : "",
			descricao : "",
			texto : "",
			data : "",
			status : 1,
			status_desc : "",
			show : true,
			showDelete : true,
			animal : montarObjAnimal()
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
	
}]);
