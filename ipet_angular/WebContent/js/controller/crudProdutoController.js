'use strict';

angular.module('app').controller(
		'crudProdutoController',
		[
				'$scope',
				'$http',
				function($scope, $http) {

					$scope.showCadastro = false;
					$scope.produto = montarObjProduto();
					$scope.allProdutos = {};

					$scope.abreCadastroProduto = function() {
						$scope.showCadastro = true;
					}

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
						
//						$.ajax({
//							url : 'ProdutoServlet',
//							data : {acao : 'listar'},
//							success : function(responseText) {
//								$scope.allProdutos = responseText;
//								for (var i=0; i<$scope.allProdutos.length; i++) {
//										$scope.allProdutos[i].show = true;
//								}
//							}
//						});
						
					};

					$scope.cadastrarNovoProduto = function() {
						$http.post('/ipet_angular/rest/produto/new',
								$scope.produto).success(function(data) {
							alert("Cadastro efetuado com sucesso!");
							$scope.showCadastro = false;
							$scope.produto = montarObjProduto();
							$scope.listarProdutos();
						}).error(function() {
							alert("Falha ao cadastrar produto!");
						});
						
//								$.ajax({
//									url : 'ProdutoServlet',
//									data : {acao : 'new', produto : $scope.produto},
//									success : function(responseText) {
//									}
//								});
//								$scope.produto = montarObjProduto();
//								$scope.showCadastro = false;
//								$scope.listarProdutos();
						
					};
					$scope.listarProdutos();

					$scope.excluirProduto = function(produto) {
						var params = '?id=' + produto.id;
						$http.post('/ipet_angular/rest/produto/delete'+ params).success(function(data) {
							$scope.listarProdutos();
						}).error(function() {
							alert("Falha ao excluir produto!");
						});
						

								// $.ajax({
								// url : 'ProdutoServlet',
								// data : {acao : 'delete', id : produto.id},
								// success : function(responseText) {
								// $scope.listarProdutos();
								//							}
								//						});
					};
					
					$scope.cancelaCadastro = function() {
						$scope.showCadastro = false;
						$scope.produto = montarObjProduto();
						$scope.listarProdutos();
					};

					$scope.f = {};

					$scope.filter_by = function() {
						for (var i=0; i<$scope.allProdutos.length; i++) {
							if ($scope.allProdutos[i].descricao.toLowerCase().indexOf($scope.f.value.toLowerCase()) !== -1) {
								$scope.allProdutos[i].show = true;
							}else{
								$scope.allProdutos[i].show = false;
							}
				        }
					}

					function montarObjProduto() {
						return {
							id : -1,
							descricao : "",
							valor : 0,
							estoque : 0,
							tipo : -1,
							show : true
						};
					}
				} ]);
